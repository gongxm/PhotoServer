package com.gongxm.photo.runnable;

import java.io.IOException;

import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gongxm.photo.BeanContext;
import com.gongxm.photo.MyConstants;
import com.gongxm.photo.pojo.Category;
import com.gongxm.photo.pojo.ImageCategoryRelation;
import com.gongxm.photo.pojo.ImageGroupInfo;
import com.gongxm.photo.pojo.ImagePage;
import com.gongxm.photo.service.ImageGroupInfoService;
import com.gongxm.photo.service.PhotoService;
import com.gongxm.photo.utils.HttpUtils;

public class ImagePageCollectRunnable implements Runnable {

	private ImageGroupInfo groupInfo;

	public ImagePageCollectRunnable(ImageGroupInfo groupInfo) {
		this.groupInfo = groupInfo;
	}

	@Override
	public void run() {
		PhotoService photoService = BeanContext.getApplicationContext().getBean(PhotoService.class);
		ImageGroupInfoService imageGroupInfoService = BeanContext.getApplicationContext()
				.getBean(ImageGroupInfoService.class);
		String groupInfoId = groupInfo.getId();
		String baseUrl = groupInfo.getUrl();
		try {
			int index = 1;
			while (true) {
				String url = baseUrl + "/" + index;
				System.out.println("采集:" + url);
				ImagePage page = new ImagePage(url, groupInfoId, index);
				try {
					photoService.addImagePage(page);
				} catch (Exception e1) {
					System.out.println("添加图片页面链接失败,原因:" + e1.getMessage());
				}
				Document doc = HttpUtils.getDocument(url);

				if (index == 1) {
					Elements tags = doc.select("body>div.container>div.row>div.col-md-12>div.mb-3>a");
					if (tags != null && tags.size() > 1) {
						String type = "";
						String country = "";
						for (int i = 0; i < tags.size(); i++) {

							Element tagElement = tags.get(i);
							String tag = tagElement.text();
							if (StringUtil.isBlank(tag) || "首页".equals(tag) || "正文".equals(tag)) {
								System.out.println("无效标签,跳过...");
								continue;
							}
							if (i == 0) {
								if ("国产".contentEquals(tag)) {
									country = "0";
								}
								if ("港台".contentEquals(tag)) {
									country = "1";
								}
								if ("日韩".contentEquals(tag)) {
									country = "2";
								}
								type = "";
							} else if (i >= 1 && i <= 2) {
								type = "-" + i;
							} else {
								country = "";
								type = "3";
							}

							// 查找是否有该标签
							Category category = photoService.findCategoryByTag(tag);
							if (category != null) {
								Integer tagId = category.getId();
								ImageCategoryRelation relation = photoService.findImageCategoryRelation(groupInfoId,
										tagId);
								if (relation != null) {
									System.out.println("标签存在,跳过...");
									continue;
								}
								System.out.println("添加已有标签:" + tag);
								photoService.addImageCategoryRelation(groupInfoId, tagId);
							} else {
								System.out.println("添加新标签:" + tag);
								photoService.addImageCategoryRelation(groupInfoId, tag, country + type);
							}
						}
					}
				}

				try {
					Element e = doc.selectFirst(".pagination");
					if (!e.text().contains("下一页")) {
						break;
					}
				} catch (Exception e) {
					break;
				}
				index++;
			}
			groupInfo.setStatus(MyConstants.COLLECT_STATUS_COLLECTED);
			imageGroupInfoService.updateImageGroupInfo(groupInfo);
			System.out.println(baseUrl + ":采集完成!");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(baseUrl + ":采集出错!");
		}

	}

}
