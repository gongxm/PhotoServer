package com.gongxm.photo.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gongxm.photo.domain.ResponseResult;
import com.gongxm.photo.pojo.ImageGroupInfo;
import com.gongxm.photo.pojo.ImageInfo;
import com.gongxm.photo.pojo.ImagePage;
import com.gongxm.photo.pojo.ListUrl;
import com.gongxm.photo.runnable.ImageFileCollectRunnable;
import com.gongxm.photo.runnable.ImageGroupCollectRunnable;
import com.gongxm.photo.runnable.ImageInfoCollectRunnable;
import com.gongxm.photo.runnable.ImagePageCollectRunnable;
import com.gongxm.photo.service.ImageGroupInfoService;
import com.gongxm.photo.service.ImageService;
import com.gongxm.photo.service.PhotoService;
import com.gongxm.photo.utils.ThreadPoolUtils;

@RestController
@RequestMapping("/action/")
@PropertySource("classpath:config.properties")
public class CollectAction {
	@Autowired
	PhotoService photoService;
	@Autowired
	ImageService imageService;
	@Autowired
	ImageGroupInfoService imageGroupInfoService;
	@Value("${startIndex}")
	private int startIndex;
	@Value("${endIndex}")
	private int endIndex;

	// 生成采集列表
	@RequestMapping("create_url_list.action")
	public ResponseResult createUrlList() {
		ResponseResult result = new ResponseResult();
		String baseUrl = "https://kuqur.com/page/";
		System.out.println("startIndex=" + startIndex);
		System.out.println("endIndex=" + endIndex);
		for (int i = startIndex; i <= endIndex; i++) {
			System.out.println("insert collect url:" + i);
			photoService.addListUrl(baseUrl + i);
		}
		result.setSuccess();
		return result;
	}

	// 采集图片组信息
	@RequestMapping("collect_image_group_info.action")
	public ResponseResult collectImageGroupInfo() {
		ResponseResult result = new ResponseResult();

		int total = photoService.getListUrlUnCollectCount();
		int page = 1;
		int pageSize = 20;
		int temp = total / pageSize;
		int totalPage = total % pageSize == 0 ? temp : temp + 1;
		while (page <= totalPage) {
			List<ListUrl> list = photoService.findUnCollectListUrl(page, pageSize);
			for (ListUrl listUrl : list) {
				Runnable task = new ImageGroupCollectRunnable(listUrl);
				ThreadPoolUtils.execute(task);
			}
			page++;
		}

		result.setSuccess();
		return result;
	}

	// 采集图片页面地址
	@RequestMapping("collect_image_page.action")
	public ResponseResult collectImagePage() {
		ResponseResult result = new ResponseResult();

		int total = imageGroupInfoService.getImageGroupUnCollectCount();
		int page = 1;
		int pageSize = 20;
		int temp = total / pageSize;
		int totalPage = total % pageSize == 0 ? temp : temp + 1;
		while (page <= totalPage) {
			List<ImageGroupInfo> list = imageGroupInfoService.findUnCollectImageGroup(page, pageSize);
			for (ImageGroupInfo groupInfo : list) {
				Runnable task = new ImagePageCollectRunnable(groupInfo);
				ThreadPoolUtils.execute(task);
			}
			page++;
		}

		result.setSuccess();
		return result;
	}

	// 采集图片地址
	@RequestMapping("collect_image_info.action")
	public ResponseResult collectImageInfo() {
		ResponseResult result = new ResponseResult();

		int total = photoService.getImagePageUnCollectCount();
		int page = 1;
		int pageSize = 20;
		int temp = total / pageSize;
		int totalPage = total % pageSize == 0 ? temp : temp + 1;
		while (page <= totalPage) {
			List<ImagePage> list = photoService.findUnCollectImagePage(page, pageSize);
			for (ImagePage imagePage : list) {
				Runnable task = new ImageInfoCollectRunnable(imagePage);
				ThreadPoolUtils.execute(task);
			}
			page++;
		}

		result.setSuccess();
		return result;
	}

	// 采集图片文件
	@RequestMapping("collect_image_file.action")
	public ResponseResult collectImageFile() {
		ResponseResult result = new ResponseResult();

		int total = imageService.getImageInfoUnCollectCount();
		int page = 1;
		int pageSize = 20;
		int temp = total / pageSize;
		int totalPage = total % pageSize == 0 ? temp : temp + 1;
		while (page <= totalPage) {
			List<ImageInfo> list = imageService.findUnCollectImageInfo(page, pageSize);
			for (ImageInfo imageInfo : list) {
				Runnable task = new ImageFileCollectRunnable(imageInfo);
				ThreadPoolUtils.execute(task);
			}
			page++;
		}

		result.setSuccess();
		return result;
	}

	// 定时更新
	@RequestMapping("update_image_group_info.action")
	public void updateListUrl() {
		System.out.println(".........update ListUrl........");
		ListUrl listUrl = photoService.getListUrlById(818);
		System.out.println("listUrl:" + listUrl.getUrl());
		Runnable task = new ImageGroupCollectRunnable(listUrl);
		ThreadPoolUtils.execute(task);
	}

}
