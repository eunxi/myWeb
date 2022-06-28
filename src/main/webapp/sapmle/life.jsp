<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
	private int numOne = 0;
	public void jspInit(){ // 메소드 재정의
		System.out.println("jspInit() 호출");
	}
	public void jspDestory(){ // 메소드 재정의
		System.out.println("jspDestroy() 호출");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP Life Cycle</title>
</head>
<body>
<%
	int numTwo = 0;
	numOne++; // 새로고침할 때마다 증가
	numTwo++; // 새로고침할 때마다 초기화 -> int numTwo = 0 이기 때문에, 계속해서 0 -> 1 만 출력
	System.out.println(session.getId()); // session 객체의 id 가져오기
%>
<ul>
	<li>Number One : <%=numOne %></li>
	<li>Number Two : <%=numTwo %></li>
</ul>
</body>
</html>