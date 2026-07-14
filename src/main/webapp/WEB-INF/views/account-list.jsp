<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

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

        <a href="${pageContext.request.contextPath}/account/create" class="w-full btn btn-primary mb-4">계좌 생성</a>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <c:if test="${not empty accounts}">
                <c:forEach var="account" items="${accounts}" varStatus="status">
                    <a href="${pageContext.request.contextPath}/transaction/deposit?accountId=${account.id}">
                        <div class="stats bg-base-200 shadow w-full">
                            <div class="stat">
                                <div class="stat-title">${status.index + 1}</div>
                                <div class="stat-value"><fmt:formatNumber value="${account.balance}" type="currency" currencySymbol="₩" /></div>
                                <div class="stat-desc">${account.id}</div> 
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </c:if>
        </div>

        <c:if test="${empty accounts}">
            <p>계좌가 없습니다. 계좌를 생성하세요.</p>
        </c:if>
    </main>
</body>
</html>
