package com.zht.train.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
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
import com.zht.train.util.HttpRequestTool;
import com.zht.train.util.HttpResponseTool;

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

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String secretSer = null;
		Query query = new Query();
		ObjectMapper mapper = new ObjectMapper();
		String cookie = (String) request.getSession().getAttribute("cookie");
		Map<String,String> getParamMap = 
				HttpRequestTool.getMoreParameters(request, "trainDate","fromStation","toStation","startTime");
		Map<String,String> setParamMap = new HashMap<String, String>();
		Map<String,String> getQueryLeftNewDTOMap = new HashMap<String, String>();
		try {
			boolean existsQuery = true;
			while(existsQuery){
				//在这里轮询车票
				String queryRestul = query.query(getParamMap.get("trainDate"), getParamMap.get("fromStation"), getParamMap.get("toStation"));
				System.out.println(queryRestul);
				
				Tickets tickets = mapper.readValue(queryRestul, Tickets.class);
				List<TicketInfo> ticketInfoList = tickets.getData();
				if(ticketInfoList.size()>0){
					for (TicketInfo ticketInfo : ticketInfoList) {
						Map<String, String> mapParam = ticketInfo.getQueryLeftNewDTO();
						if(mapParam.get("canWebBuy").toUpperCase().equals("Y")){
							//有票的情况下，进行条件筛选
							if(mapParam.get("start_time").equals(getParamMap.get("startTime"))){
								secretSer = URLDecoder.decode(ticketInfo.getSecretStr(),"utf-8");
								getQueryLeftNewDTOMap.putAll(mapParam);
								existsQuery = false;
							};
						}
					}
				}
			}
			if(secretSer != null){
				setParamMap.put("cookie", cookie);
				setParamMap.put("secretStr", secretSer);
				setParamMap.put("train_date", getParamMap.get("trainDate"));
				setParamMap.put("back_train_date", getParamMap.get("trainDate"));
//				setParamMap.put("tour_flag", value);
//				setParamMap.put("purpose_codes", value);
				setParamMap.put("query_from_station_name", getQueryLeftNewDTOMap.get("start_station_name"));
				setParamMap.put("query_to_station_name", getQueryLeftNewDTOMap.get("end_station_name"));
				
				query.submitOrderRequest(cookie,secretSer,setParamMap);
				//的到三个令牌，（可以优化）
				Map<String,String> tokenMap = query.initDc(cookie);
				request.getSession().setAttribute("tokenMap", tokenMap);
				HttpResponse responseEntity = query.getOrderPassCodeNew(cookie);
				
				//获取响应BASE64
				String BASE64String = HttpResponseTool.getEntityBASE64Encoder(responseEntity);
				request.getSession().setAttribute("randCodeSubmit", BASE64String);
				
				response.sendRedirect("/single/submit.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
