package com.zht.train;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Encoder;

import com.zht.train.entity.TicketInfo;
import com.zht.train.entity.Tickets;

public class TestSingle {

	Logger logs = LoggerFactory.getLogger(TestSingle.class);

	UserLogin userLogin;
	ObjectMapper mapper;
	String cookie;
	Map<String,String> map = null;
	@Before
	public void start() {
		userLogin = new UserLogin();
		mapper = new ObjectMapper();
		map = new HashMap<String, String>();
		cookie = " JSESSIONID=0A01D48660B900042DAC7D3BF87C7E19BB403B7B59; BIGipServerotn=2262040842.24610.0000";
	}

	@After
	public void end() {
		
	}
	
	@Test
	public void login() throws Exception {
		HttpResponse response = userLogin.getLoginPassCodeNew();
		Header[] headers = response.getHeaders("Set-Cookie");
		for (int i = 0; i < headers.length; i++) {
			Header header = headers[i];
			System.out.println(header.getValue());
		}
		InputStream inputStream = response.getEntity().getContent();
		byte[] data = new byte[inputStream.available()];
		inputStream.read(data);
		inputStream.close();
		BASE64Encoder encoder = new BASE64Encoder();
		String re = encoder.encode(data);
		System.out.println(re.length());
//		BASE64Encoder
//		String loginResult = userLogin.getUser(cookie, "2uqd");
//		System.out.println(loginResult);
	}

	/**
	 * 获取车次
	 * @throws Exception
	 */
	@Test
	public void query() throws Exception {
		CloseableHttpClient httpLeftTicket= HttpClientUtil.createSSLClientDefault();
		String url_leftTicket = "https://kyfw.12306.cn/otn/leftTicket/log?leftTicketDTO.train_date=2014-10-26&leftTicketDTO.from_station=BXP&leftTicketDTO.to_station=XMS&purpose_codes=ADULT";
		HttpGet httpGet = new HttpGet(url_leftTicket);
		HttpResponse response = httpLeftTicket.execute(httpGet);
		System.out.println(EntityUtils.toString(response.getEntity()));
		
		Query query = new Query();
		String log = query.log("2014-10-28", "BJP", "XMS");
		String chepiao = query.query("2014-10-28", "BJP", "XMS");
		Tickets tickets = mapper.readValue(chepiao, Tickets.class);
		List<TicketInfo> ticketInfoList = tickets.getData();
		System.out.println("趟数----->"+ticketInfoList.size());
		for (TicketInfo ticketInfo : ticketInfoList) {
			System.out.println(ticketInfo.getQueryLeftNewDTO());
			Map<String, String> map = ticketInfo.getQueryLeftNewDTO();
//				URLEncoder.encode(s, enc);
				System.out.println(URLDecoder.decode(ticketInfo.getSecretStr(),"utf-8"));
//				System.out.println(ticketInfo.getSecretStr());
//			if (map.get("station_train_code").equals("G421")) {
				System.out.println("软卧" + map.get("rw_num"));
				System.out.println("硬卧" + map.get("yw_num"));
				System.out.println("软座" + map.get("rz_num"));
				System.out.println("硬座" + map.get("yz_num"));
				System.out.println("无座" + map.get("wz_num"));
				System.out.println("其它" + map.get("qt_num"));
				System.out.println("商务座" + map.get("swz_num"));
				System.out.println("特等座" + map.get("tz_num"));
				System.out.println("高级软卧" + map.get("gr_num"));
				System.out.println("duo" + map.get("yb_num"));
				System.out.println("一等座" + map.get("zy_num"));
				System.out.println("二等座" + map.get("ze_num"));
				System.out.println("duo" + map.get("gg_num"));
//				break;
//			}
		}
		
	}
	
	/**
	 * 获取乘客信息
	 * @throws Exception
	 */
//	@Ignore
//	@Test
//	public void getPassengerDTOs() throws Exception{
//		String result = userLogin.getPassengerDTOs(cookie);
//		PassengerDTO passengerDTO = mapper.readValue(result, PassengerDTO.class);
//		Person[] person = passengerDTO.getData().getNormal_passengers();
//		for (int i = 0; i < person.length; i++) {
//			System.out.println(person[i].getPassenger_name());
//		}
//	}
	
	/**
	 * 提交订单
	 */
//	@Ignore
	@Test
	public void sendOrder() throws Exception{
		//1 验证用户是否登录
		String url_checkUser = "https://kyfw.12306.cn/otn/login/checkUser";
		CloseableHttpClient httpClientCheck= HttpClientUtil.createSSLClientDefault();
		HttpPost postOrderCheck = new HttpPost(url_checkUser);
		postOrderCheck.setHeader("Cookie",cookie);
		HttpResponse response = httpClientCheck.execute(postOrderCheck);
		System.out.println(EntityUtils.toString(response.getEntity()));
		
		
		//2 验证车票是否还存在？？？
		String url_submitOrderRequest = "https://kyfw.12306.cn/otn/leftTicket/submitOrderRequest";
		CloseableHttpClient httpClientCheckOrder = HttpClientUtil.createSSLClientDefault();
		HttpPost postOrder = new HttpPost(url_submitOrderRequest);
		postOrder.setHeader("Cookie",cookie);
		String secretStr="MjAxNC0xMC0yOCMwMCNHMTY1IzEyOjQ1IzA4OjQ1IzI0MDAwMEcxNjUwMCNWTlAjWEtTIzIxOjMwI+WMl+S6rOWNlyPljqbpl6jljJcjMDEjMjUjTzA4Mjk1MDE2N00xMjg0MDAwNzU5MjU4MTUwMDAyI1AzIzE0MTQ0MTI0MDY4MjIjMTVGRjM2MTc5MDc1OTlDQjM0NzY0RDQzNEM5QjQyMjY2MTU2OEFEQkI4NzY2Q0JCQjk2NjlEMzU=";
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("secretStr", secretStr));
		nvps.add(new BasicNameValuePair("train_date", "2014-10-28"));
		nvps.add(new BasicNameValuePair("back_train_date", "2014-10-28"));
		nvps.add(new BasicNameValuePair("tour_flag", "dc"));
		nvps.add(new BasicNameValuePair("purpose_codes", "ADULT"));
		nvps.add(new BasicNameValuePair("query_from_station_name", "北京"));
		nvps.add(new BasicNameValuePair("query_to_station_name", "厦门"));
		nvps.add(new BasicNameValuePair("undefined", ""));
		postOrder.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		HttpResponse response1 = httpClientCheckOrder.execute(postOrder);
		System.out.println(EntityUtils.toString(response1.getEntity()));
		
		//3 验证成功后，这里有3个 令牌
		String url_initDc = "https://kyfw.12306.cn/otn/confirmPassenger/initDc";
		CloseableHttpClient httpClientInit = HttpClientUtil.createSSLClientDefault();
		HttpPost postInit = new HttpPost(url_initDc);
		postInit.setHeader("Cookie",cookie);
		HttpResponse response2 = httpClientInit.execute(postInit);
//		System.out.println(EntityUtils.toString(response2.getEntity()));
		
		
		HttpEntity entity = response2.getEntity();
		InputStream inputStream = entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String lines = null;
		StringBuffer sb = new StringBuffer();
		while((lines = reader.readLine()) != null){
			sb.append(lines+"\n");
		}
		inputStream.close();
		reader.close();
		
		//key1 key_check_isChange
		int startIndex = sb.toString().lastIndexOf("key_check_isChange':'");
		int endIndex = sb.toString().lastIndexOf("','leftDetails");
		String key_check_isChange = sb.toString().substring(startIndex, endIndex);
		String[] key = key_check_isChange.split("':'");
		map.put(key[0], key[1]);
		
		//key2 leftTicketStr
		int startIndex1 = sb.toString().lastIndexOf("leftTicketStr':'");
		int endIndex1 = sb.toString().lastIndexOf("','limitBuySeatTicketDTO");
		String leftTicketStr = sb.toString().substring(startIndex1, endIndex1);
		String[] key1 = leftTicketStr.split("':'");
		map.put(key1[0], key1[1]);
	
		//key3 globalRepeatSubmitToken
		int startIndex2 = sb.toString().indexOf("globalRepeatSubmitToken = '");
		int endIndex2 = sb.toString().indexOf("';",startIndex2);
		String globalRepeatSubmitToken = sb.toString().substring(startIndex2, endIndex2);
		map.put("globalRepeatSubmitToken", globalRepeatSubmitToken);
		
		System.out.println(map.size());
		System.out.println("key_check_isChange--->"+map.get("key_check_isChange"));
		System.out.println("leftTicketStr---->"+map.get("leftTicketStr"));
		System.out.println("globalRepeatSubmitToken---->"+map.get("globalRepeatSubmitToken"));
		
	}
	
	String REPEAT_SUBMIT_TOKEN="c9b2b5e08fd35cf85559af6f2828142a";
	String randCode = "pus5";
//	@Ignore
	@Test
	public void home() throws Exception{
		SubmitOrder so = new SubmitOrder();
		so.getPassengerDTOs(REPEAT_SUBMIT_TOKEN, REPEAT_SUBMIT_TOKEN);
		so.checkRandCodeAnsyn(REPEAT_SUBMIT_TOKEN, REPEAT_SUBMIT_TOKEN, REPEAT_SUBMIT_TOKEN);
		so.checkOrderInfo(REPEAT_SUBMIT_TOKEN, REPEAT_SUBMIT_TOKEN, REPEAT_SUBMIT_TOKEN);
		so.getQueueCount(REPEAT_SUBMIT_TOKEN, REPEAT_SUBMIT_TOKEN, REPEAT_SUBMIT_TOKEN);
		
		Set set = new HashSet();
		set.add("xxx");
		set.add("xxx");
		System.out.println(set.size());
//		this.confirmSingleForQueue();
		
	}
}