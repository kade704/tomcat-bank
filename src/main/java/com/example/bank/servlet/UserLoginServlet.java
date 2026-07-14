package com.example.bank.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import com.example.bank.dao.UserDAO;
import com.example.bank.dto.UserLoginDTO;
import com.example.bank.util.DatabaseUtil;

@WebServlet("/user/login")
public class UserLoginServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String password = request.getParameter("password");

        if (id == null || id.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "누락된 매개변수");
            return;
        }

        UserLoginDTO userLoginDTO = new UserLoginDTO(id, password);

        Connection conn = DatabaseUtil.getConnection();
        if (conn == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        try {
            boolean isValidUser = userDAO.loginUser(conn, userLoginDTO);
            if (isValidUser) {
                request.getSession().invalidate();
                request.getSession(true);

                String csrfToken = UUID.randomUUID().toString();

                request.getSession().setAttribute("userId", id);
                request.getSession().setAttribute("csrfToken", csrfToken);
                request.getSession().setAttribute("message", "로그인이 완료되었습니다.");
                response.sendRedirect(request.getContextPath() + "/account/list");
            } else {
                request.getSession().setAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
                response.sendRedirect(request.getContextPath() + "/user/login");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "로그인 중 오류가 발생했습니다. 다시 시도해주세요.");
            response.sendRedirect(request.getContextPath() + "/user/login");
        } finally {
            DatabaseUtil.closeConnection(conn);
        }
    }
}
