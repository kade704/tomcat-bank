<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <c:import url="/WEB-INF/views/layout/theme-changer.jsp" />
    <title>SHBank</title>
</head>
<body class="bg-base-100">
    <c:import url="/WEB-INF/views/layout/sidebar.jsp" />

    <main class="ml-64 mt-4 w-2xl">
        <c:import url="/WEB-INF/views/layout/alert.jsp" />

        <div class="hero h-96 bg-base-200 border-base-300 rounded-box border shadow">
            <div class="hero-content text-center">
                <div class="max-w-md">
                    <h1 class="text-2xl font-bold mb-4">SHBank에 오신 것을 환영합니다!</h1>

                    <p>SHBank는 안전하고 편리한 온라인 뱅킹 서비스를 제공합니다.</p>
                    <p>계좌 개설, 송금, 거래 내역 조회 등 다양한 금융 서비스를 이용해보세요.</p>
                    <a href="${pageContext.request.contextPath}/account/list" class="btn btn-primary mt-4">거래 시작</a>
                </div>
            </div>
        </div>
    </main>
</body>
</html>
