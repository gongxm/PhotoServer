package com.gongxm.photo.runnable;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gongxm.photo.BeanContext;
import com.gongxm.photo.MyConstants;
import com.gongxm.photo.pojo.ImageInfo;
import com.gongxm.photo.pojo.ImagePage;
import com.gongxm.photo.service.ImageService;
import com.gongxm.photo.service.PhotoService;
import com.gongxm.photo.utils.HttpUtils;

/**
 * @author 作者 : gongxm
 * @version 创建时间：2019年12月17日 下午3:28:27
 * @description 描述 :
 * 
 */
public class ImageInfoCollectRunnable implements Runnable {

	private ImagePage imagePage;

	public ImageInfoCollectRunnable(ImagePage imagePage) {
		this.imagePage = imagePage;
	}

	@Override
	public void run() {
		try {
			ImageService imageService = BeanContext.getApplicationContext().getBean(ImageService.class);
			PhotoService photoService = BeanContext.getApplicationContext().getBean(PhotoService.class);
			String imageGroupId = imagePage.getImageGroupId();
			String url = imagePage.getUrl();
			Integer page = imagePage.getPosition();
			int pageSize = 5;
			int offset = (page-1)*pageSize;
			
			Document doc = HttpUtils.getDocument(url);
//			Element div = doc.selectFirst(".content");
			Element div = doc.selectFirst(".imgbox");
			Elements links = div.select("a");
			if (links != null && links.size() > 0) {
				for (int i = 0; i < links.size(); i++) {
					String imgUrl = links.get(i).absUrl("href");
					ImageInfo info = new ImageInfo(imgUrl, imageGroupId,i+offset);
					ImageInfo dbInfo = imageService.findImageInfoById(info.getId());
					if(dbInfo!=null) {
						System.out.println(dbInfo.getId()+":图片链接已存在,跳过:"+imgUrl);
					}else {
						imageService.addImageInfo(info);
						System.out.println("Add image link!");
					}
				}
			}
			imagePage.setStatus(MyConstants.COLLECT_STATUS_COLLECTED);
			photoService.updateImagePage(imagePage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
