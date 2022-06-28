<%@page import="tommy.web.boardone.BoardDAO"%>
<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="article" scope="page" class="tommy.web.boardone.BoardVO">
	<jsp:setProperty name="article" property="*" />
</jsp:useBean>
<%
	article.setRegdate(new Timestamp(System.currentTimeMillis()) ); // 현재 시간 구하는 방법
	article.setIp(request.getRemoteAddr()); // ip 얻어오기
	BoardDAO dbPro = BoardDAO.getInstance(); // BoardDAO 객체 생성
	dbPro.insertArticle(article); // dao.insertArticle(article) 메소드
	response.sendRedirect("list.jsp"); // list.jsp 로 이동
%>
