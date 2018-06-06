<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-widtb, initial-scale=1.0">
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>

<c:choose>
	<c:when test="${empty USER_SESSION}">
	<a href="${pageContext.request.contextPath}/login" target="_self">로그인</a>
	</c:when>
	<c:otherwise>
		<p>${USER_SESSION.nickname}님 환영합니다,</p>
		<a href="${pageContext.request.contextPath}/logoff" target="_self">로그아웃</a>
	</c:otherwise>
</c:choose>
</body>
</html>
