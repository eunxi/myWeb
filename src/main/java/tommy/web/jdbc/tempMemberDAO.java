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
//			pool = ConnectionPool.getInstance(); // pool ��ü �ʱ�ȭ
//		} catch (Exception e) {
//			System.out.println("Error : JDBC ����̹� �ε� ����");
//			System.out.println("Error : Connection ������ ����");
//		}
	}
	
	// ��� �ϳ��ϳ��� �޼ҵ�� ����
	private Connection getConnection() {
		Connection con = null;
		try {
			Context init = new InitialContext(); // ū ȯ���� �ʱ�ȭ�����ִ� �ڵ�
			DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/myOracle"); // �ʱ�ȭ������ ȯ�濡 jdbc/myOracle �� ã�Ƽ� �־��� ������ DataSource �� �� ��ȯ �����ش�.
			con = ds.getConnection(); // con �� ds(context.xml ���� DB ������ ��) ������ ���� �޴´ٰ� ���� -> ConnectionPool �� �غ�� Connection ��ü�� �������� �޼ҵ��, Connection �� con �� ��� �ڵ�
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
		
		Vector<tempMemberVO> vecList = new Vector<tempMemberVO>(); // Vector �ʱ�ȭ
		
		try {
//			con = DriverManager.getConnection(JDBC_URL, USER, PASS);
//			con = pool.getConnection();
			con = getConnection();
			String strQuery = "select * from tempMember";
			stmt = con.createStatement();
			rs = stmt.executeQuery(strQuery);
			
			while(rs.next()) {
				tempMemberVO vo = new tempMemberVO(); // vo �ʱ�ȭ
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
