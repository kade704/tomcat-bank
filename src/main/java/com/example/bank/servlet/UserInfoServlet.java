package com.example.bank.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.example.bank.dao.UserDAO;
import com.example.bank.dto.UserResponseDTO;
import com.example.bank.util.DatabaseUtil;

@WebServlet("/user/info")
public class UserInfoServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = (String) request.getSession().getAttribute("userId");
        if (userId == null || userId.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }

        Connection conn = DatabaseUtil.getConnection();
        if (conn == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection failed");
            return;
        }

        try {
            UserResponseDTO user = userDAO.getUserById(conn, userId);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/views/user-info.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        } finally {
            DatabaseUtil.closeConnection(conn);
        }
    }
    
}
