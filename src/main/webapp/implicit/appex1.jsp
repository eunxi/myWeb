<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>초기화 파라미터 읽어오기</title>
</head>
<body>
초기화 파라미터 목록:
<ul>
	<%
		Enumeration enumData = application.getInitParameterNames(); // getInitParameterNames() : 파라미터의 이름 목록을 리턴
		while(enumData.hasMoreElements()){ 
			String initParamName = (String)enumData.nextElement();
	%>
	<li>
		<%-- getInitParameter(String name) : 이름이 name 인 웹어플리케이션 초기화 파라미터 값을 읽어온다. 존재하지 않을 경우, null 값 읽어온다. --%>
		<%=initParamName %> = <%=application.getInitParameter(initParamName) %>
	</li>
	<%
		}
	%>
</ul>
</body>
</html>