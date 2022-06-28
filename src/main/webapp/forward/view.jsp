<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String code = request.getParameter("code"); // request 에서 "code" 를 code 에 받는다.
	String viewPageURI = null; // viewPageURI 는 null 값
	
	if(code.equals("A")){ // code 의 value 의 A 와 "A" 가 같다면 viewPageURI 에 값 대입해주는 형식 !
		viewPageURI = "viewModule/a.jsp";
	}else if(code.equals("B")){
		viewPageURI = "viewModule/b.jsp";
	}else if(code.equals("C")){
		viewPageURI = "viewModule/c.jsp";
	}
%>
<!-- 아래 코드로 인해서, viewPageURI/무슨.jsp 로 이동하는 것을 URL 에서 보여주지 않는다.
	따라서 URL 에 view.jsp?code=B 라고만 출력되는 것을 확인 가능 -->
<jsp:forward page="<%=viewPageURI %>" />