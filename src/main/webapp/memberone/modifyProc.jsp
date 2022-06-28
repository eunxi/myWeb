<%@page import="tommy.web.memberone.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	request.setCharacterEncoding("UTF-8"); // 요청받아서 수정 -> request %>
<jsp:useBean id="vo" class="tommy.web.memberone.StudentVO">
	<jsp:setProperty name="vo" property="*" />
</jsp:useBean>
<%
	StudentDAO dao = StudentDAO.getInstance();
	String loginID = (String)session.getAttribute("loginID"); // session에서 loginID 가져와서 String loginID 에 넣어주기
	vo.setId(loginID); // vo 의 setId에 loginID 넣어주기 -> setProperty 로 vo 의 setId 에 넣어주는 것 !
	dao.updateMember(vo); // dao.updateMember(vo) 넣어주기
%>
<html>
<head>
<meta charset="UTF-8">
<title>Update Process</title>
</head>
<meta http-equiv="Refresh" content="3;url=login.jsp"> <%-- Refresh(입력한 주소로 몇 초 후 이동), login.jsp 로 3 초 후 이동 --%>
<body>
	<center>
		<font size="5" face="바탕체">
			입력하신 내용대로 <b>회원정보가 수정되었습니다.</b></br>
			3초 후에 Login Page 로 이동합니다.
		</font>
	</center>
</body>
</html>