<%@page import="java.util.Map"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8"); // 입력박스에 한글이 들어갈 경우, Tomcat 에 들어갔다 나오면서 한글이 깨지므로 받는 쪽에서 한글 깨짐 방지를 위해서 작성해놓은 코드
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>요청 파라미터 출력</title>
</head>
<body>
	<b>request.getParameter() 메소드 사용</b><br></br>
	name 파마리터 = <%=request.getParameter("name") %><br></br>
	address 파라미터 = <%=request.getParameter("address") %><br></br>
	<br></br><br></br>
	
	<b>request.getParameterValues() 메소드 사용</b><br></br> <!-- getParameterValues(String name) : 체크박스 여러 개 선택한 경우와 같이 이름이 name 인 모든 파라미터의 값을 배열로 구한다. -->
	<%
		String[] values = request.getParameterValues("pet");
	
		if(values != null){
			for(int i = 0; i < values.length; i++){
	%>
	<%=values[i] %>
	<%
			}
		}
	%>
	<br></br><br></br>
	
	<b>request.getParameterNames() 메소드 사용</b><br></br> <!-- getParameterNames() : 웹브라우저가 전송한 파라미터의 이름을 구한다. -->
	<%
		Enumeration enumData = request.getParameterNames();
		while(enumData.hasMoreElements()){ // 파라미터의 이름이 존재한다면
			String name = (String)enumData.nextElement(); // 다음 요소를 String 으로 형 변환해서 구하기
	%>
	<%=name %>, <%=request.getParameter(name) %>
	<%
		}
	%>
	<br></br><br></br>
	
	<b>request.getParameterMap() 메소드 사용</b><br></br> <!-- getParameterMap() : 웹브라우저가 전송한 파라미터의 맵을 구한다. -->
	<%
		Map parameterMap = request.getParameterMap(); // key, value 이용해서 데이터가 몇 갠지 알고 얻을 수 있다.
		String[] nameParam = (String[])parameterMap.get("name");
		if(nameParam != null){
	%>
	name = <%=nameParam[0] %>
	<%
		}
	%>
</body>
</html>