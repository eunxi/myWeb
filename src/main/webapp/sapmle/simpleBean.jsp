<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
//	String message = request.getParameter("message"); // 자바빈즈 활용 안했다면, 해당 코드로 사용
%>
<%-- 객체 생성, SimpleData msg = new SimpleData(); --%>
<jsp:useBean id="msg" class="tommy.web.sample.SimpleData" />
<%-- 객체의 세터메소드 활용, msg.setMessage(?); --%>
<%-- property="*" 이렇게 작성해줄 경우, 모든 내용을 가져다 사용 가능하다는 특징 존재 --%>
<jsp:setProperty name="msg" property="message" />
<!-- 주의점: 기본자료형, String 만 가능 -->
<html>
<body>
	<h1>간단한 빈즈 프로그램 결과</h1>
	<hr color="red"></hr><br></br>
	<font size="5">
		<%-- 객체의 게터메소드 활용, <%=message %> --%>
		메세지: <jsp:getProperty name="msg" property="message" />
	</font>
</body>
</html>