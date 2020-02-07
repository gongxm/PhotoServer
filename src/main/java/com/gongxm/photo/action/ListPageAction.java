package com.gongxm.photo.action;

import java.util.List;

import org.jsoup.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gongxm.photo.domain.Page;
import com.gongxm.photo.pojo.Category;
import com.gongxm.photo.pojo.ImageGroupInfo;
import com.gongxm.photo.pojo.ImageInfo;
import com.gongxm.photo.service.CategoryService;
import com.gongxm.photo.service.ImageGroupInfoService;
import com.gongxm.photo.service.ImageService;

/**
 * @author 作者 : gongxm
 * @version 创建时间：2019年12月29日 下午11:17:08
 * @description 描述 :
 * 
 */

@Controller
public class ListPageAction {
	@Autowired
	ImageGroupInfoService imageGroupInfoService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ImageService imageService;

	// 列表页
	@RequestMapping({ "/", "/index.html", "/page/{page}" })
	public ModelAndView getList(ModelAndView view, @PathVariable(value = "page", required = false) Integer page) {

		int pageSize = 30;

		if (page == null) {
			page = 1;
		}
		if (page == 1) {
			List<ImageGroupInfo> recommendList = imageGroupInfoService.findLastRecommend();
			view.addObject("recommendList", recommendList);
		}
		List<ImageGroupInfo> pageList = imageGroupInfoService.findPageList(page, pageSize);
		view.addObject("pageList", pageList);

		int total = imageGroupInfoService.getTotalCount();
		Page pageItem = new Page("page",total, page, pageSize);
		view.addObject("page", pageItem);
		view.setViewName("index");
		return view;
	}

	// 内容页
	@RequestMapping({ "/s/{id}", "/s/{id}/{page}" })
	public ModelAndView showPage(ModelAndView view, @PathVariable(value = "id", required = true) String id,
			@PathVariable(value = "page", required = false) Integer page,@CookieValue(value = "pageNumber",defaultValue = "5") Integer pageNumber) {
		
		int pageSize = 5;
		
		if(pageNumber!=null) {
			pageSize = pageNumber;
		}
		
		view.addObject("pageNumber", pageNumber);
		
		if (page == null) {
			page = 1;
		}
		// 取标签
		List<Category> categories = categoryService.findCategoryByImageGroupId(id);
		view.addObject("tags", categories);
		// 图片组信息
		ImageGroupInfo info = imageGroupInfoService.findImageGroupInfoById(id);
		view.addObject("info", info);
		// 取图片链接
		List<ImageInfo> images = imageService.findImageInfoByGroupId(id, page, pageSize);
		view.addObject("images", images);

		// 分页
		int total = imageService.getCountByGroupId(id);
		Page pageItem = new Page("s/"+id,total, page, pageSize);
		view.addObject("page", pageItem);
		// 推荐列表
		List<ImageGroupInfo> recommendList = imageGroupInfoService.findLastRecommend();
		view.addObject("recommendList", recommendList);
		view.setViewName("imagePage");
		return view;
	}

	// 获取推荐列表
	@RequestMapping("/recommend/new")
	@ResponseBody
	public String getRecommendList() {
		List<ImageGroupInfo> recommendList = imageGroupInfoService.findLastRecommend();
		StringBuilder sb = new StringBuilder();
		for (ImageGroupInfo info : recommendList) {
			sb.append("<div class='col-md-2 col-4 mb-3 pr-0'><a href='/s/").append(info.getId()).append("'><img src='")
					.append(info.getCover()).append("' class='w-100'></a></div>");
		}
		return sb.toString();
	}

	// 搜索
	@RequestMapping({ "/search", "/search/{keyword}/{page}"})
	public ModelAndView search(ModelAndView view,@RequestParam(value = "q",required = false)String q, @PathVariable(value = "keyword", required = false)String keyword,
			@PathVariable(value = "page", required = false) Integer page) {
		int pageSize = 24;
		if (page == null) {
			page = 1;
		}
		
		if(!StringUtil.isBlank(q)) {
			keyword = q;
		}
		
		List<ImageGroupInfo> searchList = imageGroupInfoService.findSearchList(keyword, page, pageSize);
		view.addObject("keyword", keyword);
		view.addObject("searchList", searchList);

		// 分页
		int total = imageGroupInfoService.getCountByKeyword(keyword);
		Page pageItem = new Page("search/"+keyword,total, page, pageSize);
		view.addObject("page", pageItem);

		view.setViewName("search");
		return view;
	}
	
	// tag
	@RequestMapping({"/tag/{tag}","/tag/{tag}/{page}"})
	public ModelAndView tag(ModelAndView view,@PathVariable(value = "tag")String tag,
			@PathVariable(value = "page", required = false) Integer page) {
		int pageSize = 24;
		if (page == null) {
			page = 1;
		}
		
		view.addObject("tag", tag);
		List<ImageGroupInfo> tagList = imageGroupInfoService.findImageGroupByTag(tag, page, pageSize);
		view.addObject("tagList", tagList);
		
		// 分页
		int total = categoryService.getCountByTag(tag);
		Page pageItem = new Page("tag/"+tag,total, page, pageSize);
		view.addObject("page", pageItem);
		
		view.setViewName("tag");
		return view;
	}
	
	

}
