package tommy.web.memberone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class StudentDAO {
	private static StudentDAO instance; // ��� �ʵ�
//	// �����ڴ� �ܺο��� ȣ�� ���ϰ� private ���� ����
//	private StudentDAO() {} // -> private �̹Ƿ� useBean ���� �Ұ��ϹǷ�, getInstance() �޼ҵ带 �̿��ؼ� ��ü ������
//	public static StudentDAO getInstance() { // �޼ҵ�
//		if(instance == null) { // instance �� null �� ��
//			synchronized(StudentDAO.class) { // ����ȭ
//				instance = new StudentDAO(); // instance ����
//			}
//		}
//		return instance;
//	}
	
	// LazyHolder �� ����ϴ� Singleton ����
	private StudentDAO() {} // ������
	
	private static class StudentDAOHolder{ // private static inner class �� StudentDAOHolder
		// StudentDAOHolder Ŭ���� �ʱ�ȭ �������� JVM �� Thread-Safe �ϰ� instance ����
		private static final StudentDAO instance = new StudentDAO();
	}
	
	public static StudentDAO getInstance() { // StudentDAOHolder Ŭ������ instance �� �����Ͽ� ��ȯ
		return StudentDAOHolder.instance;
	}
	
	/* ���� : synchronized �� ������� �ʾƵ� JVM ��ü�� �����ϴ� ���ڼ��� ����Ͽ� Thread-Safe �ϰ� �̱��� ���� ���� ����
	 *		synchronized �� �����ϸ�, �������� �ʾ��� ������ �ӵ��� �� 100�� ���� �������ٰ� �Ѵ�. (���� ����)
	 */
	
	private Connection getConnection() {
		Connection con = null;
		
		try {
			Context init = new InitialContext();
			DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/myOracle");
			con = ds.getConnection();
		}catch(Exception e) {
			System.err.println("Connection ���� ����!");
		}

		return con;
	}

	// �ʿ��� ��� �ϳ��� �޼ҵ�� ����
	public boolean idCheck(String id) { // id �� �޾Ƽ� �ߺ� Ȯ��
		boolean result = true; // true �� id �ߺ� -> �ش� id ��� �Ұ�
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement("select * from student where id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			// ����� �ϳ� Ȥ�� �ȳ��� ���, if �� ���
			// !rs.next() : ���� �����Ͱ� ���ٸ�
			if(!rs.next()) result = false; // false �� id �ߺ� X -> �ش� id ��� ����
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {}
		}
		
		return result;
	}
	
	public Vector<ZipCodeVO> zipcodeRead(String dong){ // �����ȣ DB ���� �˻��ؼ� Vector �� ��Ƽ� ����ϴ� �޼ҵ�
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<ZipCodeVO> vecList = new Vector<ZipCodeVO>();
		
		try {
			con = getConnection();
			// "select * from zipcode where dong like 'dong%'";
			// "select * from zipcode where dong like '" + dong "%'";
			String strQuery = "select * from zipcode where dong like '" + dong + "%'";
			pstmt = con.prepareStatement(strQuery);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ZipCodeVO tempZipcode = new ZipCodeVO();
				tempZipcode.setZipcode(rs.getString("zipcode"));
				tempZipcode.setSido(rs.getString("sido"));
				tempZipcode.setGugun(rs.getString("gugun"));
				tempZipcode.setDong(rs.getString("dong"));
				tempZipcode.setRi(rs.getString("ri"));
				tempZipcode.setBunji(rs.getString("bunji"));
				vecList.add(tempZipcode);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {}
		}
		
		return vecList;
	}
	
	public boolean memberInsert(StudentVO vo) { // DB �� ȸ������ �� ȸ�� ������ �־��ִ� �޼ҵ�
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false; // flag = false -> ���� ����
		
		try {
			con = getConnection();
			String strQuery = "insert into student values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(strQuery);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPass());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getPhone1());
			pstmt.setString(5, vo.getPhone2());
			pstmt.setString(6, vo.getPhone3());
			pstmt.setString(7, vo.getEmail());
			pstmt.setString(8, vo.getZipcode());
			pstmt.setString(9, vo.getAddress1());
			pstmt.setString(10, vo.getAddress2());
			
			int count = pstmt.executeUpdate(); // �� ���� ���ڵ尡 insert �Ǿ�����, ���ڵ� ���� 
			if(count > 0) flag = true; // �߰� �Ǿ����� 0 �ʰ��� �� -> ���ڵ尳�� > 0 �̶�� true (���� ����)
		}catch(SQLException e) {
			System.out.println("Exception" + e);
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {}
		}
		
		return flag;
	}
	
	public int loginCheck(String id, String pass) { // DB ���� ID/PW ���ؼ� ����� ���������� ����
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int check = -1; // ���̵� ����
		
		try {
			con = getConnection();
			String strQuery = "select pass from student where id = ?";
			pstmt = con.prepareStatement(strQuery);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String dbPass = rs.getString("pass"); // DB ���� ������ pass �� ���� dbPass �� �־��ֱ�
				/*
				 * if(pass.equals(dbPass)) check = 1; // DB �� pass �� ���⼭ �Է��� pass �� ���� ��� -> �α���
				 * ���� else check = 0; // �ٸ� ��� -> �α��� ����(��й�ȣ ����)
				 */	
				
				if(pass.equals(dbPass)) {
					check = 1; // �α��� ����
				}else {
					if(pass == null) check = 2; // ��й�ȣ null
					else check = 0; // ��й�ȣ ����
				}
			}
		}catch(Exception e) {
			System.out.println("Exception " + e);
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {}
		}
		
		return check;
	}
	
	public StudentVO getMember(String id) { // id �� �����ͼ� ȸ������ ������ �޼ҵ� -> ���� ������ ���ؼ�
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StudentVO vo = null;
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement("select * from student where id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // �ش� ���̵� ���� ȸ�� ������ ���
				vo = new StudentVO(); // vo �ʱ�ȭ (�����ڸ� �̿��� �ʱ�ȭ)
				vo.setId(rs.getString("id")); // �� �޾ƿ���
				vo.setPass(rs.getString("pass"));
				vo.setName(rs.getString("name"));
				vo.setPhone1(rs.getString("phone1"));
				vo.setPhone2(rs.getString("phone2"));
				vo.setPhone3(rs.getString("phone3"));
				vo.setEmail(rs.getString("email"));
				vo.setZipcode(rs.getString("zipcode"));
				vo.setAddress1(rs.getString("address1"));
				vo.setAddress2(rs.getString("address2"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {}
		}
		return vo;
	}
	
	public void updateMember(StudentVO vo) { // ���� ���� �޼ҵ�
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement("update student set pass = ?, phone1 = ?, phone2 = ?, phone3 = ?, email = ?, zipcode = ?, address1 = ?, address2 = ? where id = ?");
			pstmt.setString(1, vo.getPass());
			pstmt.setString(2, vo.getPhone1());
			pstmt.setString(3, vo.getPhone2());
			pstmt.setString(4, vo.getPhone3());
			pstmt.setString(5, vo.getEmail());
			pstmt.setString(6, vo.getZipcode());
			pstmt.setString(7, vo.getAddress1());
			pstmt.setString(8, vo.getAddress2());
			pstmt.setString(9, vo.getId());
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {}
		}
	}
	
	public int deleteMember(String id, String pass) { // ȸ�� ���� �޼ҵ�
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dbPass = ""; // DB �� ������ ����� ��й�ȣ
		int result = -1; // ���
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement("select pass from student where id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dbPass = rs.getString("pass");
				if(dbPass.equals(pass)) { // true ��� ����Ȯ�� ����
					pstmt = con.prepareStatement("delete from student where id = ?");
					pstmt.setString(1, id);
					pstmt.executeUpdate();
					result = 1; // ȸ��Ż�� ����
				}else { // false ��� ����Ȯ�� ����
					result = 0; // ��й�ȣ ����
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {}
		}
		
		return result;
	}
}
