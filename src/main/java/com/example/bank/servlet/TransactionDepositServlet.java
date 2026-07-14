package com.example.bank.servlet;

import com.example.bank.dao.AccountDAO;
import com.example.bank.dao.TransactionDAO;
import com.example.bank.dto.AccountResponseDTO;
import com.example.bank.dto.TransactionDepositDTO;
import com.example.bank.util.DatabaseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/transaction/deposit")
public class TransactionDepositServlet extends HttpServlet {
    
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
            // 입금 계좌에 대한 접근 권한 확인
            List<AccountResponseDTO> accounts = accountDAO.getAccountsByUserId(conn, userId);
            boolean ownsAccount = accounts.stream().anyMatch(acc -> acc.getId().equals(accountId));
            if (!ownsAccount) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "권한없음");
                return;
            }

            AccountResponseDTO account = accountDAO.getAccountById(conn, accountId);
            request.setAttribute("account", account);
            request.getRequestDispatcher("/WEB-INF/views/transaction-deposit.jsp").forward(request, response);
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
        String amountStr = request.getParameter("amount");

        if (accountId == null || accountId.trim().isEmpty() || 
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

        // 입금 제한 금액 설정
        if (amount <= 0 || amount > 1_000_000) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "유효하지 않은 금액");
            return;
        }

        Connection conn = DatabaseUtil.getConnection();
        if (conn == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "데이터베이스 연결 실패");
            return;
        }

        try {
            conn.setAutoCommit(false);

            // 입금 계좌에 대한 접근 권한 확인
            List<AccountResponseDTO> accounts = accountDAO.getAccountsByUserId(conn, userId);
            boolean ownsAccount = accounts.stream().anyMatch(acc -> acc.getId().equals(accountId));
            if (!ownsAccount) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "권한없음");
                return;
            }

            // 입금 계좌 잔액 조회
            Long balance = accountDAO.getAccountBalanceById(conn, accountId);

            // 입금 계좌 잔액 증액
            accountDAO.increaseAccountBalance(conn, accountId, amount);

            // 거래 기록 저장
            TransactionDepositDTO transaction = new TransactionDepositDTO(accountId, amount, balance);
            transactionDAO.createDepositTransaction(conn, transaction);

            conn.commit();

            response.setStatus(HttpServletResponse.SC_OK);
            request.getSession().setAttribute("message", "입금이 완료되었습니다.");
            response.sendRedirect(request.getContextPath() + "/transaction/deposit?accountId=" + accountId);
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception rollbackEx) {
                System.err.println("롤백 실패: " + rollbackEx.getMessage());
            }

            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "입금 처리 중 오류 발생");
        } finally {    
            DatabaseUtil.closeConnection(conn);
        }
    }
}
