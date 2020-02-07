package com.gongxm.photo.utils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.gongxm.photo.MyConstants;

/**
 * Created by gongxm on 2017/7/11.
 */

public class HttpUtils {

	static {
		// 设置https协议访问
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");
	}

	/**
	 * 获取文档对象
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static Document getDocument(String url) throws IOException {
		Document doc = Jsoup.connect(url).timeout(60000).header("Accept-Language", "zh-CN,zh;q=0.9")
				.header("Cache-Control", "max-age=0").header("Connection", "keep-alive")
				.header("Host", "www.lingyutxt.com")
				.header("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
				.header("User-Agent",
						"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
				.get();
		return doc;
	}

	/**
	 * 
	 * @param path 接收网络链接
	 * @return 返回html内容
	 * @throws IOException
	 */
	public static String readHtml(String url) throws IOException {

		// 获取整个页面文件
		Response response = Jsoup.connect(url)
				// 需要加上userAgent才能伪装成浏览器而不会被网站屏蔽IP
				.userAgent(MyConstants.USER_AGENT)
				// 设置超时
				.timeout(300000)
				// 设置抓取网页最大为10M
				.maxBodySize(10240000)
				// 请求网址
				.ignoreContentType(true).execute();

		byte[] bytes = response.bodyAsBytes();

		return new String(bytes);
	}

	// 获取网络文件大小: 需要响应头返回length才有效
	public static long getFileLength(String imgUrl) throws Exception {
		URL url = new URL(imgUrl);
		URLConnection connection = url.openConnection();
		long length = connection.getContentLengthLong();
		return length;
	}

	private static final class DefaultTrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}

	public static HttpsURLConnection getHttpsURLConnection(String uri, String method) throws IOException {
		SSLContext ctx = null;
		try {
			ctx = SSLContext.getInstance("TLS");
			ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		SSLSocketFactory ssf = ctx.getSocketFactory();

		URL url = new URL(uri);
		HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
		httpsConn.setSSLSocketFactory(ssf);
		httpsConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		httpsConn.setRequestProperty("Authorization", "username");
		httpsConn.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		/*
		在握手期间，如果 URL 的主机名和服务器的标识主机名不匹配，
		则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。
		策略可以是基于证书的或依赖于其他验证方案。
		当验证 URL 主机名使用的默认规则失败时使用这些回调。
		 */
		httpsConn.setHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
		});
		httpsConn.setRequestMethod(method);
		httpsConn.setDoInput(true);
		httpsConn.setDoOutput(true);
		return httpsConn;
	}
}
