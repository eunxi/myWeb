package tommy.web.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			HttpSession session = request.getSession(false); // false 일 경우, 해당 클라이언트에 대해 생성된 세션객체가 없을 경우 null 반환
			if(session != null) { // session 에 값이 존재할 때
				String sessionId = session.getId();
				System.out.println("세션 아이디 : " + sessionId);
				String user = (String)session.getAttribute("user"); // session 의 setAttribute 에서 user 로 저장해줬기 때문에, user 받아서 user 로 저장
				out.println("<html>");
				out.println("<body>");
				out.println("<table border='1' width='300'>");
				out.println("<tr>");
				out.println("<td width='300' align='center'>" + user + "님 로그인 되었습니다.</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td align='center'>");
				out.println("<a href='#'>회원정보</a>");
				out.println("<a href='Logout'>로그아웃</a>"); // 로그아웃 클릭시 Logout 으로 이동
				out.println("</td>");
				out.println("</tr>");
				out.println("</table>");
				out.println("</body>");
				out.println("</html>");
			}else { // session 에 값이 존재하지 않을 때
				out.println("<html>");
				out.println("<body>");
				out.println("<form method='post' action='LoginCheck'>"); // post 방식으로, LoginCheck 로 이동
				out.println("<table border='1' width='300'>");
				out.println("<tr>");
				out.println("<th width='100'>아이디</th>");
				out.println("<td width='200'>&nbsp;<input type='text' name='id'></td>"); // name = id
				out.println("</tr>");
				out.println("<tr>");
				out.println("<th width='100'>비번</th>");
				out.println("<td width='200'>&nbsp;<input type='password' name='pwd'></td>"); // name = pwd
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td align='center' colspan='2'>");
				out.println("<input type='button' value='회원가입'>");
				out.println("<input type='submit' value='로그인'>");
				out.println("</td>");
				out.println("</tr>");
				out.println("</form>");
				out.println("</table>");
				out.println("</body>");
				out.println("</html>");
			}
		}finally {
			out.close();
		}
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
