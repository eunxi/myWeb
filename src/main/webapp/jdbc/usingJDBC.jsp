<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// DB 연동
	Class.forName("oracle.jdbc.driver.OracleDriver");
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	String id = "", passwd = "", name = "", mem_num1 = "", mem_num2 = "", e_mail = "", phone = "", zipcode = "", address = "", job = "";
	int counter = 0;
	
	try{
		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "mytest", "mytest");
		stmt = con.createStatement();
		rs = stmt.executeQuery("select * from tempmember");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 에서 데이터베이스 연동</title>
<link href="style.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="#FDF5E6">
	<h2>JSP 스크립틀릿에서 데이터베이스 연동 예제입니다.</h2>
	<h3>회원정보</h3>
	<table bordercolor="#0000ff" border="1">
		<tr>
			<td><strong>ID</strong></td>
			<td><strong>PASSWD</strong></td>
			<td><strong>NAME</strong></td>
			<td><strong>MEM_NUM1</strong></td>
			<td><strong>MEM_NUM2</strong></td>
			<td><strong>E_MAIL</strong></td>
			<td><strong>PHONE</strong></td>
			<td><strong>ZIPCODE</strong>/<strong>ADDRESS</strong></td>
			<td><strong>JOB</strong></td>
		</tr>
		<%
			if(rs != null){
				while(rs.next()){
					id = rs.getString("id");
					passwd = rs.getString("passwd");
					name = rs.getString("name");
					mem_num1 = rs.getString("mem_num1");
					mem_num2 = rs.getString("mem_num2");
					e_mail = rs.getString("e_mail");
					phone = rs.getString("phone");
					zipcode = rs.getString("zipcode");
					address = rs.getString("address");
					job = rs.getString("job");
		%>
		<tr>
			<td><%=id %></td>
			<td><%=passwd %></td>
			<td><%=name %></td>
			<td><%=mem_num1 %></td>
			<td><%=mem_num2 %></td>
			<td><%=e_mail %></td>
			<td><%=phone %></td>
			<td><%=zipcode %>/<%=address %></td>
			<td><%=job %></td>
			<%
					counter++; // 레코드 한 번씩 돌 때마다 +1
				} // end while
			} // end if
			%>
		</tr>
	</table>
	<br></br> total records : <%=counter %>
	<%
		}catch(SQLException sqlException){
			System.out.println("sql exception");
		}catch(Exception exception){
			System.out.println("exception");
		}finally{
			try{
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(con != null) con.close();
			}catch(SQLException ex){}
		}
	%>
</body>
</html>