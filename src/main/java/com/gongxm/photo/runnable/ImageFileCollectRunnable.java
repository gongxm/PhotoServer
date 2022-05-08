package com.gongxm.photo.runnable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.gongxm.photo.BeanContext;
import com.gongxm.photo.Config;
import com.gongxm.photo.MyConstants;
import com.gongxm.photo.pojo.ImageGroupInfo;
import com.gongxm.photo.pojo.ImageInfo;
import com.gongxm.photo.service.ImageGroupInfoService;
import com.gongxm.photo.service.ImageService;
import com.gongxm.photo.utils.DownloadUtils;
import com.gongxm.photo.utils.MD5Utils;

public class ImageFileCollectRunnable implements Runnable {
	
	private ImageInfo imageInfo;

	public ImageFileCollectRunnable(ImageInfo imageInfo) {
		this.imageInfo=imageInfo;
	}

	@Override
	public void run() {
		String url = imageInfo.getUrl();
		System.out.println("download image:"+url);
		ImageGroupInfoService imageGroupInfoService = BeanContext.getApplicationContext().getBean(ImageGroupInfoService.class);
		Config config = BeanContext.getApplicationContext().getBean(Config.class);
		String imageGroupId = imageInfo.getImageGroupId();
		ImageGroupInfo imageGroupInfo = imageGroupInfoService.findImageGroupInfoById(imageGroupId);
		String title = imageGroupInfo.getTitle();
		int hashCode = title.hashCode();
		//计算一级目录
		int ONE = hashCode&0xf;
		hashCode = hashCode >> 4;
		//计算二级目录
		int TWO = hashCode&0xf;
		
		String image_path = config.getImagePath();
		//存储路径
		String path = image_path +File.separator+ONE+File.separator+TWO+File.separator+title.replace("<", "").replace(">", "").replace("|", "").replace("\\", "").replace("/", "")
				.replace(":", "").replace("*", "").replace("?", "").replace("\"", "");
		String cover = imageGroupInfo.getCover();
		String referer = imageGroupInfo.getUrl();
		if(!cover.startsWith(MyConstants.TU_CHUANG)) {
			try {
				DownloadUtils.download(referer,path, "cover.jpg", cover);
				imageGroupInfo.setCover((path.replace(image_path, MyConstants.TU_CHUANG)+File.separator+"cover.jpg").replace("\\", "/"));
				imageGroupInfoService.updateImageGroupInfo(imageGroupInfo);
			} catch (IOException e) {
				if(e instanceof FileNotFoundException) {
					System.out.println("cover not found!");
				}else {
					e.printStackTrace();
				}
			}
		}
		
		if(url.startsWith(MyConstants.TU_CHUANG)) {
			return;
		}
		
		String fileName = MD5Utils.creatID(url)+".jpg";
		ImageService imageService = BeanContext.getApplicationContext().getBean(ImageService.class);
		try {
			DownloadUtils.download(referer,path, fileName, url);
			imageInfo.setUrl((path.replace(image_path, MyConstants.TU_CHUANG)+File.separator+fileName).replace("\\", "/"));
			imageInfo.setStatus(MyConstants.COLLECT_STATUS_COLLECTED);
			imageService.updateImageInfo(imageInfo);
		} catch (IOException e) {
			if(e instanceof FileNotFoundException) {
				System.out.println("image not found!");
				imageInfo.setStatus(MyConstants.COLLECT_STATUS_FAILURE);
				imageService.updateImageInfo(imageInfo);
			}else {
				e.printStackTrace();
			}
		}
	}

}
