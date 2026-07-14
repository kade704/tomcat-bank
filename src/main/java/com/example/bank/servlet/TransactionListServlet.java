package com.example.bank.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import com.example.bank.dao.AccountDAO;
import com.example.bank.dao.TransactionDAO;
import com.example.bank.dto.AccountResponseDTO;
import com.example.bank.dto.TransactionResponseDTO;
import com.example.bank.util.DatabaseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/transaction/list")
public class TransactionListServlet extends HttpServlet {

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

        String accountID = request.getParameter("accountId");
        if (accountID == null || accountID.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "누락된 매개변수");
            return;
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            // 해당 계좌에 대한 접근 권한 확인
            List<AccountResponseDTO> accounts = accountDAO.getAccountsByUserId(conn, userId);
            boolean hasAccess = accounts.stream().anyMatch(account -> account.getId().equals(accountID));
            if (!hasAccess) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "권한없음");
                return;
            }

            // 거래 내역 조회
            List<TransactionResponseDTO> transactions = transactionDAO.getTransactionsByAccountId(conn, accountID);
            request.setAttribute("transactions", transactions);
            request.getRequestDispatcher("/WEB-INF/views/transaction-list.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error listing transactions", e);
        }
    }
}
