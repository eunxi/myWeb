<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="tommy.web.actiontag.Customer" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="customer" class="tommy.web.actiontag.Customer" scope="page" />
<jsp:setProperty name="customer" property="*" />
<html>
<head>
<meta charset="UTF-8">
<title>Customer 가입정보</title>
</head>
<body>
	<ul>
		<li>이름:
			<jsp:getProperty name="customer" property="name" />
		</li>
		<li>이메일:
			<jsp:getProperty name="customer" property="email" />
		</li>
		<li>전화:
			<jsp:getProperty name="customer" property="phone" />
		</li>
	</ul>
</body>
</html>