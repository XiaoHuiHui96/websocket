package com.socke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class socke
 */
@WebServlet("/socke")
public class socke extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("socke");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		final String CRLF = "\r\n";
		InputStream input = null;
        OutputStream output = null;
        BufferedReader reader = null;
		Socket s=null;
		final String host=request.getServerName();
		final int port=request.getServerPort();
		String url=request.getContextPath();
		String one=request.getParameter("One");
		String two=request.getParameter("Two");
		String data=one+":"+two;
		 //生成一个页面
        String reqStr = "POST " + url + "/servletTest HTTP/1.1" + CRLF;
        reqStr+="User-Agent:Java/1.7"+CRLF;
        reqStr+="Host:"+host+":"+port+CRLF;
        reqStr+="Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2" + CRLF;
        reqStr+= "Upgrade: Dummy Protocol" + CRLF;
        reqStr+= "Connection: Upgrade" + CRLF;
        reqStr += "Content-type: application/x-www-form-urlencoded" + CRLF;
        reqStr += "Transfer-Encoding: chunked" + CRLF;
        reqStr += CRLF;
        reqStr += data + CRLF;
        s=new Socket(host,port);
        input=s.getInputStream();
        output=s.getOutputStream();
        output.write(reqStr.getBytes());
        output.flush();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>结果</title>");
        out.println("</head>");
        out.println("<body>");
        reader = new BufferedReader(new InputStreamReader(input));
        printHeader(reader, out);
        String dataOutput;
        while ((dataOutput = reader.readLine()) != null) {
            out.println( dataOutput);
            System.out.println(dataOutput+"123");
            if(dataOutput.isEmpty()){
            	break;
            }
        }
        if (reader != null) {
            reader.close();
        }
        if (output != null) {
            output.close();
        }
        if (input != null) {
            input.close();
        }
        if (s != null) {
            s.close();
        }
        out.println("</body>");
        out.println("</html>");
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	 protected void printHeader(BufferedReader reader, PrintWriter out) throws IOException {
	        for (String line; (line = reader.readLine()) != null;) {
	        	if(line.isEmpty()){
	        		break;
	        	}
	        }	       
	    }
	 
}
