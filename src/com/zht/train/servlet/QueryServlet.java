package com.zht.train.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

import sun.misc.BASE64Encoder;

import com.zht.train.Query;
import com.zht.train.SubmitOrder;
import com.zht.train.entity.TicketInfo;
import com.zht.train.entity.Tickets;

public class QueryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public QueryServlet() {
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
	String secretSer = null;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Query query = new Query();
		SubmitOrder submitOrder = new SubmitOrder();
		ObjectMapper mapper = new ObjectMapper();
		String cookie = (String) request.getSession().getAttribute("cookie");
		try {
			String queryRestul = query.query("2014-11-01", "BJP", "XMS");
			Tickets tickets = mapper.readValue(queryRestul, Tickets.class);
			List<TicketInfo> ticketInfoList = tickets.getData();
			for (TicketInfo ticketInfo : ticketInfoList) {
				Map<String, String> mapParam = ticketInfo.getQueryLeftNewDTO();
				if(mapParam.get("canWebBuy").toUpperCase().equals("Y")){
					if(mapParam.get("start_time").equals("08:45")){
						secretSer = URLDecoder.decode(ticketInfo.getSecretStr(),"utf-8");
					};
				}
			}
//			submitOrder.submitOrderRequest(cookie,secretSer);
			Map<String,String> map = submitOrder.initDc(cookie);
			
			request.getSession().setAttribute("map", map);
			
			HttpResponse requestSubmit = submitOrder.orderGetPassCodeNew(cookie);
			
//			//返回提交验证码
			InputStream is = requestSubmit.getEntity().getContent();
			int count = 0;
			while (count == 0) {
				count = (int) requestSubmit.getEntity().getContentLength();
			}
			byte[] data = new byte[count];
			int readCount = 0;
			while (readCount < count) {
				readCount += is.read(data, readCount, count - readCount);
			}
			is.read(data);
			is.close();
			BASE64Encoder encoder = new BASE64Encoder();
			request.getSession().setAttribute("randCodeSubmit", encoder.encode(data));
			response.sendRedirect("/single/submit.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// response.setContentType("text/html");
		// PrintWriter out = response.getWriter();
		// out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		// out.println("<HTML>");
		// out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		// out.println("  <BODY>");
		// out.print("    This is ");
		// out.print(this.getClass());
		// out.println(", using the GET method");
		// out.println("  </BODY>");
		// out.println("</HTML>");
		// out.flush();
		// out.close();
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
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
