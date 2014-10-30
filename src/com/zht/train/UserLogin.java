package com.zht.train;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class UserLogin {
	String url_getPassCodeNew ="https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand";//登陆验证码
	String url_loginAysnSuggest = "https://kyfw.12306.cn/otn/login/loginAysnSuggest";//登录信息验证
	String url_userLogin = "https://kyfw.12306.cn/otn/login/userLogin";//正式登录
	String url_getPassengerDTOs = "https://kyfw.12306.cn/otn/confirmPassenger/getPassengerDTOs";//获取乘客信息
	
	
	/**
	 * 登陆验证码
	 * @return
	 */
	public HttpResponse getPassCodeNew()throws Exception{
		CloseableHttpClient httpClient = HttpClientUtil.createSSLClientDefault();
		HttpGet getPassCodeNew = new HttpGet(url_getPassCodeNew);
		return httpClient.execute(getPassCodeNew);
	}
	
	
	/**
	 * 用户登陆，获得登陆信息
	 * @param jsessionid
	 * @param randCode
	 * @return
	 * @throws Exception
	 */
	public String getUser(String cookies,String randCode) throws Exception{
		CloseableHttpClient httpClient = HttpClientUtil
				.createSSLClientDefault();
		HttpPost postLoginAysnSuggest = new HttpPost(url_loginAysnSuggest);
		
		postLoginAysnSuggest.setHeader("Cookie", cookies);
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("loginUserDTO.user_name", "s940053457"));//"wangping1125"));
		nvps.add(new BasicNameValuePair("userDTO.password", "love1457"));//"wangping1125"));
		nvps.add(new BasicNameValuePair("randCode", randCode));// 这里是验证码
		postLoginAysnSuggest.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		
		HttpPost postUserLogin = new HttpPost(url_userLogin);
		postUserLogin.setHeader("Cookie", cookies);
		
		HttpResponse result = httpClient.execute(postLoginAysnSuggest);
		HttpResponse result1 = httpClient.execute(postUserLogin);//这一步必须有，这个才是正式登陆
		EntityUtils.toString(result1.getEntity());
		return EntityUtils.toString(result.getEntity());
	}
	
	/**
	 * 获取乘客，自己资料定义的
	 * @return
	 */
	public String getPassengerDTOs(String cookies) throws Exception{
		String cookie = cookies;
		CloseableHttpClient httpClient = HttpClientUtil.createSSLClientDefault();
		HttpPost httpPost = new HttpPost(url_getPassengerDTOs);
		httpPost.setHeader("Cookie", cookie);
		HttpResponse response = httpClient.execute(httpPost);
		return EntityUtils.toString(response.getEntity());
	}
}
