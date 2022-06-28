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
	
	// 서블릿의 lifecycle : 생성자 -> init() -> service() get 방식 요청 시 doGet(), post 방식 요청 시 doPost() 수행 -> destroy()
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{ // 클라이언트가 get 방식으로 요청하면 get 방식 사용
		PrintWriter out = response.getWriter(); // 응답에서 뽑아내기
		response.setContentType("text/html; charset=UTF-8");
		try{
			out.println("<html><head><title>My First Servlet</title></head><body>");
			out.println("<h1 color='red'><br><center><font size='5'>지금은 ");
			out.println(new java.util.Date());
			out.println(" 입니다.</font></center></body></html>");
		}finally{
			out.close();
		}
	}
}
