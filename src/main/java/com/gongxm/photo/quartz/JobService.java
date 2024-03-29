package com.gongxm.photo.quartz;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.gongxm.photo.service.PhotoService;

@Service
@PropertySource("classpath:config.properties")
public class JobService {
	@Autowired
	PhotoService photoService;

	@Value("${autoUpdate}")
	private boolean autoUpdate;
	
	@Value("${download}")
	private boolean download;

	// 每6小时更新
	@Scheduled(cron = "0 0 0/6 * * ?")
	public void updateImageGroup() throws IOException {
		if (autoUpdate) {
			System.out
					.println("update image group info, current time :" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			Jsoup.connect("http://localhost:9000/action/update_image_group_info.action").ignoreContentType(true).get();
		}
	}

	// 每6小时更新
	@Scheduled(cron = "0 5 0/6 * * ?")
	public void updateImagePage() throws IOException {
		if (autoUpdate) {
			System.out
					.println("update image page, current time :" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			Jsoup.connect("http://localhost:9000/action/collect_image_page.action").ignoreContentType(true).get();
		}
	}

	// 每6小时更新
	@Scheduled(cron = "0 20 0/6 * * ?")
	public void updateImageInfo() throws IOException {
		if (autoUpdate) {
			System.out
					.println("update image url, current time :" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			Jsoup.connect("http://localhost:9000/action/collect_image_info.action").ignoreContentType(true).get();
		}
	}

	// 每6小时更新
	@Scheduled(cron = "0 50 0/6 * * ?")
	public void updateImageFile() throws IOException {
		if (autoUpdate && download) {
			System.out
					.println("download image, current time :" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			Jsoup.connect("http://localhost:9000/action/collect_image_file.action").ignoreContentType(true).get();
		}
	}
}
