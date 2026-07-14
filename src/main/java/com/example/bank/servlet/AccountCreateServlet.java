package com.example.bank.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import com.example.bank.dao.AccountDAO;
import com.example.bank.util.DatabaseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/account/create")
public class AccountCreateServlet extends HttpServlet {
    private AccountDAO accountDAO;

    @Override
    public void init() {
        accountDAO = new AccountDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/account-create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 로그인된 사용자 ID 가져오기
        String userId = (String) request.getSession().getAttribute("userId");
        if (userId == null || userId.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 필요");
            return;
        }
        
        String branchId = request.getParameter("branchId");
        if (branchId == null || branchId.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "누락된 매개변수");
            return;
        }

        String[] availableBranches = {"111", "112", "113"};
        if (!Arrays.asList(availableBranches).contains(branchId)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "유효하지 않은 지점 ID");
            return;
        }

        Connection conn = DatabaseUtil.getConnection();
        if (conn == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "데이터베이스 연결 실패");
            return;
        }

        try {
            boolean success = accountDAO.createAccount(conn, userId, branchId);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/account/list");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "계좌 생성 실패");
            }
        } catch (SQLException e) {
            System.err.println("Error creating account: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "계좌 생성 중 오류 발생");
        } finally {
            DatabaseUtil.closeConnection(conn);
        }
    }

}
