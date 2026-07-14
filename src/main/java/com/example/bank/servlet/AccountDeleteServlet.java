package com.example.bank.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.example.bank.dao.AccountDAO;
import com.example.bank.dto.AccountResponseDTO;
import com.example.bank.util.DatabaseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/account/delete")
public class AccountDeleteServlet extends HttpServlet {
    private AccountDAO accountDAO;

    @Override
    public void init() {
        accountDAO = new AccountDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        Connection conn = DatabaseUtil.getConnection();
        if (conn == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        try {
            // 해당 계좌에 대한 접근 권한 확인
            List<AccountResponseDTO> accounts = accountDAO.getAccountsByUserId(conn, userId);
            boolean ownsAccount = accounts.stream().anyMatch(acc -> acc.getId().equals(accountId));
            if (!ownsAccount) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "권한없음");
                return;
            }

            // 계좌 잔액 확인
            AccountResponseDTO account = accountDAO.getAccountById(conn, accountId);
            if (account.getBalance() > 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "계좌 잔액이 0이어야 삭제할 수 있습니다");
                return;
            }

            // 계좌 삭제
            boolean success = accountDAO.deleteAccount(conn, accountId);
            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException e) {
            throw new ServletException("Error deleting account", e);
        } finally {
            DatabaseUtil.closeConnection(conn);
        }
    }
}
