<%@page import="tommy.web.boardone.BoardDAO"%>
<%@page import="tommy.web.boardone.BoardVO"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="view/color.jsp" %>
<%
	int pageSize = 5; // 한 페이지에 게시물 5개 보여주겠다
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // 특정 문자열 포맷으로 얻고 싶을 때 SimpleDateFormat 클래스 사용
	
	// 수정 2
	String pageNum = request.getParameter("pageNum");
	if(pageNum == null){
		pageNum = "1";
	}
	int currentPage = Integer.parseInt(pageNum); // 현재 페이지
	int startRow = (currentPage - 1) * pageSize + 1; // 시작 번호
	int endRow = currentPage * pageSize; // 끝 번호
	
	int count = 0;
	int number = 0;
	List<BoardVO> articleList = null;
	BoardDAO dbPro = BoardDAO.getInstance();
	count = dbPro.getArticleCount(); // 전체 글 수 
	if(count > 0){
		articleList = dbPro.getArticles(startRow, endRow); // 수정 3
	}
	number = count - (currentPage - 1) * pageSize; // 수정 4, 화면에 보여질 글 번호로 DB 와 상관없다.
%>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<link href="style.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="<%=bodyback_c %>">
	<center><b>글 목록(전체 글:<%=count %>)</b>
	<table width="700">
		<tr>
			<td align="right" bgcolor="<%=value_c %>">
				<a href="writeForm.jsp">글쓰기</a>
			</td>
		</tr>
	</table>
	<% if(count == 0) { %>
	<table width="700" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td align="center">
				게시판에 저장된 글이 없습니다.
			</td>
		</tr>
	</table>
	<% } else { %>
	<table border="1" width="700" cellpadding="0" cellspacing="0" align="center">
		<tr height="30" bgcolor="<%=value_c %>">
			<td align="center" width="50">번호</td>
			<td align="center" width="250">제목</td>
			<td align="center" width="100">작성자</td>
			<td align="center" width="150">작성일</td>
			<td align="center" width="50">조회</td>
			<td align="center" width="100">IP</td>
		</tr>
		<%
			for(int i = 0; i < articleList.size(); i++){
				BoardVO article = (BoardVO)articleList.get(i);
		%>
		<tr height="30">
			<td align="center" width="50"> <%=number-- %></td>
			<td width="250">
				<%
					int wid = 0;
					if(article.getDepth() > 0){
						wid = 5 * (article.getDepth());
				%>
					<img src="images/level.gif" width="<%=wid %>" height="16">
					<img src="images/re.gif">
				<% } else { %>
					<img src="images/level.gif" width="<%=wid %>" height="16">
				<% } %>
				<a href="content.jsp?num=<%=article.getNum() %>&pageNum=<%=currentPage %>"> <%-- 현재 페이지 번호 --%>
					<!-- 수정 6 -->
					<%=article.getSubject() %>
				</a>
				<%
					if(article.getReadcount() >= 10) {
				%>
				<img src="images/hot.gif" border="0" height="16"><% } %>
			</td>
			<td align="center" width="100">
				<a href="mailto:<%=article.getEmail() %>">
					<%=article.getWriter() %>
				</a>
			</td>
			<td align="center" width="150">
				<%=sdf.format(article.getRegdate()) %>
			</td>
			<td align="center" width="50"><%=article.getReadcount() %></td>
			<td align="center" width="100"><%=article.getIp() %></td>
		</tr>
		<%} %>
	</table>
	<%} 
		// [이전]	[다음]
		if(count > 0){
			int pageBlock = 3; // 하나의 블럭에 보여질 페이지 수
			int imsi = count % pageSize == 0 ? 0 : 1; // 나머지가 0 이면 0 더하기, 나머지가 0 이 아니면 1 더하기
			int pageCount = count / pageSize + imsi; // 전체 페이지 수
			int startPage = (int)((currentPage - 1) / pageBlock) * pageBlock + 1; // 현재 페이지를 기준으로 해당 블럭의 시작 페이지 번호
			int endPage = startPage + pageBlock - 1; // 현재 페이지를 기준으로 해당 블럭의 끝 페이지 번호
			if(endPage > pageCount) endPage = pageCount; // 해당 블럭의 마지막 페이지가 존재하지 않을 경우를 대비
			if(startPage > pageBlock){  // 이전 블럭
	%>
				<a href="list.jsp?pageNum=<%=startPage - pageBlock %>">[이전]</a>
	<%
			}
			for(int i = startPage; i <= endPage; i++){ // 해당 페이지 링크 걸어두기
	%>
				<a href="list.jsp?pageNum=<%=i %>">[<%=i %>]</a>
	<%
			}
			if(endPage < pageCount) { // 다음 블럭
	%>
				<a href="list.jsp?pageNum=<%=startPage + pageBlock %>">[다음]</a>
	<%
			}
		}
	%>
	</center>
</body>
</html>