package com.zht.train.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequest 工具
 */
public class HttpRequestTool {
	/**
	 * 获取多个请求参数，（在不用模型的情况下）
	 * @param request HttpServletRequest
	 * @param params String...string
	 * @return Map type String
	 */
	public static Map<String,String> getMoreParameters(HttpServletRequest request,String ...params){
		Map<String,String> paramMap = new HashMap<String, String>();
		for (String key : params) {
			String  value = request.getParameter(key);
			paramMap.put(key, value);
		}
		return paramMap;
	}
}
