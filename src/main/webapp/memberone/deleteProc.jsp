<%@page import="tommy.web.memberone.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴</title>
</head>
<%
	StudentDAO dao = StudentDAO.getInstance();
	String id = (String)session.getAttribute("loginID"); // session 에서 loginID 가져와서 String id 에 넣어주기
	String pass = request.getParameter("pass"); // request 에서 입력한 pass 가져와서 String pass 에 넣어주기
	int check = dao.deleteMember(id, pass); // dao.deleteMember(id, pass) 가져와서 int check 에 넣어주기 -> 메소드가 정수형으로 반환해주기 때문에
	if(check == 1){ // 만약 check 가 1 이라면, session 초기화로 회원 탈퇴시켜주기
		session.invalidate();
%>
<meta http-equiv="Refresh" content="3;url=login.jsp">
<body>
	<center>
		<font size="5" face="바탕체">
			회원정보가 삭제되었습니다.</br>
			안녕히가세요! 😭 </br>
			3초 후 Login Page 로 이동합니다.
		</font>
	</center>
<%	}else{ // check 가 -1 이 아니라면 %>
<script>
	alert("비밀번호가 맞지 않습니다.");
	history.go(-1);
</script>
<% } %>
</body> 
</html>