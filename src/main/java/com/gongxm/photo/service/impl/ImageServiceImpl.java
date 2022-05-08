package com.gongxm.photo.service.impl;

import java.util.List;

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
import com.gongxm.photo.mapper.ImageInfoMapper;
import com.gongxm.photo.pojo.ImageInfo;
import com.gongxm.photo.service.ImageService;

/**
 * @author 作者 : gongxm
 * @version 创建时间：2019年12月30日 上午11:40:47
 * @description 描述 :
 * 
 */
@Service
public class ImageServiceImpl implements ImageService {
	final String PhotoServerImageInfo = "PhotoServerImageInfo";

	@Autowired
	ImageInfoMapper imageInfoMapper;

	@Autowired
	SolrTemplate solrTemplate;

	@Cacheable(value = "ImageInfo", key = "'findImageInfoByGroupId_'+#imageGroupId+'_'+#page+'_'+#pageSize")
	@Override
	public List<ImageInfo> findImageInfoByGroupId(String imageGroupId, Integer page, Integer pageSize) {
		long offset = (page - 1) * pageSize;
		Query query = new SimpleQuery("*:*");
		query.setOffset(offset);
		query.setRows(pageSize);
		query.addCriteria(new Criteria("image_group_id").is(imageGroupId));
		query.addSort(Sort.by(Order.asc("image_position")));
		Page<ImageInfo> result = solrTemplate.query(PhotoServerImageInfo, query, ImageInfo.class);
		return result.getContent();
	}

	@Cacheable(value = "ImageInfo", key = "'getCountByGroupId_'+#imageGroupId")
	@Override
	public int getCountByGroupId(String imageGroupId) {
		Query query = new SimpleQuery("*:*");
		query.setOffset(0L);
		query.setRows(0);
		query.addCriteria(new Criteria("image_group_id").is(imageGroupId));
		return (int) solrTemplate.count(PhotoServerImageInfo, query);
	}

	@CacheEvict(value = "ImageInfo", allEntries = true)
	@Override
	public void addImageInfo(ImageInfo imageInfo) {
		imageInfoMapper.insert(imageInfo);
		solrTemplate.saveBean(PhotoServerImageInfo, imageInfo);
		solrTemplate.commit(PhotoServerImageInfo);
	}

	@CacheEvict(value = "ImageInfo", allEntries = true)
	@Override
	public void updateImageInfo(ImageInfo imageInfo) {
		imageInfoMapper.updateByPrimaryKey(imageInfo);
		solrTemplate.saveBean(PhotoServerImageInfo, imageInfo);
		solrTemplate.commit(PhotoServerImageInfo);
	}

	@Override
	public int getImageInfoUnCollectCount() {
		Query query = new SimpleQuery("*:*");
		query.setOffset(0L);
		query.setRows(0);
		query.addCriteria(new Criteria("image_status").is(MyConstants.COLLECT_STATUS_UNCOLLECT));
		return (int) solrTemplate.count(PhotoServerImageInfo, query);
	}

	@Override
	public List<ImageInfo> findUnCollectImageInfo(int page, int pageSize) {
		long offset = (page - 1) * pageSize;
		Query query = new SimpleQuery("*:*");
		query.setOffset(offset);
		query.setRows(pageSize);
		query.addCriteria(new Criteria("image_status").is(MyConstants.COLLECT_STATUS_UNCOLLECT));
		Page<ImageInfo> result = solrTemplate.query(PhotoServerImageInfo, query, ImageInfo.class);
		return result.getContent();
	}

	@Override
	public ImageInfo findImageInfoById(String id) {
		return imageInfoMapper.selectByPrimaryKey(id);
	}

}
