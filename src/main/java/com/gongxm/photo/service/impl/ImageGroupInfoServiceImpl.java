package com.gongxm.photo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gongxm.photo.MyConstants;
import com.gongxm.photo.mapper.ImageGroupInfoMapper;
import com.gongxm.photo.pojo.ImageGroupInfo;
import com.gongxm.photo.pojo.ImageGroupInfoExample;
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

	@Override
	public List<ImageGroupInfo> findLastRecommend() {
		return imageGroupInfoMapper.findLastRecommend();
	}

	@Override
	public List<ImageGroupInfo> findPageList(Integer page, Integer pageSize) {
		int offset = (page - 1) * pageSize;
		return imageGroupInfoMapper.findPageList(offset,pageSize);
	}

	@Cacheable(value = "ImageGroup", key = "'getTotalCount'")
	@Override
	public int getTotalCount() {
		return imageGroupInfoMapper.countByExample(null);
	}

	@Override
	public ImageGroupInfo findImageGroupInfoById(String imageGroupId) {
		return imageGroupInfoMapper.selectByPrimaryKey(imageGroupId);
	}

	@CacheEvict(value = "ImageGroup", allEntries = true)
	@Override
	public void addImageGroupInfo(ImageGroupInfo groupInfo) {
		imageGroupInfoMapper.insert(groupInfo);
	}

	
	@Cacheable(value = "ImageGroup", key = "'url_'+#absUrl")
	@Override
	public ImageGroupInfo findImageGroupInfoByUrl(String absUrl) {
		ImageGroupInfoExample example = new ImageGroupInfoExample();
		example.createCriteria().andUrlEqualTo(absUrl);
		List<ImageGroupInfo> list = imageGroupInfoMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int getImageGroupUnCollectCount() {
		ImageGroupInfoExample example = new ImageGroupInfoExample();
		example.createCriteria().andStatusEqualTo(MyConstants.COLLECT_STATUS_UNCOLLECT);
		return imageGroupInfoMapper.countByExample(example);
	}

	@Override
	public List<ImageGroupInfo> findUnCollectImageGroup(int page, int pageSize) {
		int offset = (page - 1) * pageSize;
		return imageGroupInfoMapper.findUnCollectImageGroup(offset, pageSize);
	}

	@Override
	public void updateImageGroupInfo(ImageGroupInfo groupInfo) {
		imageGroupInfoMapper.updateByPrimaryKey(groupInfo);
	}

	
	@Cacheable(value = "ImageGroup", key = "'search_'+#keyword+'_'+#page+'_'+#pageSize")
	@Override
	public List<ImageGroupInfo> findSearchList(String keyword, Integer page, Integer pageSize) {
		int offset = (page - 1) * pageSize;
		return imageGroupInfoMapper.findSearchList(keyword, offset, pageSize);
	}
	
	@Cacheable(value = "ImageGroup", key = "'count_'+#keyword")
	@Override
	public int getCountByKeyword(String keyword) {
		ImageGroupInfoExample example = new ImageGroupInfoExample();
		example.createCriteria().andTitleLike("%"+keyword+"%");
		return imageGroupInfoMapper.countByExample(example);
	}
	
	@Override
	public List<ImageGroupInfo> findImageGroupByTag(String tag, Integer page, Integer pageSize) {
		int offset = (page - 1) * pageSize;
		
		return imageGroupInfoMapper.findImageGroupByTag(tag,offset,pageSize);
	}

}
