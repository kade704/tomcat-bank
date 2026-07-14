<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<div>
    <c:if test="${not empty sessionScope.message}">
        <div class="alert alert-success mb-4">
            ${sessionScope.message}
        </div>
        <c:remove var="message" scope="session"/>
    </c:if>

    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-error mb-4">
            ${sessionScope.error}
        </div>
        <c:remove var="error" scope="session"/>
    </c:if>
</div>