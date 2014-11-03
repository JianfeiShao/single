package com.zht.train;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Query {

	String url_log = "https://kyfw.12306.cn/otn/leftTicket/log?";
	String url_query = "https://kyfw.12306.cn/otn/leftTicket/query?";
	public String log(String time,String sAddress,String eAddress) throws Exception{
		String url = url_log
				+"leftTicketDTO.train_date="+time
				+"&leftTicketDTO.from_station="+sAddress
				+"&leftTicketDTO.to_station="+eAddress
				+"&purpose_codes=ADULT";
		CloseableHttpClient httpClient = HttpClientUtil
				.createSSLClientDefault();
		HttpGet getQuery = new HttpGet(url);
		HttpResponse response = httpClient.execute(getQuery);
		return EntityUtils.toString(response.getEntity());
	}
	/**
	 * 查询车票
	 * @param time
	 * @param sAddress
	 * @param eAddress
	 * @return
	 * @throws Exception
	 */
	public String query(String time, String sAddress, String eAddress) throws Exception{
		String url = url_query
				+"leftTicketDTO.train_date="+time
				+"&leftTicketDTO.from_station="+sAddress
				+"&leftTicketDTO.to_station="+eAddress
				+"&purpose_codes=ADULT";
		CloseableHttpClient httpClient = HttpClientUtil
				.createSSLClientDefault();
		HttpGet getQuery = new HttpGet(url);
		HttpResponse response = httpClient.execute(getQuery);
		return EntityUtils.toString(response.getEntity());
	}
	/**
	 *  验证车票是否还存在？？？
	 */
	public void submitOrderRequest(String cookie,String secretStr,Map<String,String> paramMap) throws Exception{
				String url_submitOrderRequest = "https://kyfw.12306.cn/otn/leftTicket/submitOrderRequest";
				CloseableHttpClient httpClientCheckOrder = HttpClientUtil.createSSLClientDefault();
				HttpPost postOrder = new HttpPost(url_submitOrderRequest);
				postOrder.setHeader("Cookie",paramMap.get("cookie"));
				List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
				nvps.add(new BasicNameValuePair("secretStr", paramMap.get("secretStr")));
				nvps.add(new BasicNameValuePair("train_date", "2014-10-30"));
				nvps.add(new BasicNameValuePair("back_train_date", "2014-10-30"));
				nvps.add(new BasicNameValuePair("tour_flag", "dc"));
				nvps.add(new BasicNameValuePair("purpose_codes", "ADULT"));
				nvps.add(new BasicNameValuePair("query_from_station_name", paramMap.get("query_from_station_name")));
				nvps.add(new BasicNameValuePair("query_to_station_name", paramMap.get("query_to_station_name")));
				nvps.add(new BasicNameValuePair("undefined", ""));
				postOrder.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
				HttpResponse response1 = httpClientCheckOrder.execute(postOrder);
				System.out.println(EntityUtils.toString(response1.getEntity()));
	}
	
	/**
	 *  验证成功后，这里有3个 令牌
	 * @throws Exception
	 */
	public Map<String,String> initDc(String cookie){
		Map<String,String> tokenMap = new HashMap<String,String>();
		String url_initDc = "https://kyfw.12306.cn/otn/confirmPassenger/initDc";
		CloseableHttpClient httpClientInit = HttpClientUtil.createSSLClientDefault();
		HttpPost postInit = new HttpPost(url_initDc);
		postInit.setHeader("Cookie",cookie);
		
		HttpResponse response2 = null;
		try {
			response2 = httpClientInit.execute(postInit);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpEntity entity = response2.getEntity();
		String sb = null;
		try {
			sb = EntityUtils.toString(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		HttpEntity entity = response2.getEntity();
//		InputStream inputStream = entity.getContent();
//		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//		String lines = null;
//		StringBuffer sb = new StringBuffer();
//		while((lines = reader.readLine()) != null){
//			sb.append(lines+"\n");
//		}
//		inputStream.close();
//		reader.close();
		
		//key1 key_check_isChange
		int startIndex = sb.toString().lastIndexOf("key_check_isChange':'");
		int endIndex = sb.toString().lastIndexOf("','leftDetails");
		String key_check_isChange = sb.toString().substring(startIndex, endIndex);
		String[] key = key_check_isChange.split("':'");
		tokenMap.put(key[0], key[1]);
		
		//key2 leftTicketStr
		int startIndex1 = sb.toString().lastIndexOf("leftTicketStr':'");
		int endIndex1 = sb.toString().lastIndexOf("','limitBuySeatTicketDTO");
		String leftTicketStr = sb.toString().substring(startIndex1, endIndex1);
		String[] key1 = leftTicketStr.split("':'");
		tokenMap.put(key1[0], key1[1]);
		
		//key3 globalRepeatSubmitToken
		int startIndex2 = sb.toString().indexOf("globalRepeatSubmitToken = '");
		int endIndex2 = sb.toString().indexOf("';",startIndex2);
		String globalRepeatSubmitToken = sb.toString().substring(startIndex2, endIndex2);
		String[] tokens = globalRepeatSubmitToken.split("= '"); 
		System.out.println("---->"+tokens[0]);
		System.out.println("---->"+tokens[1]);
		tokenMap.put(tokens[0].trim(),tokens[1].trim());
		
		System.out.println("key_check_isChange--->"+tokenMap.get("key_check_isChange"));
		System.out.println("leftTicketStr---->"+tokenMap.get("leftTicketStr"));
		System.out.println("globalRepeatSubmitToken---->"+tokenMap.get("globalRepeatSubmitToken"));
		return tokenMap;
	}
	
	/**
	 * 获取提交订单前，验证码
	 */
	public HttpResponse getOrderPassCodeNew(String cookie) throws Exception{
		CloseableHttpClient httpClient = HttpClientUtil.createSSLClientDefault();
		HttpGet httpGetPassCodeNew = new HttpGet("https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=passenger&rand=randp");
		httpGetPassCodeNew.setHeader("Cookie", cookie);
		return httpClient.execute(httpGetPassCodeNew);
	}
}
