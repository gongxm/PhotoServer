package com.gongxm.photo.service;

import java.util.List;

import com.gongxm.photo.pojo.Category;

/**
 * @author 作者 : gongxm
 * @version 创建时间：2019年12月30日 上午11:26:47
 * @description 描述 :
 * 
 */
public interface CategoryService {

	List<Category> findCategoryByImageGroupId(String imageGroupId);

	int getCountByTag(String tag);

	List<Category> findCategoryByType(String type);

	Category findCategoryByTag(String text);

}
