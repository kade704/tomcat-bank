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
<body class="bg-base-100 w-screen h-screen flex items-center justify-center">
    <main class="w-lg">
        <c:import url="/WEB-INF/views/layout/alert.jsp" />
    
        <form action="/user/login" method="post" class="w-full flex flex-col gap-2 bg-base-200 border-base-300 rounded-box border shadow p-4">
            <a href="${pageContext.request.contextPath}/" class="w-full mb-4 text-center text-4xl font-bold">SHBank</a>

            <div>
                <label class="text-xs">아이디</label>
                <input type="text" name="id" class="input w-full" placeholder="아이디" required />
            </div>

            <div>
                <label class="text-xs">비밀번호</label>
                <input type="password" name="password" class="input w-full" placeholder="비밀번호" required />
            </div>

            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}" />

            <button class="w-full btn btn-primary mt-4">로그인</button>

            <p class="text-sm text-center">계정이 없으신가요? <a href="${pageContext.request.contextPath}/user/signup" class="link">회원가입</a></p>
        </form>
    </main>
</body>
</html>
