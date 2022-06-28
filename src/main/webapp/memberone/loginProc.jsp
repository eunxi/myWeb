<%@page import="tommy.web.memberone.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	StudentDAO dao = StudentDAO.getInstance();
	String id = request.getParameter("id"); // id 가져와 !
	String pass = request.getParameter("pass"); // pass 가져와 !
	int check = dao.loginCheck(id, pass); // dao.loginCheck(id, pass) 넘겨서 int check 에 넣어줘 !
	
	if(check == 1){ // 로그인 성공
		session.setAttribute("loginID", id); // session 에 값 넣어주기 (String name, Object obj)
		response.sendRedirect("login.jsp"); // login.jsp 로 보내기 
	}else if(check == 0){ // 비밀번호 안적었을 때
%>
<script>
	alert("비밀번호가 틀렸습니다.");
	history.go(-1);
</script>
<%
	}else{ // 아이디 자체가 존재하지 않는 경우
%>
<script>
	alert("아이디가 존재하지 않습니다.");
	history.go(-1);
</script>
<%
	}
%>