<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
	<h1>간단한 자바빈즈 프로그램</h1>
	<hr color="red"></hr><br></br>
	<form method="post" action="simpleBean.jsp">
		메세지: <input type="text" name="message">
		<input type="submit" value="전송"> <!-- 전송 클릭 시 simpleBean.jsp 로 이동 -->
	</form>
</body>
</html>