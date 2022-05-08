package com.gongxm.photo.runnable;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gongxm.photo.BeanContext;
import com.gongxm.photo.MyConstants;
import com.gongxm.photo.pojo.ImageGroupInfo;
import com.gongxm.photo.pojo.ListUrl;
import com.gongxm.photo.service.ImageGroupInfoService;
import com.gongxm.photo.service.PhotoService;
import com.gongxm.photo.utils.HttpUtils;

public class ImageGroupCollectRunnable implements Runnable {

	private ListUrl listUrl;

	public ImageGroupCollectRunnable(ListUrl listUrl) {
		this.listUrl=listUrl;
	}

	@Override
	public void run() {
		PhotoService photoService = BeanContext.getApplicationContext().getBean(PhotoService.class);
		ImageGroupInfoService imageGroupInfoService = BeanContext.getApplicationContext().getBean(ImageGroupInfoService.class);
		String url = listUrl.getUrl();
		try {
			Document doc = HttpUtils.getDocument(url);
			if(doc!=null) {
				Elements divs = doc.select(".pr-0");
				if(divs!=null && divs.size()>0) {
					for (Element div : divs) {
						String absUrl = div.selectFirst(".text-dark").absUrl("href");
						String cover = div.selectFirst(".w-100").absUrl("src");
						String title = div.selectFirst(".my-1").text();
						try {
							ImageGroupInfo groupInfo = new ImageGroupInfo(cover,title,absUrl);
							ImageGroupInfo dbInfo = imageGroupInfoService.findImageGroupInfoById(groupInfo.getId());
							if(dbInfo!=null) {
								System.out.println(dbInfo.getTitle()+"已存在,跳过");
							}else {
								imageGroupInfoService.addImageGroupInfo(groupInfo);
							}
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("Add ImageGroupInfo Error:"+absUrl+", Because:"+e.getMessage());
						}
					}
				}
			}
			
			listUrl.setStatus(MyConstants.COLLECT_STATUS_COLLECTED);
			photoService.updateListUrl(listUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
