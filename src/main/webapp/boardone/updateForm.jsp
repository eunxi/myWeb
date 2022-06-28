<%@page import="tommy.web.boardone.BoardVO"%>
<%@page import="tommy.web.boardone.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="view/color.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<link href="style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="script.js"></script>
</head>
<%
	int num = Integer.parseInt(request.getParameter("num")); // 입력받는 num 을 가져와서 정수 형태로 변환 후 대입
	String pageNum = request.getParameter("pageNum"); // 페이지 번호
	
	try{
		BoardDAO dbPro = BoardDAO.getInstance();
		BoardVO article = dbPro.updateGetArticle(num); // 수정 시 필요한 메소드인 updateArticle(num) 호출
%>
<body bgcolor="<%=bodyback_c %>">
<center><b>글 수정</b><br></br>
	<form method="post" name="writeForm" action="updateProc.jsp?pageNum=<%=pageNum %>" onsubmit="return writeSave()">
		<table width="500" border="1" cellspacing="0" cellpadding="0" bgcolor="<%=bodyback_c %>" align="center">
		<tr>
			<td width="70" bgcolor="<%=value_c %>" align="center">이름</td>
			<td align="left" width="330">
				<input type="text" size="10" maxlength="10" name="writer" value="<%=article.getWriter() %>">
				<input type="hidden" name="num" value="<%=article.getNum() %>"> <%-- hidden 태그를 활용해서 num 값은 계속 가져가기 --%>
			</td>
		</tr>
		<tr>
			<td width="70" bgcolor="<%=value_c %>" align="center">제목</td>
			<td align="left" width="330">
				<input type="text" size="40" maxlength="50" name="subject" value="<%=article.getSubject() %>">
			</td>
		</tr>
		<tr>
			<td width="70" bgcolor="<%=value_c %>" align="center">Email</td>
			<td align="left" width="330">
				<input type="text" size="40" maxlength="30" name="email" value="<%=article.getEmail() %>">
			</td>
		</tr>
		<tr>
			<td width="70" bgcolor="<%=value_c %>" align="center">내용</td>
			<td align="left" width="330">
				<textarea name="content" rows="13" cols="50"><%=article.getContent() %></textarea>
			</td>
		</tr>
		<tr>
			<td width="70" bgcolor="<%=value_c %>" align="center">비밀번호</td>
			<td align="left" width="330">
				<input type="password" size="8" maxlength="12" name="pass">
			</td>
		</tr>
		<tr>
			<td colspan=2 bgcolor="<%=value_c %>" align="center">
				<input type="submit" value="글 수정">
				<input type="reset" value="다시 작성">
				<input type="button" value="목록 보기" onclick="document.location.href='list.jsp?pageNum=<%=pageNum %>'">
			</td>
		</tr>
		</table>
	</form>
</center>
<% }catch(Exception e){} %>
</body>
</html>