package tommy.web.bbs;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VisitList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8"); // 웹 브라우저에게 전달 (UTF-8 문자코드로 전달하겠다!), 해당 코드 없으면 글자 깨짐 현상 발생
		PrintWriter out = response.getWriter();
		
		try {
			out.print("<html>");
			out.print("<head><title>방명록 리스트</title></head>");
			out.print("<body>");
			
			StringBuffer sql = new StringBuffer();
			sql.append("select no, writer, memo, regdate ");
			sql.append("from visit ");
			sql.append("order by no desc"); // 최신 글이 먼저 출력되도록 desc 로 역순으로 검색 기능!
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "mytest", "mytest");
				pstmt = con.prepareStatement(sql.toString());
				rs = pstmt.executeQuery(); // SQL 문 결과를 rs 에 담아준다. (select 문이므로 ResultSet 이용)
				
				while(rs.next()) { // 값이 있을 경우, no, writer, memo, regdate 변수에 값 다 담아주기
					int no = rs.getInt("no");
					String writer = rs.getString("writer");
					String memo = rs.getString("memo");
					java.sql.Date regdate = rs.getDate("regdate");
					
					out.print("<table align=center width=500 border=1>");
					out.print("<tr>");
					out.print("<th width=50>번호</th>");
					out.print("<td width=50 align=center>" + no + "</td>");
					out.print("<th width=70>작성자</th>");
					out.print("<td width=180 align=center>" + writer + "</td>");
					out.print("<th width=50>날짜</th>");
					out.print("<td width=100 align=center>" + regdate + "</td>");
					out.print("</tr>");
					out.print("<tr>");
					out.print("<th width=50>내용</th>");
					out.print("<td colspan=5>&nbsp;<textarea rows=3 cols=50>" + memo + "</textarea></td>");
					out.print("</tr>");
					out.print("</table>");
					out.print("<p>");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}finally {
				try {
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(con != null) con.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			out.print("<p align=center><a href=/myWeb/bbs/write.html>글쓰기</a></p>");
			out.print("</body>");
			out.print("</html>");
		}finally {
			out.close();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
}
