<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// pageContext Scope 에 속성 저장하기 -> 해당 JSP 내
	pageContext.setAttribute("pageAttribute", "은지");
	// pageContext.setAttribute("pageAttribute", "은지", PageContext.PAGE_SCOPE);
	
	// request Scope 에 속성 저장하기 -> 어플리케이션에서 Request 에 접근 가능한 것들
	request.setAttribute("requestAttribute", "010-4804-4804");
	// pageContext.setAttribute("requestAttribute", "010-4804-4804", PageContext.PAGE_SCOPE);
	
	// session Scope 에 속성 저장하기 -> 특정 세션에 접근할 수 있는 서블릿이나 JSP
	session.setAttribute("sessionAttribute", "eunxi@world.com");
	// pageContext.setAttribute("sessionAttribute", "eunxi@world.com", PageContext.PAGE_SCOPE);
	
	// application Scope 에 속성 저장하기 -> 웹 어플리케이션 내의 모든 것들
	application.setAttribute("applicationAttribute", "KG");
	// pageContext.setAttribute("applicationAttribute", "KG", PageContext.PAGE_SCOPE);
%>
<html>
<head>
<meta charset="UTF-8">
<title>Scope Example</title>
</head>
<body>
	<ul>
		<li>이름: <%=pageContext.getAttribute("pageAttribute") %></li>
		<li>전화번호: <%=request.getAttribute("requestAttribute") %></li>
		<li>이메일: <%=session.getAttribute("sessionAttribute") %></li>
		<li>회사: <%=application.getAttribute("applicationAttribute") %></li>
	</ul>
</body>
</html>