package com.zht.train.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zht.train.SubmitOrder;

public class SubmitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public SubmitServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SubmitOrder submitOrder = new SubmitOrder();
		
		String verificationCode = request.getParameter("codeSubmit");
		
		String cookie = (String)request.getSession().getAttribute("cookie");
		
		Map<String,String> tokenMap = (Map<String, String>) request.getSession().getAttribute("tokenMap");
		try {
			//检测验证是否正确，应该在服务端标记了这个请求
			submitOrder.checkRandCodeAnsyn(cookie, 
					verificationCode, 
					tokenMap.get("globalRepeatSubmitToken"));
			//验证订单，这一步可以去除？
			submitOrder.checkOrderInfo(cookie, 
					verificationCode, 
					tokenMap.get("globalRepeatSubmitToken"));
			//获取余票信息，是否可以去除？
			submitOrder.getQueueCount(cookie, 
					tokenMap.get("globalRepeatSubmitToken"), 
					tokenMap.get("leftTicketStr"));
			//提交订单
			String submitResult = submitOrder.confirmSingleForQueue(cookie, 
					tokenMap.get("globalRepeatSubmitToken"), 
					tokenMap.get("key_check_isChange"), 
					tokenMap.get("leftTicketStr"), 
					verificationCode);
			request.getSession().setAttribute("message", submitResult);
			response.sendRedirect("/single/message.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
