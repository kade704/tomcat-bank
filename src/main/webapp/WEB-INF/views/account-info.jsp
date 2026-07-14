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

    <main class="w-lg">
        <c:import url="/WEB-INF/views/layout/alert.jsp" />

        <div class="w-full bg-base-200 border-base-300 rounded-box border p-4 space-y-2">
            <h1>${param.accountId}의 계좌 정보</h1>

            <c:if test="${not empty account}">
                <c:set var="accountIdForBranch" value="${not empty account.id ? account.id : param.accountId}" />
                <c:set var="accountIdParts" value="${fn:split(accountIdForBranch, '-')}" />
                <c:set var="branchCode" value="${fn:length(accountIdParts) ge 2 ? accountIdParts[1] : ''}" />

                <div class="flex">
                    <p class="flex-1">계좌 ID:</p>
                    <p>${account.id}</p>
                </div>
                <div class="flex">
                    <p class="flex-1">잔액:</p>
                    <p>${account.balance}</p>
                </div>
                <div class="flex">
                    <p class="flex-1">지점:</p>
                    <p>
                        <c:choose>
                            <c:when test="${branchCode eq '111'}">서울 (111)</c:when>
                            <c:when test="${branchCode eq '112'}">부산 (112)</c:when>
                            <c:when test="${branchCode eq '113'}">대구 (113)</c:when>
                            <c:otherwise>알 수 없음 (${branchCode})</c:otherwise>
                        </c:choose>
                    </p>
                </div>
                <div class="flex">
                    <p class="flex-1">생성일:</p>
                    <p>${account.createdAt}</p>
                </div>
            </c:if>

            <form action="/account/delete" method="post" onsubmit="return confirm('정말 계좌를 삭제하시겠습니까?');">
                <input type="hidden" name="accountId" value="${param.accountId}">
                <button type="submit">계좌 삭제</button>
            </form>
        </div>
    </main>
</body>
</html>
