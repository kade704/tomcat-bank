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

    <main class="ml-64 mt-4 w-lg">
        <c:import url="/WEB-INF/views/layout/alert.jsp" />

        <div class="bg-base-200 p-8 border-base-300 rounded-box border shadow flex flex-col gap-4">
            <div class="w-full join">
                <a href="${pageContext.request.contextPath}/transaction/deposit?accountId=${account.id}" class="flex-1 btn join-item">입금</a>
                <a href="${pageContext.request.contextPath}/transaction/withdraw?accountId=${account.id}" class="flex-1 btn join-item">출금</a>
                <a href="${pageContext.request.contextPath}/transaction/transfer?accountId=${account.id}" class="flex-1 btn join-item btn-accent">이체</a>
            </div>

            <div>
                <p class="text-sm">${account.id}</p>
                <h2 class="text-3xl font-bold"><fmt:formatNumber value="${account.balance}" type="currency" currencySymbol="₩" /></h2>
            </div>

            <form action="/transaction/transfer" method="post">
                <input type="hidden" name="accountId" value="${account.id}">
                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}" />

                <div>
                    <label class="text-xs">이체할 금액</label>
                    <input type="number" name="amount" min="1" max="1000000" class="input input-bordered w-full" placeholder="금액을 입력하세요 (1~1,000,000)" required>
                </div>

                <div>
                    <label class="text-xs">이체할 계좌 ID</label>
                    <input type="text" name="accountIdTransfer" class="input input-bordered w-full" placeholder="계좌 ID를 입력하세요 (000-000-000000)" pattern="\d{3}-\d{3}-\d{6}" required>
                </div>

                <button type="submit" class="w-full btn btn-accent mt-4">이체</button>
            </form>

            <a href="${pageContext.request.contextPath}/transaction/list?accountId=${account.id}" class="w-full text-center link">거래 내역 보기</a>
        </div>
    </main>
</body>
</html>
