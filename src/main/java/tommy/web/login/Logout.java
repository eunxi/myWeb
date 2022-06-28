package tommy.web.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false); // getSession(false) -> ������ ���� ������ �α��� �������� �ٷ� �̵�, ������ ���� �ִٸ� �״�� ��������
		
		if(session != null) { // session �� �����Ѵٸ�
			session.invalidate(); // �α׾ƿ�
		}
		response.sendRedirect("Login"); // session �� �������� �ʴ´ٸ� Login �������� �̵�
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
