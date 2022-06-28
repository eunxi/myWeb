<%@page import="tommy.web.memberone.StudentDAO"%>
<%@page import="tommy.web.memberone.ZipCodeVO"%>
<%@page import="java.util.Vector"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	StudentDAO dao = StudentDAO.getInstance();
	request.setCharacterEncoding("UTF-8"); // request 에서 입력 받은 한글 깨짐 방지를 위해 작성
	String check = request.getParameter("check"); // 검색 후의 상태
	String dong = request.getParameter("dong"); // 입력한 dong 을 받아서 dong 에 넣어주기
	Vector<ZipCodeVO> zipcodeList = dao.zipcodeRead(dong); // dao 의 zipcodeRead(dong) 을 이용해서 Vector 컬렉션의 변수인 zipcodeList 에 값 넣어주기
	int totalList = zipcodeList.size(); // 우편번호 목록 크기를 int totalList 에 넣어주기(갯수)
%>
<html>
<head>
<meta charset="UTF-8">
<title>우편번호 검색</title>
<link href="style.css" rel="stylesheet" type="text/css" />
<script language="javaScript" src="script.js"></script>
<script>
	function dongCheck(){
		if(document.zipForm.dong.value == ""){
			alert("동 이름을 입력하세요!");
			doucument.zipForm.dong.focus();
			return;
		}
		document.zipForm.submit();
	}
	
	function sendAddress(zipcode, sido, gugun, dong, ri, bunji){
		var address = sido + " " + gugun + " " + dong + " " + ri + " " + bunji;
		opener.document.regForm.zipcode.value = zipcode; /* regForm 의 zipcode 에 sendAddress의 zipcode 보내주기*/
		opener.document.regForm.address1.value = address; /* regForm 의 address1 에 sendAddress의 address(총 주소) 보내주기*/
		self.close();
	}
</script>
</head>
<body bgcolor="#FDF5E6">
	<center>
		<b>우편번호 찾기</b>
		<form name="zipForm" method="post" action="zipCheck.jsp">
			<table>
				<tr>
					<td>동 이름 입력
						<input type="text" name="dong" />
						<input type="button" value="검색" onclick="dongCheck()" /> <%-- 검색 버튼 클릭 시, 집 주소 검색해야하고 해당 주소가 zipDB 에 존재하므로 가져오기 위해 DAO에서 메소드 생성 --%>
					</td>
				</tr>
			</table>
			<input type="hidden" name="check" value="n"> <!-- hidden : 사용자에게 보이지 않는 숨겨진 입력 필드 -->
		</form>
		
		<table>
		<%
			// check 가 n 라면 (dong 검색했다면 check = "n" 으로 변경)
			if(check.equals("n")){
				// 집 주소 목록이 비어있다면
				if(zipcodeList.isEmpty()){
		%>
					<tr><td align="center"><br/>검색된 결과가 없습니다.</td></tr>
		<%
				}
			else{
		%>
				<tr><td align="center"><br/>
					※ 검색 후, 아래 우편번호를 클릭하면 자동으로 입력됩니다.</td>
				</tr>
		<%
				for(int i = 0; i < totalList; i++){
					ZipCodeVO vo = zipcodeList.elementAt(i);
					String tempZipcode = vo.getZipcode();
					String tempSido = vo.getSido();
					String tempGugun = vo.getGugun();
					String tempDong = vo.getDong();
					String tempRi = vo.getRi();
					String tempBunji = vo.getBunji();
					
					if(tempRi == null) tempRi = " ";
					if(tempBunji == null) tempBunji = " ";
		%>
			<tr><td>
				<a href="javascript:sendAddress('<%=tempZipcode %>', '<%=tempSido %>', '<%=tempGugun %>', '<%=tempDong %>', '<%=tempRi %>', '<%=tempBunji %>')"> <%-- 해당 내용을 보낸다 --%>
					<%=tempZipcode %>&nbsp;<%=tempSido %>&nbsp;<%=tempGugun %>&nbsp;<%=tempDong %>&nbsp;<%=tempRi %>&nbsp;<%=tempBunji %></a><br> <%-- 내용 출력 --%>
		<%
					} // end for
				} // end else
			}
		%>
			</td></tr>
			<tr>
				<td align="center">
					<a href="javascript:this.close();">닫기</a>
				</td>
			</tr>
		</table>
	</center>
</body>
</html>