<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>서버 정보 출력</title>
</head>
<body>
서버 정보: <%=application.getServerInfo() %><br></br> <!-- getServerInfo() : 서버 정보 구하기 -->
서블릿 규약 메이저 버전:
	<%=application.getMajorVersion() %><br></br> <!-- getMajorVersion() : 서버가 지원하는 서블릿 규약의 메이저 버전 리턴 -->
서블릿 규약 마이너 버전:
	<%=application.getMinorVersion() %><br></br> <!-- getMinorVersion() : 서버가 지원하는 서블릿 규약의 마이너 버전 리턴 -->
실제 경로:
	<!-- getRealPath(String path) : 웹 어플리케이션 내에서 지정한 경로에 해당하는 자원의 시스템 상에서의 자원 경로 리턴 -->
	<%=application.getRealPath("/") %><br></br> <!-- / : localhost:8080 의미 -->
</body>
</html>