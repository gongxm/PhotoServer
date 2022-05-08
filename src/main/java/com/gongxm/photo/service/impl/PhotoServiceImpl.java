package com.gongxm.photo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Service;

import com.gongxm.photo.MyConstants;
import com.gongxm.photo.mapper.CategoryMapper;
import com.gongxm.photo.mapper.ImageCategoryRelationMapper;
import com.gongxm.photo.mapper.ImagePageMapper;
import com.gongxm.photo.mapper.ListUrlMapper;
import com.gongxm.photo.pojo.Category;
import com.gongxm.photo.pojo.ImageCategoryRelation;
import com.gongxm.photo.pojo.ImageCategoryRelationExample;
import com.gongxm.photo.pojo.ImagePage;
import com.gongxm.photo.pojo.ImagePageExample;
import com.gongxm.photo.pojo.ListUrl;
import com.gongxm.photo.pojo.ListUrlExample;
import com.gongxm.photo.service.PhotoService;

@Service
public class PhotoServiceImpl implements PhotoService {
	@Autowired
	ListUrlMapper listUrlMapper;

	@Autowired
	CategoryMapper categoryMapper;
	@Autowired
	ImageCategoryRelationMapper relationMapper;
	@Autowired
	ImagePageMapper imagePageMapper;

	@Autowired
	SolrTemplate solrTemplate;
	final String PhotoServerRelation = "PhotoServerRelation";

	@Override
	public void addListUrl(String url) {
		ListUrl record = new ListUrl();
		record.setUrl(url);
		listUrlMapper.insert(record);
	}

	@Override
	public List<ListUrl> findUnCollectListUrl(int page, int pageSize) {
		int offset = (page - 1) * pageSize;
		return listUrlMapper.findUnCollectListUrl(offset, pageSize);
	}

	@Override
	public int getListUrlUnCollectCount() {
		ListUrlExample example = new ListUrlExample();
		example.createCriteria().andStatusEqualTo(MyConstants.COLLECT_STATUS_UNCOLLECT);
		return listUrlMapper.countByExample(example);
	}

	@Override
	public ListUrl getListUrlById(int id) {
		return listUrlMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateListUrl(ListUrl listUrl) {
		listUrlMapper.updateByPrimaryKey(listUrl);
	}

	/** -------------------------------------------------------- */

	@Caching(evict = { @CacheEvict(value = "ImageCategoryRelation", allEntries = true),
			@CacheEvict(value = "tags", allEntries = true) })
	@Override
	public void addImageCategoryRelation(String groupInfoId, Integer categoryId) {
		ImageCategoryRelation relation = new ImageCategoryRelation(groupInfoId, categoryId);
		relationMapper.insert(relation);
	}

	@Caching(evict = { @CacheEvict(value = "ImageCategoryRelation", allEntries = true),
			@CacheEvict(value = "tags", allEntries = true) })
	@Override
	public void addImageCategoryRelation(String groupInfoId, String category, String type) {
		Category tag = new Category(category, type);
		categoryMapper.insert(tag);
		Integer categoryId = tag.getId();
		addImageCategoryRelation(groupInfoId, categoryId);
	}

	@Cacheable(value = "ImageCategoryRelation", key = "'relation_'+#groupInfoId+'_'+#categoryId")
	@Override
	public ImageCategoryRelation findImageCategoryRelation(String groupInfoId, Integer categoryId) {
		ImageCategoryRelationExample example = new ImageCategoryRelationExample();
		example.createCriteria().andImageGroupIdEqualTo(groupInfoId).andCategoryIdEqualTo(categoryId);
		List<ImageCategoryRelation> list = relationMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}


	@Override
	public void addImagePage(ImagePage page) {
		imagePageMapper.insert(page);
	}

	@Override
	public int getImagePageUnCollectCount() {
		ImagePageExample example = new ImagePageExample();
		example.createCriteria().andStatusEqualTo(MyConstants.COLLECT_STATUS_UNCOLLECT);
		return imagePageMapper.countByExample(example);
	}

	@Override
	public List<ImagePage> findUnCollectImagePage(int page, int pageSize) {
		int offset = (page - 1) * pageSize;
		return imagePageMapper.findUnCollectImagePage(offset, pageSize);
	}

	@Override
	public void updateImagePage(ImagePage imagePage) {
		imagePageMapper.updateByPrimaryKeySelective(imagePage);
	}

	@Override
	public ImagePage findImagePageById(String id) {
		return imagePageMapper.selectByPrimaryKey(id);
	}

}
