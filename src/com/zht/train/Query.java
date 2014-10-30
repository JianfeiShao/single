package com.zht.train;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
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
}
