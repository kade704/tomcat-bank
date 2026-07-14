package com.example.bank.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CsrfFilter implements Filter {

    private List<String> excludeUrls = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String excludeParam = filterConfig.getInitParameter("excludeUrls");
        
        if (excludeParam != null && !excludeParam.trim().isEmpty()) {
            String[] urls = excludeParam.split(",");
            for (String url : urls) {
                excludeUrls.add(url.trim());
            }
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String method = httpRequest.getMethod();
        if ("GET".equalsIgnoreCase(method) || "HEAD".equalsIgnoreCase(method) || "OPTIONS".equalsIgnoreCase(method)) {
            chain.doFilter(request, response);
            return;
        }

        String path = httpRequest.getServletPath();
        if (excludeUrls.contains(path)) {
            chain.doFilter(request, response); 
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        String sessionToken = (session != null) ? (String) session.getAttribute("csrfToken") : null;
        
        String requestToken = httpRequest.getParameter("csrfToken");
        if (requestToken == null) {
            requestToken = httpRequest.getHeader("X-CSRF-TOKEN");
        }

        if (sessionToken == null || !sessionToken.equals(requestToken)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF Token 누락 또는 불일치");
            return;
        }

        chain.doFilter(request, response);
    }
}