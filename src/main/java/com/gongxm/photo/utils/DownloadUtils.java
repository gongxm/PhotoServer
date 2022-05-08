package com.gongxm.photo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.net.ssl.HttpsURLConnection;

public class DownloadUtils {
	
	public static void download(String referer,String dirPath,String fileName,String urlPath) throws IOException {
		
		System.out.println("download image file:"+fileName);
		
		HttpsURLConnection con = HttpUtils.getHttpsURLConnection(urlPath, "GET");
		
		con.setRequestProperty("Referer", referer);
		
		//设置超时
		con.setConnectTimeout(60000);
		con.setReadTimeout(60000);
		 
		InputStream is = con.getInputStream();
		
		File dir = new File(dirPath);
		
		if(!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir,fileName);
		
		System.out.println("下载图片:"+file.getAbsolutePath());
		
		FileOutputStream fos = new FileOutputStream(file);
		
		byte[] buff = new byte[8192];
		
		int len;
		
		while((len=is.read(buff))!=-1) {
			fos.write(buff, 0, len);
		}
		
		fos.close();
		is.close();
	}
	
	
	public static void main(String[] args) throws IOException {
		String dirPath="D:\\Documents\\Desktop\\test";
		String urlPath="https://cdn.imgupio.com/eyJ1cmwiOiAiaHR0cDovL3d3dy5ubGVncy5jb20vaW1hZ2VzLzIwMjAvMDcvMjUvMTU3MjYvSTE1OWtmLmpwZyIsICJyZWZlcmVyIjogImh0dHBzOi8vd3d3Lm5sZWdzLmNvbS8ifQ==";
		String fileName = MD5Utils.creatID(urlPath)+".jpg";
		String referer = "";
		download(referer,dirPath, fileName, urlPath);
		
	}
}
