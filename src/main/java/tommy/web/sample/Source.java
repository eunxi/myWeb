package tommy.web.sample;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Source")
public class Source extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Source Start");
		
		// 페이지 이동
		// 1. forward
//		RequestDispatcher view = request.getRequestDispatcher("Destination");
		request.setAttribute("myName", "eunxi");
		request.setAttribute("myAge", "25");
//		view.forward(request, response); // 두 객체는 해당 주소에 요청하고 응답하라 !
		// 2. redirect
		response.sendRedirect("Destination");
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
