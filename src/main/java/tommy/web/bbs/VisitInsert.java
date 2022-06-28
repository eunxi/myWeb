package tommy.web.bbs;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VisitInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // .jsp 또는 .html 파일에서 POST 방식으로 값 전송할 때, 전달하는 값이 한글일 경우 글자 깨짐을 방지하기 위해(브라우저 상 출력이 아닌 값 전송의 경우 = request)
		
		// request.getParameter 로 가져온 값은 write.html 에 존재하는 name 의 값이다. 클라이언트가 값을 전송(request, 요청)하는 값
		String writer = request.getParameter("writer"); // request 파라미터(writer) 가져온 후 writer 에 넣어주기
		String memo = request.getParameter("memo"); // request 파라미터(memo) 가져온 후 memo 에 넣어주기
		System.out.println("작성자 : " + writer);
		System.out.println("내용 : " + memo);
		
		StringBuffer sql = new StringBuffer();
		sql.append("insert into visit(no, writer, memo, regdate) ");
		sql.append("values (visit_seq.nextval, ?, ?, sysdate)");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // ojdbc8.jar 연동
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "mytest", "mytest");
			pstmt = con.prepareStatement(sql.toString()); // SQL 문
			pstmt.setString(1, writer);
			pstmt.setString(2, memo);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {}
		}
		response.sendRedirect("VisitList"); // VisitList 로 이동
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
