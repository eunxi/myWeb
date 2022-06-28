package tommy.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// �������� lifecycle : ������ -> init() -> service() get ��� ��û �� doGet(), post ��� ��û �� doPost() ���� -> destroy()
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{ // Ŭ���̾�Ʈ�� get ������� ��û�ϸ� get ��� ���
		PrintWriter out = response.getWriter(); // ���信�� �̾Ƴ���
		response.setContentType("text/html; charset=UTF-8");
		try{
			out.println("<html><head><title>My First Servlet</title></head><body>");
			out.println("<h1 color='red'><br><center><font size='5'>������ ");
			out.println(new java.util.Date());
			out.println(" �Դϴ�.</font></center></body></html>");
		}finally{
			out.close();
		}
	}
}