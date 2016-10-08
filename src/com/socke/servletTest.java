package com.socke;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class servletTest
 */
@WebServlet("/servletTest")
public class servletTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		if("Dummy Protocol".equals(request.getHeader("Upgrade"))){
			response.setStatus(101);
			response.setHeader("X-Powered-By", "Servlet/3.1 "
                    + "(GlassFish Server Open Source Edition 4.0 Java/Oracle Corporation/1.7)");
			response.setHeader("Upgrade", "Dummy Protocol");
			response.setHeader("Connection", "Upgrade");
			response.flushBuffer();
			protoco pro=request.upgrade(protoco.class);
		}else{
			System.out.println("No");
			response.setStatus(400);
			response.setHeader("X-Powered-By", "Servlet/3.1") ;
			response.setHeader("Connection", "Refused");
			response.sendError(400, "The Upgrade request sent by the client was incorrect or can not be accept by the server");
			
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

}
