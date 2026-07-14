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
import com.example.bank.dto.UserSignupDTO;
import com.example.bank.util.DatabaseUtil;

@WebServlet("/user/signup")
public class UserSignupServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String passwordConfirm = request.getParameter("passwordConfirm");
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");
        String ageStr = request.getParameter("age");

        

        if (id == null || id.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            passwordConfirm == null || passwordConfirm.trim().isEmpty() ||
            fullName == null || fullName.trim().isEmpty() ||
            phoneNumber == null || phoneNumber.trim().isEmpty() ||
            ageStr == null || ageStr.trim().isEmpty()
            ) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "누락된 매개변수");
            return;
        }

        int age = 0;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "유효하지 않은 나이 값");
            return;
        }

        if (!password.equals(passwordConfirm)) {
            request.getSession().setAttribute("error", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            response.sendRedirect(request.getContextPath() + "/user/signup");
            return;
        }


        UserSignupDTO user = new UserSignupDTO(id, password, passwordConfirm, fullName, email, phoneNumber, age);
        
        Connection conn = DatabaseUtil.getConnection();
        if (conn == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "데이터베이스 연결 실패");
            return;
        }

        try {
            if (userDAO.signupUser(conn, user)) {
                request.getSession().setAttribute("message", "가입이 완료되었습니다. 로그인해주세요.");
            } else {
                request.getSession().setAttribute("error", "가입에 실패했습니다. 아이디가 이미 존재합니다.");
            }
        } catch (SQLException e) {
            request.getSession().setAttribute("error", "가입 중 오류가 발생했습니다. 다시 시도해주세요.");
        } finally {            
            DatabaseUtil.closeConnection(conn);
        }
        response.sendRedirect(request.getContextPath() + "/user/signup");
    }
}
