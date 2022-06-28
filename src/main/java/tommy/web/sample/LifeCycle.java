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
	
	// ������
	public LifeCycle() {
		System.out.println("LifeCycle�� ������ ȣ��");
	}
	
	// LifeCycle �� �ʱ�ȭ �۾� ���
	// Servlet ��ü ���� �� �� �ѹ��� ����
	@Override
	public void init() throws ServletException {
		System.out.println("init() ȣ��");
		
		System.out.println("�ʱ�ȭ �޼ҵ� ����");
    	// ServletContext �� �ʱ� �Ķ���� �� �б�
    	company = getServletContext().getInitParameter("company");
    	manager = getServletContext().getInitParameter("manager");
    	
    	// ServletConfig �� �ʱ� �Ķ���� �� �б�
    	tel = getServletConfig().getInitParameter("tel");
    	email = getServletConfig().getInitParameter("email");
	}
	
	// LifeCycle ��ü�� WAS ���� �Ҹ�� �� ����
	@Override
	public void destroy() {
		System.out.println("destroy() ȣ��");
	}
	
	// Ŭ���̾�Ʈ�� ���� ��û�� ���� ������ ȣ��
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		System.out.println("service() ȣ��");
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			out.println("<html>");
			out.println("<body>");
			out.println("<li>ȸ��� : " + company + "</li>");
			out.println("<li>����� : " + manager + "</li>");
			out.println("<li>��ȭ��ȣ : " + tel + "</li>");
			out.println("<li>�̸��� : " + email + "</li>");
			out.println("</body>");
			out.println("</html>");
		}finally {
			out.close();
		}
	}
}
