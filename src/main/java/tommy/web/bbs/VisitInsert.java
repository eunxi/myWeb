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
		request.setCharacterEncoding("UTF-8"); // .jsp �Ǵ� .html ���Ͽ��� POST ������� �� ������ ��, �����ϴ� ���� �ѱ��� ��� ���� ������ �����ϱ� ����(������ �� ����� �ƴ� �� ������ ��� = request)
		
		// request.getParameter �� ������ ���� write.html �� �����ϴ� name �� ���̴�. Ŭ���̾�Ʈ�� ���� ����(request, ��û)�ϴ� ��
		String writer = request.getParameter("writer"); // request �Ķ����(writer) ������ �� writer �� �־��ֱ�
		String memo = request.getParameter("memo"); // request �Ķ����(memo) ������ �� memo �� �־��ֱ�
		System.out.println("�ۼ��� : " + writer);
		System.out.println("���� : " + memo);
		
		StringBuffer sql = new StringBuffer();
		sql.append("insert into visit(no, writer, memo, regdate) ");
		sql.append("values (visit_seq.nextval, ?, ?, sysdate)");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // ojdbc8.jar ����
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "mytest", "mytest");
			pstmt = con.prepareStatement(sql.toString()); // SQL ��
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
		response.sendRedirect("VisitList"); // VisitList �� �̵�
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
