package com.zht.train.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;

import sun.misc.BASE64Encoder;

public class HttpResponseTool {
	public static String getEntityBASE64Encoder(HttpResponse response){
		InputStream inputStream = null;
		BASE64Encoder encoder = null ;
		byte[] data = null;
		try { 
			inputStream = response.getEntity().getContent();//获取流
			int count = 0;
			while (count == 0) {
				count = (int) response.getEntity().getContentLength();
			}
			data = new byte[count];
			int readCount = 0;
			while (readCount < count) {
				readCount += inputStream.read(data, readCount, count - readCount);
			}
			inputStream.read(data);
			encoder = new BASE64Encoder();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return encoder.encode(data);
	}
}
