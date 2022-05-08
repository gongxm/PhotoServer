package com.gongxm.photo.service.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.stereotype.Service;

import com.gongxm.photo.mapper.CategoryMapper;
import com.gongxm.photo.mapper.ImageCategoryRelationMapper;
import com.gongxm.photo.pojo.Category;
import com.gongxm.photo.pojo.ImageCategoryRelation;
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
	
	@Autowired
	SolrTemplate solrTemplate;
	
	final String CATEGORY_COLLECTION = "PhotoServerCategory";
	final String RELATION_COLLECTION = "PhotoServerRelation";

	@Cacheable(value = "tags", key = "'tag_'+#imageGroupId")
	@Override
	public List<Category> findCategoryByImageGroupId(String imageGroupId) {
		
		Query query = new SimpleQuery("*:*");
		query.addCriteria(new Criteria("image_group_id").is(imageGroupId));
		Page<ImageCategoryRelation> page = solrTemplate.query(RELATION_COLLECTION, query, ImageCategoryRelation.class);
		List<ImageCategoryRelation> relations = page.getContent();
		List<Category> categories = new LinkedList<Category>();
		if (relations != null && relations.size() > 0) {
			List<Integer> ids = new LinkedList<Integer>();
			for (ImageCategoryRelation relation : relations) {
				ids.add(relation.getCategoryId());
			}
			Collection<Category> collection = solrTemplate.getByIds(CATEGORY_COLLECTION, ids, Category.class);
			categories.addAll(collection);
		}
		return categories;
	}

	
	@Cacheable(value = "tags", key = "'count_'+#tag")
	@Override
	public int getCountByTag(String tag) {
		Query query = new SimpleQuery("*:*");
		query.addCriteria(new Criteria("category_tag").is(tag));
		Page<Category> page = solrTemplate.query(CATEGORY_COLLECTION, query, Category.class);
		List<Category> categories = page.getContent();
		
		if(categories!=null && categories.size()>0) {
			Category category = categories.get(0);
			Integer id = category.getId();
			Query query2 = new SimpleQuery("*:*");
			query2.setOffset(0L);
			query2.setRows(0);
			query2.addCriteria(new Criteria("category_id").is(id));
			return (int)solrTemplate.count(RELATION_COLLECTION, query2);
		}
		
		return 0;
	}

	@Cacheable(value = "tags", key = "'list_'+#type")
	@Override
	public List<Category> findCategoryByType(String type) {
		Query query = new SimpleQuery("*:*");
		query.addCriteria(new Criteria("category_type").is(type));
		Page<Category> page = solrTemplate.query(CATEGORY_COLLECTION, query, Category.class);
		return page.getContent();
	}
	
	
	@Cacheable(value = "tags",key = "#tag")
	@Override
	public Category findCategoryByTag(String tag) {
		Query query = new SimpleQuery("*:*");
		query.addCriteria(new Criteria("category_tag").is(tag));
		Page<Category> page = solrTemplate.query(CATEGORY_COLLECTION, query, Category.class);
		List<Category> list = page.getContent();
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		return 	null;
	}


}
