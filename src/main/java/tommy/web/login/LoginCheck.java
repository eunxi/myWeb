package tommy.web.login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id"); // Login 에서 session 없을 때 name = id
		String pwd = request.getParameter("pwd"); // Login 에서 session 없을 때 name = pwd
		System.out.println(id + ", " + pwd);
		
//		String dbID = "admin";
//		String dbPWD = "1234";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select pass from login where id = ?"); // PK 인 id 를 조건식으로 검색
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // ojdbc8.jar 를 lib 폴더에 추가
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("드라이버 로딩 실패");
		}
		
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "mytest", "mytest");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, id); // 동적쿼리문의 바인딩변수에 데이터 넣어주기
			rs = pstmt.executeQuery();

			if(rs.next()) { // 검색결과 존재할 때
				String dbPass = rs.getString("pass"); // DB 비밀번호 pass 를 dbPass 에 넣어주기
				
				if(pwd.equals(dbPass)) { // 입력한 pwd 와 dbPass 랑 같다면
					HttpSession session = request.getSession(); // 세션 객체 생성
					session.setAttribute("user", id); // session 이 id 를 가지고 user 로 생성
					System.out.println("로그인 성공"); // 로그인 성공
				}else {
					System.out.println("비밀번호 실패"); // 비밀번호 다르기 때문에, 로그인 실패
				}
			}else {
				System.out.println("ID 없다."); // 검색결과 존재하지 않을 땐 아이디도 없음 -> 여기서 SQL Developer 에서 commit 안해주면 아이디 없다고 뜨니깐 주의
			}
			response.sendRedirect("Login"); // Login 으로 이동
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("쿼리문 오류");
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {}
		}
		
//		if(dbID.equals(id) && dbPWD.equals(pwd)) { // id 와 pwd 가 dbID 와 dbPWD 와 같다면 정보 저장
//			// HttpSession 객체 생성
//			HttpSession session = request.getSession();
//			session.setAttribute("user", id); // 클라이언트의 정보를 HttpSession 객체에 저장
//		}
//		response.sendRedirect("Login");
		
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
