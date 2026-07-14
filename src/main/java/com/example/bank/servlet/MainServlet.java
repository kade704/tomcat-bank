package com.example.bank.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // CSRF 토큰 생성 및 세션에 저장
        String csrfToken = java.util.UUID.randomUUID().toString();
        if (request.getSession() != null && request.getSession().getAttribute("csrfToken") == null) {
            request.getSession().setAttribute("csrfToken", csrfToken);
        }

        request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
    }
}