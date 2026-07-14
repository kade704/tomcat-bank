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

    <main class="ml-64 mt-4 w-xl">
        <c:import url="/WEB-INF/views/layout/alert.jsp" />

        <ul class="list bg-base-200 border-base-300 rounded-box border shadow">
            <li class="p-4 pb-2 text-xs opacity-60 tracking-wide">사용자 정보</li>

            <c:if test="${not empty user}">
                <li class="list-row">
                    <p class="flex-1">아이디:</p>
                    <p>${user.id}</p>
                </li>
                <li class="list-row">
                    <p class="flex-1">성명:</p>
                    <p>${user.fullName}</p>
                </li>
                <li class="list-row">
                    <p class="flex-1">이메일:</p>
                    <p>${user.email}</p>
                </li>
                <li class="list-row">
                    <p class="flex-1">전화번호:</p>
                    <p>${user.phoneNumber}</p>
                </li>
                <li class="list-row">
                    <p class="flex-1">나이:</p>
                    <p>${user.age}</p>
                </li>
                <li class="list-row">
                    <p class="flex-1">가입일:</p>
                    <p>${fn:replace(user.createdAt, 'T', ' ')}</p>
                </li>
            </c:if>
        </ul>
    </main>
</body>
</html>
