package tommy.web.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class tempMemberDAO {
//	private final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
//	private final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
//	private final String USER = "mytest";
//	private final String PASS = "mytest";
//	private ConnectionPool pool = null;
	
	public tempMemberDAO() {
//		try {
//			Class.forName(JDBC_DRIVER);
//			pool = ConnectionPool.getInstance(); // pool 객체 초기화
//		} catch (Exception e) {
//			System.out.println("Error : JDBC 드라이버 로딩 실패");
//			System.out.println("Error : Connection 얻어오기 실패");
//		}
	}
	
	// 기능 하나하나를 메소드로 구현
	private Connection getConnection() {
		Connection con = null;
		try {
			Context init = new InitialContext(); // 큰 환경을 초기화시켜주는 코드
			DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/myOracle"); // 초기화시켜준 환경에 jdbc/myOracle 을 찾아서 넣어준 다음에 DataSource 로 형 변환 시켜준다.
			con = ds.getConnection(); // con 은 ds(context.xml 에서 DB 연동한 값) 연결한 값을 받는다고 생각 -> ConnectionPool 에 준비된 Connection 객체를 빌려오는 메소드로, Connection 을 con 에 담는 코드
		}catch(NamingException ne) {
			ne.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public Vector<tempMemberVO> getMemberList(){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		Vector<tempMemberVO> vecList = new Vector<tempMemberVO>(); // Vector 초기화
		
		try {
//			con = DriverManager.getConnection(JDBC_URL, USER, PASS);
//			con = pool.getConnection();
			con = getConnection();
			String strQuery = "select * from tempMember";
			stmt = con.createStatement();
			rs = stmt.executeQuery(strQuery);
			
			while(rs.next()) {
				tempMemberVO vo = new tempMemberVO(); // vo 초기화
				vo.setId(rs.getString("id"));
				vo.setPasswd(rs.getString("passwd"));
				vo.setName(rs.getString("name"));
				vo.setMem_num1(rs.getString("mem_num1"));
				vo.setMem_num2(rs.getString("mem_num2"));
				vo.setEmail(rs.getString("e_mail"));
				vo.setPhone(rs.getString("phone"));
				vo.setZipcode(rs.getString("zipcode"));
				vo.setAddress(rs.getString("address"));
				vo.setJob(rs.getString("job"));
				vecList.add(vo);
			}
		}catch(Exception ex) {
			System.out.println("Exception " + ex);
//			ex.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(con != null) con.close();
//				pool.releaseConnection(con);
			}catch(SQLException e) {}
		}
		return vecList;
	}
}
