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

@WebServlet("/account/list")
public class AccountListServlet extends HttpServlet {
    private AccountDAO accountDAO;

    @Override
    public void init() {
        accountDAO = new AccountDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = (String) request.getSession().getAttribute("userId");
        if (userId == null || userId.trim().isEmpty()) {
            response.sendRedirect("/user/login");
            return;
        }

        Connection conn = DatabaseUtil.getConnection();
        if (conn == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "데이터베이스 연결 실패");
            return;
        }

        try {
            List<AccountResponseDTO> accounts = accountDAO.getAccountsByUserId(conn, userId); 
            request.setAttribute("accounts", accounts);
            request.getRequestDispatcher("/WEB-INF/views/account-list.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error listing accounts", e);
        } finally {
            DatabaseUtil.closeConnection(conn);
        }
    }
}
