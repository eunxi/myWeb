<%@page import="tommy.web.boardone.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="article" scope="page" class="tommy.web.boardone.BoardVO">
	<jsp:setProperty name="article" property="*" />
</jsp:useBean>
<%
	String pageNum = request.getParameter("pageNum"); // 페이지 번호
	BoardDAO dbPro = BoardDAO.getInstance(); // BoardDAO 객체 생성
	int check = dbPro.updateArticle(article); // dbPro.updateArticle(article) 받아와서 int check 에 넣엊귀
	if(check == 1) { // check 가 1 이라면,  수정 성공
%>
<meta http-equiv="Refresh" content="0;url=list.jsp?pageNum=<%=pageNum %>">
<% }else { %>
<script type="text/javascript">
	alert("비밀번호가 맞지 않습니다.");
	history.go(-1);
</script>
<% } %>