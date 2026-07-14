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
    <main class="w-xl">
        <c:import url="/WEB-INF/views/layout/alert.jsp" />

        <form action="/user/signup" method="post" class="w-full flex flex-col gap-2 bg-base-200 border-base-300 rounded-box border shadow p-4">
            <a href="${pageContext.request.contextPath}/" class="w-full mb-4 text-center text-4xl font-bold">SHBank</a>

            <div>
                <label class="text-xs">아이디</label>
                <input type="text" name="id" class="input w-full" placeholder="아이디" required />
            </div>

            <div class="flex justify-between gap-2">
                <div class="basis-1/2">
                    <label class="text-xs">비밀번호</label>
                    <input type="password" name="password" class="input w-full" placeholder="비밀번호" required />
                </div>

                <div class="basis-1/2">
                    <label class="text-xs">비밀번호 확인</label>
                    <input type="password" name="passwordConfirm" class="input w-full" placeholder="비밀번호 확인" required />
                </div>
            </div>

            <div>
                <label class="text-xs">성명</label>
                <input type="text" name="fullName" class="input w-full" placeholder="성명" required />
            </div>
            
            <div>
                <label class="text-xs">이메일</label>
                <input type="email" name="email" class="input w-full" placeholder="이메일" required />
            </div>
            <div class="flex justify-between gap-2">
                <div class="basis-1/2">
                    <label class="text-xs">전화번호</label>
                    <input type="text" name="phoneNumber" class="input w-full" placeholder="전화번호" required />
                </div>

                <div class="basis-1/2">
                    <label class="text-xs">나이</label>
                    <input type="number" name="age" class="input w-full" placeholder="나이" required />
                </div>
            </div>

            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}" />

            <button class="w-full btn btn-primary mt-4">가입하기</button>

            <p class="text-sm text-center">이미 계정이 있으신가요? <a href="${pageContext.request.contextPath}/user/login" class="link">로그인</a></p>
        </form>
    </main>
</body>
</html>
