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
<body>
    <c:import url="/WEB-INF/views/layout/sidebar.jsp" />

    <main class="ml-64 mt-4 w-4xl">
        <c:import url="/WEB-INF/views/layout/alert.jsp" />

        <div class="bg-base-200 p-8 border-base-300 rounded-box border shadow flex flex-col gap-4">
            <h1 class="text-sm font-bold mb-4">${param.accountId}의 거래 내역</h1>
            <c:if test="${not empty transactions}">
                <table class="table">
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>유형</th>
                            <th>거래 금액</th>
                            <th>거래 후 잔액</th>
                            <th>거래 일시</th>
                            <th>상대방 계좌</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="transaction" items="${transactions}" varStatus="status">
                            <tr>
                                <td>${fn:length(transactions) - status.index}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${transaction.type == 'DEPOSIT'}">입금</c:when>
                                        <c:when test="${transaction.type == 'WITHDRAW'}">출금</c:when>
                                        <c:when test="${transaction.type == 'TRANSFER_IN'}">이체 (수신)</c:when>
                                        <c:when test="${transaction.type == 'TRANSFER_OUT'}">이체 (발신)</c:when>
                                        <c:otherwise>${transaction.type}</c:otherwise>
                                    </c:choose>
                                </td>
                                <td><c:if test="${transaction.amount > 0}">+</c:if><fmt:formatNumber value="${transaction.amount}" type="currency" currencySymbol="₩" /></td>
                                <td><fmt:formatNumber value="${transaction.balanceAfter}" type="currency" currencySymbol="₩" /></td>
                                <td>${fn:replace(transaction.createdAt, 'T', ' ')}</td>
                                <td>${transaction.accountIdTransfer}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${empty transactions}">
                <p>거래 내역이 없습니다.</p>
            </c:if>
        </div>
    </main>
</body>
</html>
