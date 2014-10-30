package com.zht.train;

import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class x0 {
	public static void main(String[] args) throws Exception{
		CloseableHttpClient httpClient = x0.createSSLClientDefault();
		HttpGet get = new HttpGet();
//		
		get.setURI(new URI("https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand"));
//		post.setHeader("Host", "kyfw.12306.cn");
//		post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");
//		post.setHeader("Accept", "*/*");
//		post.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
//		post.setHeader("Accept-Encoding", "gzip, deflate");
//		post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//		post.setHeader("X-Requested-With", "XMLHttpRequest");
//		post.setHeader("Referer", "https://kyfw.12306.cn/otn/login/init");
//		post.setHeader("Content-Length", "73");
//		post.setHeader("Cookie", "JSESSIONID=0A01D47FFC623DECA5C5E6E3818972F4DB205C3B36; _jc_save_fromStation=%u5317%u4EAC%2CBJP; _jc_save_toStation=%u9A7B%u9A6C%u5E97%2CZDN; _jc_save_fromDate=2014-10-19; _jc_save_toDate=2014-10-19; _jc_save_wfdc_flag=dc; BIGipServerotn=2144600330.64545.0000");
//		post.setHeader("Connection","keep-alive");
//		post.setHeader("Pragma","no-cache");
		
		HttpResponse result = httpClient.execute(get);
		System.out.println("---->"+result.getFirstHeader("Set-Cookie"));
	}

	public static CloseableHttpClient createSSLClientDefault() throws Exception {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
					null, new TrustStrategy() {
						// 信任所有
						public boolean isTrusted(X509Certificate[] chain,
								String authType) throws CertificateException {
							return true;
						}
					}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (Exception e) {
			e.printStackTrace();
			// } catch (NoSuchAlgorithmException e) {
			// e.printStackTrace();
			// } catch (KeyStoreException e) {
			// e.printStackTrace();
		}
		return HttpClients.createDefault();
		// }
	}
}