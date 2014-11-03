package com.zht.train;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class SubmitOrder {
	/**
	 * 验证用户是否登录
	 * @param cookie
	 * @throws Exception
	 */
	public void checkUser(String cookie) throws Exception{
				String url_checkUser = "https://kyfw.12306.cn/otn/login/checkUser";
				CloseableHttpClient httpClientCheck= HttpClientUtil.createSSLClientDefault();
				HttpPost postOrderCheck = new HttpPost(url_checkUser);
				postOrderCheck.setHeader("Cookie",cookie);
				HttpResponse response = httpClientCheck.execute(postOrderCheck);
				System.out.println(EntityUtils.toString(response.getEntity()));
	}
	
	
	/**
	 * 获取乘客，里面随机一个token
	 * @param cookie
	 * @param REPEAT_SUBMIT_TOKEN
	 * @throws Exception
	 */
	public void getPassengerDTOs(String cookie,String REPEAT_SUBMIT_TOKEN) throws Exception{
				CloseableHttpClient httpClient = HttpClientUtil.createSSLClientDefault();
				HttpPost httpPassenger = new HttpPost("https://kyfw.12306.cn/otn/confirmPassenger/getPassengerDTOs");
				httpPassenger.setHeader("Cookie", cookie);
				List<BasicNameValuePair> nvps1 = new ArrayList<BasicNameValuePair>();
				nvps1.add(new BasicNameValuePair("_json_att", ""));
				nvps1.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN", REPEAT_SUBMIT_TOKEN));//随机产生
				httpPassenger.setEntity(new UrlEncodedFormEntity(nvps1, "UTF-8"));
				HttpResponse response1 = httpClient.execute(httpPassenger);
				System.out.println("获取乘客---》"+EntityUtils.toString(response1.getEntity()));
	}
	
	/**
	 * 验证码，伴有token
	 * @param cookie
	 * @param REPEAT_SUBMIT_TOKEN
	 * @throws Exception
	 */
	public void checkRandCodeAnsyn(String cookie,String randCode,String REPEAT_SUBMIT_TOKEN) throws Exception{
				String url_checkRandCodeAnsyn = "https://kyfw.12306.cn/otn/passcodeNew/checkRandCodeAnsyn";//先验证验证码，token
				CloseableHttpClient httpRandCode = HttpClientUtil.createSSLClientDefault();
				HttpPost postRandCode = new HttpPost(url_checkRandCodeAnsyn);
				postRandCode.setHeader("Cookie",cookie);
				List<BasicNameValuePair> nvps2 = new ArrayList<BasicNameValuePair>();
				nvps2.add(new BasicNameValuePair("randCode", randCode));//提交订单时候验证码
				nvps2.add(new BasicNameValuePair("rand", "randp"));
				nvps2.add(new BasicNameValuePair("_json_att", ""));
				nvps2.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN", REPEAT_SUBMIT_TOKEN));
				postRandCode.setEntity(new UrlEncodedFormEntity(nvps2,"UTF-8"));
				HttpResponse response2 = httpRandCode.execute(postRandCode);
				System.out.println("确认前，检测验证码---》"+EntityUtils.toString(response2.getEntity()));
	}
	
	/**
	 * 验证订单
	 * @param cookie
	 * @param randCode
	 * @param REPEAT_SUBMIT_TOKEN
	 * @throws Exception
	 */
	public void checkOrderInfo(String cookie,String randCode,String REPEAT_SUBMIT_TOKEN) throws Exception{
				String url_checkOrderInfo = "https://kyfw.12306.cn/otn/confirmPassenger/checkOrderInfo";
				CloseableHttpClient httpcheckOrderInfo = HttpClientUtil.createSSLClientDefault();
				HttpPost postcheckOrderInfo = new HttpPost(url_checkOrderInfo);
				postcheckOrderInfo.setHeader("Cookie",cookie);
				List<BasicNameValuePair> nvps3 = new ArrayList<BasicNameValuePair>();
				nvps3.add(new BasicNameValuePair("cancel_flag", "2"));
				nvps3.add(new BasicNameValuePair("bed_level_order_num", "000000000000000000000000000000"));
				nvps3.add(new BasicNameValuePair("passengerTicketStr", "O,0,1,邵建飞,1,412801199202062656,18801064475,N"));
				nvps3.add(new BasicNameValuePair("oldPassengerStr", "邵建飞,1,412801199202062656,1_"));
				nvps3.add(new BasicNameValuePair("tour_flag", "dc"));
				nvps3.add(new BasicNameValuePair("randCode", randCode));//这里也有验证码
				nvps3.add(new BasicNameValuePair("_json_att", ""));
				nvps3.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN", REPEAT_SUBMIT_TOKEN));
				postcheckOrderInfo.setEntity(new UrlEncodedFormEntity(nvps3,"UTF-8"));
				HttpResponse response3 = httpcheckOrderInfo.execute(postcheckOrderInfo);
				System.out.println("验证订单信息----》"+EntityUtils.toString(response3.getEntity()));
	}
	/**
	 * 获取余票,这里有个令牌---》leftTicket
	 * @param cookie
	 * @param REPEAT_SUBMIT_TOKEN
	 * @throws Exception
	 */
	public void getQueueCount(String cookie,String REPEAT_SUBMIT_TOKEN,String leftTicket) throws Exception{
				String url_getQueueCount = "https://kyfw.12306.cn/otn/confirmPassenger/getQueueCount";
				CloseableHttpClient httpgetQueueCount = HttpClientUtil.createSSLClientDefault();
				HttpPost postgetQueueCount = new HttpPost(url_getQueueCount);
				postgetQueueCount.setHeader("Cookie",cookie);
				List<BasicNameValuePair> nvps4 = new ArrayList<BasicNameValuePair>();
				nvps4.add(new BasicNameValuePair("train_date", "Mon Oct 27 2014 00:00:00 GMT+0800"));
				nvps4.add(new BasicNameValuePair("train_no", "240000G16500"));
				nvps4.add(new BasicNameValuePair("stationTrainCode", "G165"));
				nvps4.add(new BasicNameValuePair("seatType", "O"));
				nvps4.add(new BasicNameValuePair("fromStationTelecode", "VNP"));
				nvps4.add(new BasicNameValuePair("toStationTelecode", "XKS"));
				nvps4.add(new BasicNameValuePair("leftTicket", leftTicket));
				nvps4.add(new BasicNameValuePair("purpose_codes", "00"));
				nvps4.add(new BasicNameValuePair("_json_att", ""));
				nvps4.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN", REPEAT_SUBMIT_TOKEN));
				postgetQueueCount.setEntity(new UrlEncodedFormEntity(nvps4,"UTF-8"));
				HttpResponse response4 = httpgetQueueCount.execute(postgetQueueCount);
				System.out.println("获取排队信息----》"+EntityUtils.toString(response4.getEntity()));
	}
	/**
	 * 提交订单
	 * @param cookie
	 * @param REPEAT_SUBMIT_TOKEN
	 * @throws Exception
	 */
	public String confirmSingleForQueue(String cookie,
			String REPEAT_SUBMIT_TOKEN,
			String key_check_isChange,
			String leftTicketStr,
			String randCode) throws Exception{
		System.out.println("提交订单中！！！");
		String url_confirmSingleForQueue = "https://kyfw.12306.cn/otn/confirmPassenger/confirmSingleForQueue";
		CloseableHttpClient httpconfirmSingleForQueue = HttpClientUtil.createSSLClientDefault();
		HttpPost post = new HttpPost(url_confirmSingleForQueue);
		post.setHeader("Cookie",cookie);
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("passengerTicketStr", "O,0,1,邵建飞,1,412801199202062656,18801064475,N"));
		nvps.add(new BasicNameValuePair("oldPassengerStr", "邵建飞,1,412801199202062656,1_"));
		nvps.add(new BasicNameValuePair("randCode", randCode));
		nvps.add(new BasicNameValuePair("purpose_codes", "00"));
		nvps.add(new BasicNameValuePair("key_check_isChange",key_check_isChange));
		nvps.add(new BasicNameValuePair("leftTicketStr", leftTicketStr));
		nvps.add(new BasicNameValuePair("train_location", "P3"));
		nvps.add(new BasicNameValuePair("_json_att", ""));
		nvps.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN", REPEAT_SUBMIT_TOKEN));
		post.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
		HttpResponse response = httpconfirmSingleForQueue.execute(post);
		return EntityUtils.toString(response.getEntity());
	}
}
