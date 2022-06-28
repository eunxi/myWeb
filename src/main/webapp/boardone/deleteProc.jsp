<%@page import="tommy.web.boardone.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8"); // 입력받는 내용에 한글 깨짐 방지를 위해
	int num = Integer.parseInt(request.getParameter("num")); // 글 번호(num) 를 int num 에 받기
	String pageNum = request.getParameter("pageNum"); // 페이지 번호
	String pass = request.getParameter("pass"); // 비밀번호
	BoardDAO dbPro = BoardDAO.getInstance(); // BoardDAO 객체 생성
	int check = dbPro.deleteArticle(num, pass); // dbPro.deleteArticle(num, pass) 로 int check 에 넣어주기
	if(check == 1){ // 삭제 성공
%>
	<%-- 페이지 번호로 이동하기 --%>
	<meta http-equiv="Refresh" content="0;url=list.jsp?pageNum=<%=pageNum %>">
<% }else{ %>
	<script>
		<!--
			alert("비밀번호가 맞지 않습니다.");
			history.go(-1);
		-->
	</script>
<% } %>