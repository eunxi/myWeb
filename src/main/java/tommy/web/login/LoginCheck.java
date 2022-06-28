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
		
		String id = request.getParameter("id"); // Login ���� session ���� �� name = id
		String pwd = request.getParameter("pwd"); // Login ���� session ���� �� name = pwd
		System.out.println(id + ", " + pwd);
		
//		String dbID = "admin";
//		String dbPWD = "1234";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select pass from login where id = ?"); // PK �� id �� ���ǽ����� �˻�
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // ojdbc8.jar �� lib ������ �߰�
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("����̹� �ε� ����");
		}
		
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "mytest", "mytest");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, id); // ������������ ���ε������� ������ �־��ֱ�
			rs = pstmt.executeQuery();

			if(rs.next()) { // �˻���� ������ ��
				String dbPass = rs.getString("pass"); // DB ��й�ȣ pass �� dbPass �� �־��ֱ�
				
				if(pwd.equals(dbPass)) { // �Է��� pwd �� dbPass �� ���ٸ�
					HttpSession session = request.getSession(); // ���� ��ü ����
					session.setAttribute("user", id); // session �� id �� ������ user �� ����
					System.out.println("�α��� ����"); // �α��� ����
				}else {
					System.out.println("��й�ȣ ����"); // ��й�ȣ �ٸ��� ������, �α��� ����
				}
			}else {
				System.out.println("ID ����."); // �˻���� �������� ���� �� ���̵� ���� -> ���⼭ SQL Developer ���� commit �����ָ� ���̵� ���ٰ� �ߴϱ� ����
			}
			response.sendRedirect("Login"); // Login ���� �̵�
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("������ ����");
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {}
		}
		
//		if(dbID.equals(id) && dbPWD.equals(pwd)) { // id �� pwd �� dbID �� dbPWD �� ���ٸ� ���� ����
//			// HttpSession ��ü ����
//			HttpSession session = request.getSession();
//			session.setAttribute("user", id); // Ŭ���̾�Ʈ�� ������ HttpSession ��ü�� ����
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
