<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String loginID = (String)session.getAttribute("loginID");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link href="style.css" type="text/html" rel="stylesheet" />
<script language="javascript" src="script.js"></script>
</head>
<body>
<% if(loginID != null) { %> <%-- loginID 가 있다면 (로그인 O) --%>
	<table border="1" width="300" align="center">
		<tr>
			<td colspan="3" align="center">
				<%=loginID %>님 환영합니다.
			</td>
		</tr>
		<tr>
			<td align="center" width="100">
				<a href="modifyForm.jsp">정보수정</a>
			</td>
			<td align="center" width="100">
				<a href="deleteForm.jsp">회원탈퇴</a>
			</td>
			<td align="center" width="100">
				<a href="logout.jsp">로그아웃</a>			
			</td>
		</tr>
	</table>
<%}else { %> <%-- loginID 가 없다면 (로그인 X) --%>
	<form method="post" action="loginProc.jsp" name="loginForm">
		<table width="300" border="1">
			<tr>
				<td colspan="2" align="center">회원 로그인</td>
			</tr>
			<tr>
				<td align="right" width="75">아이디</td>
				<td width="200">&nbsp;&nbsp;
					<input type="text" name="id" size="20" />
				</td>
			</tr>
			<tr>
				<td align="right" width="75">비밀번호</td>
				<td width="200">&nbsp;&nbsp;
					<input type="password" name="pass" size="20" />
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="로그인" onClick="checkValue()" />&nbsp;&nbsp;
					<input type="button" value="회원가입" onClick="javascript:window.location='regForm.jsp'" />
				</td>
			</tr>
		</table>
	</form>
<% } %>
</body>
</html>