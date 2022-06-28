<%@page import="tommy.web.memberone.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	StudentDAO dao = StudentDAO.getInstance();
	String id = request.getParameter("id"); // 입력 받은 id 를 가져와서 String id 에 받아주기
	boolean check = dao.idCheck(id); // dao 의 idCheck에 id 를 넣어서, boolean check 에 받아주기
%>
<html>
<head>
<meta charset="UTF-8">
<title>ID 중복체크</title>
<link href="style.css" rel="stylesheet" type="text/css" />
<script language="javaScript" src="script.js"></script>
</head>
<body bgcolor="#FDF5E6">
	<br>
	<center>
		<b><%=id %></b>
		<%
			if(check){
				out.println(" 는 이미 존재하는 ID 입니다.<br></br>");
			}else{
				out.println(" 는 사용 가능합니다.<br></br>");
			}
		%>
	<a href="#" onClick="javascript:self.close()">닫기</a>
	</center>
</body>
</html>