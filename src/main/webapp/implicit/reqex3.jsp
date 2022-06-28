<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>헤더 목록 출력</title>
</head>
<body>
<%
	Enumeration enumData = request.getHeaderNames(); // getHeaderNames() : 모든 헤더의 이름을 구한다.
	while(enumData.hasMoreElements()) {
		String headerName = (String)enumData.nextElement(); // 다음 요소를 headerName (String) 으로 받기
		String headerValue = request.getHeader(headerName); // getHeader(String name) : 지정한 이름의 헤더 값을 구한다.
%>
<%=headerName %> = <%=headerValue %><br></br>
<%
	}
%>
</body>
</html>