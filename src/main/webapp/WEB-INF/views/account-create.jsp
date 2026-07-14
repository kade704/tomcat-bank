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
<body>
    <c:import url="/WEB-INF/views/layout/sidebar.jsp" />

    <main class="ml-64 mt-4 w-lg">
        <c:import url="/WEB-INF/views/layout/alert.jsp" />

        <form action="/account/create" method="post" class="bg-base-200 p-8 border-base-300 rounded-box border shadow flex flex-col gap-4">
            <h1 class="text-2xl font-bold">새 계좌 생성</h1>
            <select name="branchId" class="select" required>
                <option value="">지점 선택</option>
                <option value="111">서울 지점 (111)</option>
                <option value="112">부산 지점 (112)</option>
                <option value="113">대구 지점 (113)</option>
            </select>
            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}" />
            <button type="submit" class="w-full btn btn-primary">새 계좌 생성</button>
        </form>
    </main>
</body>
</html>
