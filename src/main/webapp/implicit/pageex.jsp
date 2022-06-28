<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>pageContext 기본 객체</title>
</head>
<body>
<%
	HttpServletRequest httpRequest = (HttpServletRequest)pageContext.getRequest(); // getRequest() : request 내장 객체 구하기
%>
request 기본 객체와 pageContext.getRequest() 의 동일 여부:
<%=request == httpRequest %> <%-- == : 대상의 주소 값 비교, equals : 대상의 내용 자체 비교 --%>
<br></br>
pageContext.getOut() 메소드를 사용한 데이터 출력:
<% pageContext.getOut().println("안녕하세요!"); %> <%-- getOut() : out 내장 객체 구하기 --%>
</body>
</html>