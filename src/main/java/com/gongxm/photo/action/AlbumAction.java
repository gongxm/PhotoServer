package com.gongxm.photo.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gongxm.photo.pojo.Category;
import com.gongxm.photo.service.CategoryService;

/** 
* @author 作者 : gongxm
* @version 创建时间：2019年12月30日 下午4:17:50 
* @description 描述 :
* 		
*/
@Controller
public class AlbumAction {
	@Autowired
	CategoryService categoryService;
	//名站
	@RequestMapping("/album/{keyword}")
	public ModelAndView site(ModelAndView view,@PathVariable("keyword")String keyword) {
		String type = "";
		if("国产名站".contentEquals(keyword)) {
			type="0-1";
		}else if("港台名站".contentEquals(keyword)) {
			type="1-1";
		}else if("日韩名站".contentEquals(keyword)) {
			type="2-1";
		}else if("国产模特".contentEquals(keyword)) {
			type="0-2";
		}else if("港台模特".contentEquals(keyword)) {
			type="1-2";
		}else if("日韩模特".contentEquals(keyword)) {
			type="2-2";
		}else {
			type="3";
		}
		
		
		List<Category> categories = categoryService.findCategoryByType(type);
		
		view.addObject("categories", categories);
		
		view.setViewName("album");
		
		return view;
	}
}
