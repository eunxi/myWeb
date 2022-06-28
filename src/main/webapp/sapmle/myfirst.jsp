<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP File</title>
</head>
<body>
	<h2>JSP Script 예제</h2>
	<% //Scriptlet - 연산, 처리
		String scriptlet = "스크립트릿 입니다."; // 메소드 내에서만 존재하는 지역변수 st
		String comment = "주석문 입니다.";
		out.println("내장객체를 이용한 출력 : " + declation + "<br></br>"); // 여기서의 out 은 내장되어 있는 객체 그대로 사용!
	%>
	<%-- <%! 멤버변수 및 멤버메소드 선언 %> <% 자바 코드 %> <%= 출력될 내용 %> --%>
	선언문 출력하기(변수) : <%=declation %><br></br> <%-- 클래스 내의 멤버변수로 전역변수 st --%>
	선언문 출력하기(메소드) : <%=declationMethod() %><br></br>
	스크립트릿 출력하기 : <%=scriptlet %><br></br>
	<!-- JSP 에서 사용하는 HTML 주석 부분 -->
	<!-- HTML 주석 : <%=comment %> --><br></br> <%--HTML 주석으로 자바 코드 막기 불가능 --%>
	<%-- JSP 주석 : <%=comment %> --%><br></br> <%-- 주석 순위 : JSP 주석 > 자바 주석 > HTML 주석 --%>
	<%
		// 자바 주석
		/* 
			여러 줄 주석
		*/
	%>
	<%! // declation = 변수 선언
		String declation = "선언문 입니다.";
	%>
	<%! // declation = 메소드 선언
		public String declationMethod(){
			return declation;
		}
	%>
</body>
</html>