<%@page import="tommy.web.memberone.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="vo" class="tommy.web.memberone.StudentVO" />
<jsp:setProperty name="vo" property="*" />
<%
	StudentDAO dao = StudentDAO.getInstance();
	boolean flag = dao.memberInsert(vo); // dao.memberInsert(vo) 의 데이터를 flag 에 넣어주기 -> 값 있으면 true, 없으면 false
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 확인</title>
<link href="style.css" rel="stylesheet" type="text/html" >
</head>
<body bgcolor="#FDF5E6">
	<br></br>
	<center>
	<%
		if(flag){
			out.println("<b>회원가입을 축하드립니다.</b><br/>");
			out.println("<a href=login.jsp>로그인</a>");
		}else{
			out.println("<b>다시 입력해주세요.</b><br/>");
			out.println("<a href=regForm.jsp>다시 가입<a>");
		}
	%>
	</center>
</body>
</html>