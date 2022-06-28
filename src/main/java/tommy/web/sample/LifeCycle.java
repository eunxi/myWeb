package tommy.web.sample;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

public class LifeCycle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String company;
	private String manager;
	private String tel;
	private String email;
	
	// 생성자
	public LifeCycle() {
		System.out.println("LifeCycle의 생성자 호출");
	}
	
	// LifeCycle 의 초기화 작업 담당
	// Servlet 객체 생성 시 단 한번만 수행
	@Override
	public void init() throws ServletException {
		System.out.println("init() 호출");
		
		System.out.println("초기화 메소드 수행");
    	// ServletContext 의 초기 파라미터 값 읽기
    	company = getServletContext().getInitParameter("company");
    	manager = getServletContext().getInitParameter("manager");
    	
    	// ServletConfig 의 초기 파라미터 값 읽기
    	tel = getServletConfig().getInitParameter("tel");
    	email = getServletConfig().getInitParameter("email");
	}
	
	// LifeCycle 객체가 WAS 에서 소멸될 때 수행
	@Override
	public void destroy() {
		System.out.println("destroy() 호출");
	}
	
	// 클라이언트의 연결 요청이 있을 때마다 호출
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		System.out.println("service() 호출");
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			out.println("<html>");
			out.println("<body>");
			out.println("<li>회사명 : " + company + "</li>");
			out.println("<li>담당자 : " + manager + "</li>");
			out.println("<li>전화번호 : " + tel + "</li>");
			out.println("<li>이메일 : " + email + "</li>");
			out.println("</body>");
			out.println("</html>");
		}finally {
			out.close();
		}
	}
}
