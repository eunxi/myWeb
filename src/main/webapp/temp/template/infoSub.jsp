<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String type = request.getParameter("type"); // type 이 파라미터 "type" 의 A 를 받는다.
	if(type != null) { // 해당 type 이 null 인지 아닌지 조건문으로 구분짓기
%>
<br>
<table width="100%" border="1" cellpadding="0" cellspacing="0">
	<tr>
		<td>타입</td>
		<td><b><%=type %></b></td>
	</tr>	
	<tr>
		<td>특징</td>
		<td>
			<% if(type.equals("A")) {%>
				강한 내구성
			<% }else if(type.equals("B")) {%>
				뛰어난 대처 능력
			<% } %>
		</td>
	</tr>
</table>
<%
	}
%>
