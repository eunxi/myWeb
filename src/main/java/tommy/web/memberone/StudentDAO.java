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
	private static StudentDAO instance; // 멤버 필드
//	// 생성자는 외부에서 호출 못하게 private 으로 지정
//	private StudentDAO() {} // -> private 이므로 useBean 생성 불가하므로, getInstance() 메소드를 이용해서 객체 얻어오기
//	public static StudentDAO getInstance() { // 메소드
//		if(instance == null) { // instance 가 null 일 때
//			synchronized(StudentDAO.class) { // 동기화
//				instance = new StudentDAO(); // instance 생성
//			}
//		}
//		return instance;
//	}
	
	// LazyHolder 를 사용하는 Singleton 패턴
	private StudentDAO() {} // 생성자
	
	private static class StudentDAOHolder{ // private static inner class 인 StudentDAOHolder
		// StudentDAOHolder 클래스 초기화 과정에서 JVM 이 Thread-Safe 하게 instance 생성
		private static final StudentDAO instance = new StudentDAO();
	}
	
	public static StudentDAO getInstance() { // StudentDAOHolder 클래스의 instance 에 접근하여 반환
		return StudentDAOHolder.instance;
	}
	
	/* 장점 : synchronized 를 사용하지 않아도 JVM 자체가 보장하는 원자성을 사용하여 Thread-Safe 하게 싱글톤 패턴 구현 가능
	 *		synchronized 를 적용하면, 적용하지 않았을 때보다 속도가 약 100배 정도 느려진다고 한다. (성능 저하)
	 */
	
	private Connection getConnection() {
		Connection con = null;
		
		try {
			Context init = new InitialContext();
			DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/myOracle");
			con = ds.getConnection();
		}catch(Exception e) {
			System.err.println("Connection 생성 실패!");
		}

		return con;
	}

	// 필요한 기능 하나씩 메소드로 구현
	public boolean idCheck(String id) { // id 를 받아서 중복 확인
		boolean result = true; // true 면 id 중복 -> 해당 id 사용 불가
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement("select * from student where id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			// 결과가 하나 혹은 안나올 경우, if 문 사용
			// !rs.next() : 다음 데이터가 없다면
			if(!rs.next()) result = false; // false 면 id 중복 X -> 해당 id 사용 가능
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
	
	public Vector<ZipCodeVO> zipcodeRead(String dong){ // 우편번호 DB 에서 검색해서 Vector 에 담아서 출력하는 메소드
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
	
	public boolean memberInsert(StudentVO vo) { // DB 에 회원가입 한 회원 데이터 넣어주는 메소드
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false; // flag = false -> 가입 실패
		
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
			
			int count = pstmt.executeUpdate(); // 몇 개의 레코드가 insert 되었는지, 레코드 개수 
			if(count > 0) flag = true; // 추가 되었으면 0 초과일 것 -> 레코드개수 > 0 이라면 true (가입 성공)
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
	
	public int loginCheck(String id, String pass) { // DB 에서 ID/PW 비교해서 결과를 정수형으로 리턴
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int check = -1; // 아이디 없음
		
		try {
			con = getConnection();
			String strQuery = "select pass from student where id = ?";
			pstmt = con.prepareStatement(strQuery);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String dbPass = rs.getString("pass"); // DB 에서 가져온 pass 의 값을 dbPass 에 넣어주기
				/*
				 * if(pass.equals(dbPass)) check = 1; // DB 의 pass 와 여기서 입력한 pass 가 같을 경우 -> 로그인
				 * 성공 else check = 0; // 다를 경우 -> 로그인 실패(비밀번호 오류)
				 */	
				
				if(pass.equals(dbPass)) {
					check = 1; // 로그인 성공
				}else {
					if(pass == null) check = 2; // 비밀번호 null
					else check = 0; // 비밀번호 오류
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
	
	public StudentVO getMember(String id) { // id 를 가져와서 회원정보 얻어오는 메소드 -> 정보 수정을 위해서
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StudentVO vo = null;
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement("select * from student where id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 해당 아이디에 대한 회원 존재할 경우
				vo = new StudentVO(); // vo 초기화 (생성자를 이용한 초기화)
				vo.setId(rs.getString("id")); // 값 받아오기
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
	
	public void updateMember(StudentVO vo) { // 정보 수정 메소드
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
	
	public int deleteMember(String id, String pass) { // 회원 삭제 메소드
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dbPass = ""; // DB 에 실제로 저장된 비밀번호
		int result = -1; // 결과
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement("select pass from student where id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dbPass = rs.getString("pass");
				if(dbPass.equals(pass)) { // true 라면 본인확인 성공
					pstmt = con.prepareStatement("delete from student where id = ?");
					pstmt.setString(1, id);
					pstmt.executeUpdate();
					result = 1; // 회원탈퇴 성공
				}else { // false 라면 본인확인 실패
					result = 0; // 비밀번호 오류
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
