<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>폼 생성</title>
</head>
<body>
폼에 데이터를 입력한 후 '전송' 버튼을 클릭하세요.<br></br>
	<form action="viewParameter.jsp" method="post"> <!-- viewParameter.jsp 로 POST 방식으로 페이지 이동, POST 방식은 URL 에서 ? 뒤에 값 안보인다는 특징O -->
		이름: <input type="text" name="name" size="10"><br></br>
		주소: <input type="text" name="address" size="30"><br></br>
		좋아하는 동물:
			<input type="checkbox" name="pet" value="dog">강아지 <!-- checkbox 에서의 name 은 동일해야 선택 가능 -->
			<input type="checkbox" name="pet" value="cat">고양이
			<input type="checkbox" name="pet" value="pig">돼지
		<br></br>
		<input type="submit" value="전송">	
	</form>
</body>
</html>