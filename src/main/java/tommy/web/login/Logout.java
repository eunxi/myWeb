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
		HttpSession session = request.getSession(false); // getSession(false) -> 가져올 세션 없으면 로그인 페이지로 바로 이동, 가져올 세션 있다면 그대로 가져오기
		
		if(session != null) { // session 이 존재한다면
			session.invalidate(); // 로그아웃
		}
		response.sendRedirect("Login"); // session 이 존재하지 않는다면 Login 페이지로 이동
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
