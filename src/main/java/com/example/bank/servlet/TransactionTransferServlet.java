package com.example.bank.servlet;

import com.example.bank.dao.AccountDAO;
import com.example.bank.dao.TransactionDAO;
import com.example.bank.dto.AccountResponseDTO;
import com.example.bank.dto.TransactionTransferDTO;
import com.example.bank.util.DatabaseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/transaction/transfer")
public class TransactionTransferServlet extends HttpServlet {
    private TransactionDAO transactionDAO;
    private AccountDAO accountDAO;

    @Override
    public void init() {
        transactionDAO = new TransactionDAO();
        accountDAO = new AccountDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = (String) request.getSession().getAttribute("userId");
        if (userId == null || userId.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 필요");
            return;
        }

        String accountId = request.getParameter("accountId");
        if (accountId == null || accountId.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "누락된 매개변수");
            return;
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            List<AccountResponseDTO> accounts = accountDAO.getAccountsByUserId(conn, userId);
            boolean ownsAccount = accounts.stream().anyMatch(acc -> acc.getId().equals(accountId));
            if (!ownsAccount) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "권한없음");
                return;
            }

            AccountResponseDTO account = accountDAO.getAccountById(conn, accountId);
            request.setAttribute("account", account);
            request.getRequestDispatcher("/WEB-INF/views/transaction-transfer.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "계좌 조회 중 오류가 발생");
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = (String) request.getSession().getAttribute("userId");
        if (userId == null || userId.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 필요");
            return;
        }

        String accountId = request.getParameter("accountId");
        String accountIdTransfer = request.getParameter("accountIdTransfer");
        String amountStr = request.getParameter("amount");

        if (accountId == null || accountId.trim().isEmpty() || 
            accountIdTransfer == null || accountIdTransfer.trim().isEmpty() || 
            amountStr == null || amountStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "누락된 매개변수");
            return;
        }

        Long amount = 0L;
        try {
            amount = Long.parseLong(amountStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "유효하지 않은 금액");
            return;
        }

        // 이체 금액 제한 설정
        if (amount <= 0 || amount > 1_000_000) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "유효하지 않은 금액");
            return;
        }

        // 같은 계좌로의 이체 방지
        if (accountId.equals(accountIdTransfer)) {
            request.getSession().setAttribute("error", "같은 계좌로 이체할 수 없습니다.");
            response.sendRedirect(request.getContextPath() + "/transaction/transfer?accountId=" + accountId);
            return;
        }

        Connection conn = DatabaseUtil.getConnection();
        if (conn == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "데이터베이스 연결 실패");
            return;
        }

        try {
            conn.setAutoCommit(false);

            // 출금 계좌에 대한 접근 권한 확인
            List<AccountResponseDTO> accounts = accountDAO.getAccountsByUserId(conn, userId);
            boolean ownsAccount = accounts.stream().anyMatch(acc -> acc.getId().equals(accountId));
            if (!ownsAccount) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "권한없음");
                return;
            }

            // 출금 계좌 잔액 확인
            Long balance = accountDAO.getAccountBalanceById(conn, accountId);
            if (balance < amount) {
                request.getSession().setAttribute("error", "잔액이 부족합니다.");
                response.sendRedirect(request.getContextPath() + "/transaction/transfer?accountId=" + accountId);
                return;
            }

            // 입금 계좌 존재 여부 확인
            if (!accountDAO.accountExists(conn, accountIdTransfer)) {
                request.getSession().setAttribute("error", "입금 계좌가 존재하지 않습니다.");
                response.sendRedirect(request.getContextPath() + "/transaction/transfer?accountId=" + accountId);
                return;
            }

            // 입금 계좌 잔액 확인
            Long balanceTransfer = accountDAO.getAccountBalanceById(conn, accountIdTransfer);

            // 출금 계좌 잔액 업데이트
            accountDAO.decreaseAccountBalance(conn, accountId, amount);

            // 입금 계좌 잔액 업데이트
            accountDAO.increaseAccountBalance(conn, accountIdTransfer, amount);

            // 거래 기록 저장
            TransactionTransferDTO transaction = new TransactionTransferDTO(accountId, accountIdTransfer, amount, balance, balanceTransfer);
            transactionDAO.createTransferTransaction(conn, transaction);

            conn.commit();

            request.getSession().setAttribute("message", "이체가 완료되었습니다.");
            response.sendRedirect(request.getContextPath() + "/transaction/transfer?accountId=" + accountId);
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception rollbackEx) {
                System.err.println("롤백 실패: " + rollbackEx.getMessage());
            }

            request.getSession().setAttribute("error", "이체 처리 중 오류 발생");
            response.sendRedirect(request.getContextPath() + "/transaction/transfer?accountId=" + accountId);
        } finally {
            DatabaseUtil.closeConnection(conn);
        }
    }
}
