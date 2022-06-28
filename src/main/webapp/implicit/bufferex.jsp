<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int bufferSize = out.getBufferSize(); // getBufferSize() : 버퍼의 크기 구하기
	int remainSize = out.getRemaining(); // getRemaining() : 현재 버퍼의 남은 크기 구하기
	int usedSize = bufferSize - remainSize;
%>
<html>
<body>
버퍼 전체 크기: <%=bufferSize %><br></br>
사용한 버퍼 크기: <%=usedSize %><br></br>
남은 버퍼: <% out.println(remainSize); %>byte<br></br>
남은 버퍼: <%=remainSize %><br></br>
</body>
</html>