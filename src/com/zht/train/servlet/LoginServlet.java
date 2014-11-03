package com.zht.train.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import com.zht.train.UserLogin;
import com.zht.train.util.HttpResponseTool;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	String cookie = null;
	UserLogin ul = new UserLogin();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HttpResponse responseEntity = ul.getLoginPassCodeNew();
			Header[] headers = responseEntity.getHeaders("Set-Cookie");
			String cookie = "";
			for (int i = 0; i < headers.length; i++) {
				cookie += headers[i].getValue().replaceAll("Path=/otn", "");
			}
			String BASE64Img = HttpResponseTool.getEntityBASE64Encoder(responseEntity);
			request.getSession().setAttribute("LoginRandCode",BASE64Img);
			request.getSession().setAttribute("cookie", cookie);
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		
		String randcode = request.getParameter("randcode");
		String cookies = (String) request.getSession().getAttribute("cookie");
		
		try {
			ul.getUser(user, pwd, cookies, randcode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("/single/query.jsp");
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
