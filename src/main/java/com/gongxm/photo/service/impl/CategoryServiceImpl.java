package com.gongxm.photo.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gongxm.photo.mapper.CategoryMapper;
import com.gongxm.photo.mapper.ImageCategoryRelationMapper;
import com.gongxm.photo.pojo.Category;
import com.gongxm.photo.pojo.CategoryExample;
import com.gongxm.photo.pojo.ImageCategoryRelation;
import com.gongxm.photo.pojo.ImageCategoryRelationExample;
import com.gongxm.photo.service.CategoryService;

/**
 * @author 作者 : gongxm
 * @version 创建时间：2019年12月30日 上午11:27:02
 * @description 描述 :
 * 
 */
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryMapper categoryMapper;
	@Autowired
	ImageCategoryRelationMapper imageCategoryRelationMapper;

	@Cacheable(value = "tags", key = "'tag_'+#imageGroupId")
	@Override
	public List<Category> findCategoryByImageGroupId(String imageGroupId) {
		ImageCategoryRelationExample example = new ImageCategoryRelationExample();
		example.createCriteria().andImageGroupIdEqualTo(imageGroupId);
		List<ImageCategoryRelation> relations = imageCategoryRelationMapper.selectByExample(example);
		List<Category> categories = new LinkedList<Category>();
		if (relations != null && relations.size() > 0) {
			for (ImageCategoryRelation relation : relations) {
				Category category = categoryMapper.selectByPrimaryKey(relation.getCategoryId());
				categories.add(category);
			}
		}
		return categories;
	}

	@Override
	public int getCountByTag(String tag) {
		CategoryExample example = new CategoryExample();
		example.createCriteria().andTagEqualTo(tag);
		List<Category> tags = categoryMapper.selectByExample(example);
		if(tags!=null && tags.size()>0) {
			Category category = tags.get(0);
			Integer tagId = category.getId();
			ImageCategoryRelationExample exam = new ImageCategoryRelationExample();
			exam.createCriteria().andCategoryIdEqualTo(tagId);
			return imageCategoryRelationMapper.countByExample(exam );
		}
		return 0;
	}

	@Override
	public List<Category> findCategoryByType(String type) {
		CategoryExample example = new CategoryExample();
		example.createCriteria().andTypeEqualTo(type);
		return categoryMapper.selectByExample(example);
	}

}
