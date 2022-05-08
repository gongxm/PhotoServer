package com.gongxm.photo.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.stereotype.Service;

import com.gongxm.photo.MyConstants;
import com.gongxm.photo.mapper.ImageGroupInfoMapper;
import com.gongxm.photo.pojo.Category;
import com.gongxm.photo.pojo.ImageCategoryRelation;
import com.gongxm.photo.pojo.ImageGroupInfo;
import com.gongxm.photo.service.ImageGroupInfoService;

/**
 * @author 作者 : gongxm
 * @version 创建时间：2019年12月29日 下午11:31:14
 * @description 描述 :
 * 
 */
@Service
public class ImageGroupInfoServiceImpl implements ImageGroupInfoService {
	@Autowired
	ImageGroupInfoMapper imageGroupInfoMapper;

	final String PhotoServerImageGroup = "PhotoServerImageGroup";

	@Autowired
	SolrTemplate solrTemplate;

	@Override
	public List<ImageGroupInfo> findLastRecommend() {
		int num = (int) (Math.random() * 100000);
		Query query = new SimpleQuery("*:*");
		query.addSort(Sort.by(Order.desc("rand_" + num)));// 随机排序
		query.setOffset(0L);
		query.setRows(12);
		query.addCriteria(new Criteria("group_status").is(MyConstants.COLLECT_STATUS_COLLECTED));
		Page<ImageGroupInfo> result = solrTemplate.query(PhotoServerImageGroup, query, ImageGroupInfo.class);
		return result.getContent();
	}

	@Override
	public List<ImageGroupInfo> findPageList(Integer page, Integer pageSize) {
		long offset = (page - 1) * pageSize;
		Query query = new SimpleQuery("*:*");
		query.setOffset(offset);
		query.setRows(pageSize);
		query.addCriteria(new Criteria("group_status").is(MyConstants.COLLECT_STATUS_COLLECTED));
		query.addSort(Sort.by(Order.desc("group_update_time")));
		Page<ImageGroupInfo> result = solrTemplate.query(PhotoServerImageGroup, query, ImageGroupInfo.class);
		return result.getContent();
	}

	@Cacheable(value = "ImageGroup", key = "'getTotalCount'")
	@Override
	public int getTotalCount() {
		Query query = new SimpleQuery("*:*");
		query.setOffset(0L);
		query.setRows(0);
		return (int) solrTemplate.count(PhotoServerImageGroup, query);
	}

	@Override
	public ImageGroupInfo findImageGroupInfoById(String imageGroupInfoId) {
		Optional<ImageGroupInfo> optional = solrTemplate.getById(PhotoServerImageGroup, imageGroupInfoId,
				ImageGroupInfo.class);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@CacheEvict(value = "ImageGroup", allEntries = true)
	@Override
	public void addImageGroupInfo(ImageGroupInfo groupInfo) {
		imageGroupInfoMapper.insert(groupInfo);
		solrTemplate.saveBean(PhotoServerImageGroup, groupInfo);
		solrTemplate.commit(PhotoServerImageGroup);
	}

	@Override
	public int getImageGroupUnCollectCount() {
		Query query = new SimpleQuery("*:*");
		query.setOffset(0L);
		query.setRows(0);
		query.addCriteria(new Criteria("group_status").is(MyConstants.COLLECT_STATUS_UNCOLLECT));
		return (int) solrTemplate.count(PhotoServerImageGroup, query);
	}

	@Override
	public List<ImageGroupInfo> findUnCollectImageGroup(int page, int pageSize) {
		long offset = (page - 1) * pageSize;
		Query query = new SimpleQuery("*:*");
		query.setOffset(offset);
		query.setRows(pageSize);
		query.addCriteria(new Criteria("group_status").is(MyConstants.COLLECT_STATUS_UNCOLLECT));
		Page<ImageGroupInfo> result = solrTemplate.query(PhotoServerImageGroup, query, ImageGroupInfo.class);
		return result.getContent();
	}

	@Override
	public void updateImageGroupInfo(ImageGroupInfo groupInfo) {
		imageGroupInfoMapper.updateByPrimaryKey(groupInfo);
		solrTemplate.saveBean(PhotoServerImageGroup, groupInfo);
		solrTemplate.commit(PhotoServerImageGroup);
	}

	@Cacheable(value = "ImageGroup", key = "'search_'+#keyword+'_'+#page+'_'+#pageSize")
	@Override
	public List<ImageGroupInfo> findSearchList(String keyword, Integer page, Integer pageSize) {
		long offset = (page - 1) * pageSize;
		Query query = new SimpleQuery("*:*");
		Criteria criteria = new Criteria("image_keywords").is(keyword);
		query.addCriteria(criteria);
		query.setOffset(offset);
		query.setRows(pageSize);
		query.addCriteria(new Criteria("group_status").is(MyConstants.COLLECT_STATUS_COLLECTED));
		Page<ImageGroupInfo> result = solrTemplate.query(PhotoServerImageGroup, query, ImageGroupInfo.class);
		return result.getContent();
	}

	@Cacheable(value = "ImageGroup", key = "'count_'+#keyword")
	@Override
	public int getCountByKeyword(String keyword) {
		Query query = new SimpleQuery("*:*");
		Criteria criteria = new Criteria("image_keywords").is(keyword);
		query.addCriteria(criteria);
		query.setOffset(0L);
		query.setRows(0);
		int count = (int) solrTemplate.count(PhotoServerImageGroup, query);
		return count;
	}

	@Override
	public List<ImageGroupInfo> findImageGroupByTag(String tag, Integer page, Integer pageSize) {
		long offset = (page - 1) * pageSize;

		// 1.获取tag的ID

		// 2.获取ID对应的image_category_relation

		// 3.根据relation获取ImageGroup
		Query query = new SimpleQuery("*:*");
		Criteria criteria = new Criteria("category_tag").is(tag);
		query.addCriteria(criteria);
		Page<Category> Categories = solrTemplate.query("PhotoServerCategory", query, Category.class);
		List<Category> list = Categories.getContent();
		if (list != null && list.size() > 0) {
			Category category = list.get(0);
			Integer id = category.getId();
			Query query2 = new SimpleQuery("*:*");
			Criteria criteria2 = new Criteria("category_id").is(id);
			query2.addCriteria(criteria2);
			query2.setOffset(offset);
			query2.setRows(pageSize);
			Page<ImageCategoryRelation> page2 = solrTemplate.query("PhotoServerRelation", query2,
					ImageCategoryRelation.class);
			List<ImageCategoryRelation> list2 = page2.getContent();
			if (list2 != null && list2.size() > 0) {
				List<String> ids = new LinkedList<String>();
				for (ImageCategoryRelation relation : list2) {
					String groupId = relation.getImageGroupId();
					ids.add(groupId);
				}
				return (List<ImageGroupInfo>) solrTemplate.getByIds(PhotoServerImageGroup, ids, ImageGroupInfo.class);
			}
		}
		return null;
	}

}
