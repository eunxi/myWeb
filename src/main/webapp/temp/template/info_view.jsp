<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<table width="100%" border="1" cellpadding="0" cellspacing="0">
	<tr>
		<td>제품번호</td>
		<td>XXXX</td>
	</tr>
	<tr>
		<td>가격</td>
		<td>10,000원</td>
	</tr>
</table>

<!-- 파라미터 type 을 A 라고 준다. -->
<jsp:include page="infoSub.jsp" flush="false">
	<jsp:param name="type" value="B" /> 
</jsp:include>
