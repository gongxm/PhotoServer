package com.gongxm.photo.runnable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.gongxm.photo.BeanContext;
import com.gongxm.photo.MyConstants;
import com.gongxm.photo.pojo.ImageGroupInfo;
import com.gongxm.photo.pojo.ImageInfo;
import com.gongxm.photo.service.ImageGroupInfoService;
import com.gongxm.photo.service.PhotoService;
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
		System.out.println("下载图片:"+url);
		ImageGroupInfoService imageGroupInfoService = BeanContext.getApplicationContext().getBean(ImageGroupInfoService.class);
		String imageGroupId = imageInfo.getImageGroupId();
		ImageGroupInfo imageGroupInfo = imageGroupInfoService.findImageGroupInfoById(imageGroupId);
		String title = imageGroupInfo.getTitle();
		int hashCode = title.hashCode();
		//计算一级目录
		int ONE = hashCode&0xf;
		hashCode = hashCode >> 4;
		//计算二级目录
		int TWO = hashCode&0xf;
		
		//存储路径
		String path = MyConstants.DIR_PATH+File.separator+ONE+File.separator+TWO+File.separator+title;
		
		String cover = imageGroupInfo.getCover();
		if(!cover.startsWith(MyConstants.TU_CHUANG)) {
			try {
				DownloadUtils.download(path, "cover.jpg", cover);
				imageGroupInfo.setCover((path.replace(MyConstants.DIR_PATH, MyConstants.TU_CHUANG)+File.separator+"cover.jpg").replace("\\", "/"));
				imageGroupInfoService.updateImageGroupInfo(imageGroupInfo);
			} catch (IOException e) {
				if(e instanceof FileNotFoundException) {
					System.out.println("封面文件不存在!");
				}else {
					e.printStackTrace();
				}
			}
		}
		
		if(url.startsWith(MyConstants.TU_CHUANG)) {
			return;
		}
		
		String fileName = MD5Utils.creatID(url)+".jpg";
		PhotoService photoService = BeanContext.getApplicationContext().getBean(PhotoService.class);
		try {
			DownloadUtils.download(path, fileName, url);
			imageInfo.setUrl((path.replace(MyConstants.DIR_PATH, MyConstants.TU_CHUANG)+File.separator+fileName).replace("\\", "/"));
			imageInfo.setStatus(MyConstants.COLLECT_STATUS_COLLECTED);
			photoService.updateImageInfo(imageInfo);
		} catch (IOException e) {
			if(e instanceof FileNotFoundException) {
				System.out.println("图片文件不存在!");
				imageInfo.setStatus(MyConstants.COLLECT_STATUS_FAILURE);
				photoService.updateImageInfo(imageInfo);
			}else {
				e.printStackTrace();
			}
		}
	}

}
