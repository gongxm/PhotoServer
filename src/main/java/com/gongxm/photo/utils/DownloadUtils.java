package com.gongxm.photo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.net.ssl.HttpsURLConnection;

public class DownloadUtils {
	
	public static void download(String dirPath,String fileName,String urlPath) throws IOException {
		
		System.out.println("下载图片:"+fileName);
		
		HttpsURLConnection con = HttpUtils.getHttpsURLConnection(urlPath, "GET");
		
		//设置超时
		con.setConnectTimeout(60000);
		con.setReadTimeout(60000);
		 
		InputStream is = con.getInputStream();
		
		File dir = new File(dirPath);
		
		if(!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir,fileName);
		
		FileOutputStream fos = new FileOutputStream(file);
		
		byte[] buff = new byte[8192];
		
		int len;
		
		while((len=is.read(buff))!=-1) {
			fos.write(buff, 0, len);
		}
		
		fos.close();
		is.close();
	}
	
}
