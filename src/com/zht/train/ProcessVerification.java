package com.zht.train;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.zht.train.entity.TicketInfo;
import com.zht.train.entity.Tickets;

public class ProcessVerification {
	String cookie = " JSESSIONID=0A01D964FCD906B91B6AE25A482C257103F3D5C8AA; BIGipServerotn=1691943178.64545.0000";
	String loginCode = "6vrn";
	ObjectMapper mapper = new ObjectMapper();
	UserLogin userLogin = new UserLogin();
	SubmitOrder submitOrder = new SubmitOrder();
	Query query = new Query();
	@Test
	public void login()throws Exception{
		System.out.println(userLogin.getUser("","",cookie,loginCode));//登陆
	}
	@Test
	public void query() throws Exception{
		String chepiao = query.query("2014-11-04", "BJP", "XMS");//查询票
		System.out.println(chepiao);
		Tickets tickets = mapper.readValue(chepiao, Tickets.class);
		List<TicketInfo> ticketInfoList = tickets.getData();
		System.out.println("趟数----->"+ticketInfoList.size());
		for (TicketInfo ticketInfo : ticketInfoList) {
			System.out.println(ticketInfo.getQueryLeftNewDTO());
			Map<String, String> map = ticketInfo.getQueryLeftNewDTO();
//				URLEncoder.encode(s, enc);
				System.out.println(URLDecoder.decode(ticketInfo.getSecretStr(),"utf-8"));
				System.out.println(map.get("start_station_name"));
				System.out.println(map.get("end_station_name"));
				System.out.println(map.get("seat_types"));
//				System.out.println(ticketInfo.getSecretStr());
//			if (map.get("station_train_code").equals("G421")) {
//				System.out.println("软卧" + map.get("rw_num"));
//				System.out.println("硬卧" + map.get("yw_num"));
//				System.out.println("软座" + map.get("rz_num"));
//				System.out.println("硬座" + map.get("yz_num"));
//				System.out.println("无座" + map.get("wz_num"));
//				System.out.println("其它" + map.get("qt_num"));
//				System.out.println("商务座" + map.get("swz_num"));
//				System.out.println("特等座" + map.get("tz_num"));
//				System.out.println("高级软卧" + map.get("gr_num"));
//				System.out.println("duo" + map.get("yb_num"));
//				System.out.println("一等座" + map.get("zy_num"));
//				System.out.println("二等座" + map.get("ze_num"));
//				System.out.println("duo" + map.get("gg_num"));
//				break;
//			}
		}
	}
	@Test
	public void checkUser()throws Exception{
		submitOrder.checkUser(cookie);
	}
	@Test
	public void submitOrderRequest()throws Exception{
//		query.submitOrderRequest(cookie,"");
	}
	@Test
	public void initDc()throws Exception{
		query.initDc(cookie);
	}
	@Test
	public void orderGetPassCodeNew(){
		
	}
	@Test
	public void checkRandCodeAnsyn(){
		
	}
	@Test
	public void checkOrderInfo() throws Exception{
		submitOrder.checkOrderInfo(cookie, "seam", "2d06ee1814318a18ca95f40224f62dbe");
	}
	@Test
	public void getQueueCount() throws Exception{
		submitOrder.getQueueCount(cookie, "4400fbea250f53af1675735ff3ae0de1", "O082950228M1284000769258150000");
	}
	@Test
	public void confirmSingleForQueue() throws Exception{
		submitOrder.confirmSingleForQueue(cookie, "b6f0263323df44b45804f81cbe47327d", "82CF01822D334BD5435B69461304718732F5C2FC06F27FEBF621A689", "O082950228M1284000769258150000","");
	}
	
}
