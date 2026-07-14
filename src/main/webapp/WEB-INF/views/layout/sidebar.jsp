<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<div class="fixed top-0 left-0 w-64 h-screen p-4">
    <div class="w-full h-full flex flex-col bg-base-200 shadow-sm border-base-300 rounded-box border p-4">
        <div class="flex-none my-4">
            <a href="${pageContext.request.contextPath}/" class="w-full btn btn-ghost text-4xl"><h1><span class="opacity-75">SH</span>Bank</h1></a>
        </div>
        <div class="flex-1">
            <c:if test="${not empty sessionScope.userId}">
                <ul class="space-y-2">
                    <li>
                        <a href="${pageContext.request.contextPath}/user/info" class="w-full btn btn-ghost">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><g fill="none" stroke="currentColor" stroke-width="2"><path stroke-linejoin="round" d="M4 18a4 4 0 0 1 4-4h8a4 4 0 0 1 4 4a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2Z"/><circle cx="12" cy="7" r="3"/></g></svg>
                            <p>내 정보</p>
                        </a>
                    </li>
                   <li>
                        <a href="${pageContext.request.contextPath}/account/list" class="w-full btn btn-ghost">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><path fill="currentColor" d="M5 3a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-2.28A2 2 0 0 0 22 15V9a2 2 0 0 0-1-1.72V5a2 2 0 0 0-2-2zm0 2h14v2h-6a2 2 0 0 0-2 2v6a2 2 0 0 0 2 2h6v2H5zm8 4h7v6h-7zm3 1.5a1.5 1.5 0 0 0-1.5 1.5a1.5 1.5 0 0 0 1.5 1.5a1.5 1.5 0 0 0 1.5-1.5a1.5 1.5 0 0 0-1.5-1.5"/></svg>
                            <p>내 계좌</p>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/account/create" class="w-full btn btn-ghost">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><path fill="currentColor" d="M3 0v3H0v2h3v3h2V5h3V3H5V0zm7 3v2h9v2h-6c-1.1 0-2 .9-2 2v6c0 1.1.9 2 2 2h6v2H5v-9H3v9a2 2 0 0 0 2 2h14c1.1 0 2-.9 2-2v-2.28A2 2 0 0 0 22 15V9a2 2 0 0 0-1-1.72V5c0-1.1-.9-2-2-2zm3 6h7v6h-7zm3 1.5a1.5 1.5 0 0 0-1.5 1.5a1.5 1.5 0 0 0 1.5 1.5a1.5 1.5 0 0 0 1.5-1.5a1.5 1.5 0 0 0-1.5-1.5"/></svg>
                            <p>계좌 개설</p>
                        </a>
                    </li>
                </ul>
            </c:if>
        </div>
        <div class="flex-none">
            <ul class="space-y-2">
                <li>
                    <button class="w-full btn btn-ghost" onclick="triggerTheme()">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20"><path fill="currentColor" d="M10 3.5a6.5 6.5 0 1 1 0 13zM10 2a8 8 0 1 0 0 16a8 8 0 0 0 0-16"/></svg>
                        <p>테마 변경</p>
                    </button>
                </li>
                <c:if test="${not empty sessionScope.userId}">
                    <li>
                        <form action="${pageContext.request.contextPath}/user/logout" method="post">
                            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}" />
                            <button type="submit" class="w-full btn btn-ghost">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><path fill="currentColor" d="M5 5h6c.55 0 1-.45 1-1s-.45-1-1-1H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h6c.55 0 1-.45 1-1s-.45-1-1-1H5z"/><path fill="currentColor" d="m20.65 11.65l-2.79-2.79a.501.501 0 0 0-.86.35V11h-7c-.55 0-1 .45-1 1s.45 1 1 1h7v1.79c0 .45.54.67.85.35l2.79-2.79c.2-.19.2-.51.01-.7"/></svg>
                                <p>로그아웃</p>
                            </button>
                        </form>
                    </li>
                </c:if>
                <c:if test="${empty sessionScope.userId}">
                    <li>
                        <a href="${pageContext.request.contextPath}/user/login" class="w-full btn btn-ghost">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><path fill="currentColor" d="M10.3 7.7a.984.984 0 0 0 0 1.4l1.9 1.9H3c-.55 0-1 .45-1 1s.45 1 1 1h9.2l-1.9 1.9a.984.984 0 0 0 0 1.4c.39.39 1.01.39 1.4 0l3.59-3.59a.996.996 0 0 0 0-1.41L11.7 7.7a.984.984 0 0 0-1.4 0M20 19h-7c-.55 0-1 .45-1 1s.45 1 1 1h7c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2h-7c-.55 0-1 .45-1 1s.45 1 1 1h7z"/></svg>
                            <p>로그인</p>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/user/signup" class="w-full btn btn-ghost">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><path fill="currentColor" d="M15 14c-2.67 0-8 1.33-8 4v2h16v-2c0-2.67-5.33-4-8-4m-9-4V7H4v3H1v2h3v3h2v-3h3v-2m6 2a4 4 0 0 0 4-4a4 4 0 0 0-4-4a4 4 0 0 0-4 4a4 4 0 0 0 4 4"/></svg>
                            <p>회원가입</p>
                        </a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</div>