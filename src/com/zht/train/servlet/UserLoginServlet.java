package com.zht.train.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import sun.misc.BASE64Encoder;

import com.zht.train.UserLogin;

public class UserLoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public UserLoginServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	String cookie = null;
	UserLogin ul = new UserLogin();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			HttpResponse responseResult = ul.getPassCodeNew();
			Header[] headers = responseResult.getHeaders("Set-Cookie");
			String temp = "";
			for (int i = 0; i < headers.length; i++) {
				temp += headers[i].getValue().replaceAll("Path=/otn","");
//				System.out.println("--->"+headers[i].getValue().replaceAll("Path=/otn",""));
//				temp = temp != null ?  temp + headers[i].getValue().replaceAll("Path=/otn","") : headers[i].getValue().replaceAll("Path=/otn","");
			}
			System.out.println(temp);
			cookie = temp ;
			InputStream is = responseResult.getEntity().getContent();
			int count = 0;
			while (count == 0) {
				count = (int) responseResult.getEntity().getContentLength();
			}
			byte[] data = new byte[count];
			int readCount = 0;
			while (readCount < count) {
				readCount += is.read(data, readCount, count - readCount);
			}
			is.read(data);
			is.close();
			BASE64Encoder encoder = new BASE64Encoder();
			request.getSession().setAttribute("randCode", encoder.encode(data));
			request.getSession().setAttribute("cookie", cookie);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//		out.println("<HTML>");
//		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
//		out.println("  <BODY>");
//		out.print("    This is ");
//		out.print(this.getClass());
//		out.println(", using the GET method");
//		out.println("  </BODY>");
//		out.println("</HTML>");
//		out.flush();
//		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String user = request.getParameter("user") ;
		String pwd = request.getParameter("pwd") ;
		String randcode = request.getParameter("randcode");
		String cookies = (String)request.getSession().getAttribute("cookie");
		try {
			System.out.println(ul.getUser(user,pwd,cookies, randcode));
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("/single/QueryServlet");
//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//		out.println("<HTML>");
//		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
//		out.println("  <BODY>");
//		out.print("    This is ");
//		out.print(this.getClass());
//		out.println(", using the POST method");
//		out.println("  </BODY>");
//		out.println("</HTML>");
//		out.flush();
//		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
