package com.gongxm.photo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.gongxm.photo.MyConstants;
import com.gongxm.photo.mapper.CategoryMapper;
import com.gongxm.photo.mapper.ImageCategoryRelationMapper;
import com.gongxm.photo.mapper.ImageInfoMapper;
import com.gongxm.photo.mapper.ImagePageMapper;
import com.gongxm.photo.mapper.ListUrlMapper;
import com.gongxm.photo.pojo.Category;
import com.gongxm.photo.pojo.CategoryExample;
import com.gongxm.photo.pojo.ImageCategoryRelation;
import com.gongxm.photo.pojo.ImageCategoryRelationExample;
import com.gongxm.photo.pojo.ImageInfo;
import com.gongxm.photo.pojo.ImageInfoExample;
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
	ImageInfoMapper imageInfoMapper;
	@Autowired
	CategoryMapper categoryMapper;
	@Autowired
	ImageCategoryRelationMapper relationMapper;
	@Autowired
	ImagePageMapper imagePageMapper;

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


	@Override
	public ImageInfo findImageInfoByUrl(String imgUrl) {
		ImageInfoExample example = new ImageInfoExample();
		example.createCriteria().andUrlEqualTo(imgUrl);
		List<ImageInfo> list = imageInfoMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void addImageInfo(ImageInfo imageInfo) {
		imageInfoMapper.insert(imageInfo);
	}

	@Override
	public int getImageInfoUnCollectCount() {
		ImageInfoExample example = new ImageInfoExample();
		example.createCriteria().andStatusEqualTo(MyConstants.COLLECT_STATUS_UNCOLLECT);
		return imageInfoMapper.countByExample(example);
	}

	@Override
	public List<ImageInfo> findUnCollectImageInfo(int page, int pageSize) {
		int offset = (page - 1) * pageSize;
		return imageInfoMapper.findUnCollectImageInfo(offset,pageSize);
	}

	@Cacheable(value = "tags",key = "#text")
	@Override
	public Category findCategoryByTag(String text) {
		CategoryExample example=new CategoryExample();
		example.createCriteria().andTagEqualTo(text);
		List<Category> list = categoryMapper.selectByExample(example);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		return 	null;
	}

	
	@Caching(evict = {@CacheEvict(value = "tags",key = "'tag_'+#groupInfoId+'_'+#categoryId"),@CacheEvict(value = "tags",key = "'tag_'+#groupInfoId")})
	@Override
	public void addImageCategoryRelation(String groupInfoId, Integer categoryId) {
		ImageCategoryRelation relation=new ImageCategoryRelation(groupInfoId,categoryId);
		relationMapper.insert(relation);
	}

	@CacheEvict(value = "tags",key = "#category")
	@Override
	public void addImageCategoryRelation(String groupInfoId, String category,String type) {
		Category tag=new Category(category,type);
		categoryMapper.insert(tag);
		Integer categoryId = tag.getId();
		addImageCategoryRelation(groupInfoId, categoryId);
	}

	@Cacheable(value = "tags",key = "'tag_'+#groupInfoId+'_'+#categoryId")
	@Override
	public ImageCategoryRelation findImageCategoryRelation(String groupInfoId, Integer categoryId) {
		ImageCategoryRelationExample example=new ImageCategoryRelationExample();
		example.createCriteria().andImageGroupIdEqualTo(groupInfoId).andCategoryIdEqualTo(categoryId);
		List<ImageCategoryRelation> list = relationMapper.selectByExample(example);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		return 	null;
	}

	@Override
	public void updateImageInfo(ImageInfo imageInfo) {
		imageInfoMapper.updateByPrimaryKey(imageInfo);		
	}

	@Override
	public void addImagePage(ImagePage page) {
		imagePageMapper.insert(page);
	}

	@Override
	public int getImagePageUnCollectCount() {
		ImagePageExample example=new ImagePageExample();
		example.createCriteria().andStatusEqualTo(MyConstants.COLLECT_STATUS_UNCOLLECT);
		return imagePageMapper.countByExample(example);
	}

	@Override
	public List<ImagePage> findUnCollectImagePage(int page, int pageSize) {
		int offset = (page - 1) * pageSize;
		return imagePageMapper.findUnCollectImagePage(offset,pageSize);
	}


	@Override
	public void updateImagePage(ImagePage imagePage) {
		imagePageMapper.updateByPrimaryKeySelective(imagePage);
	}

}
