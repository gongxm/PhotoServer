package com.gongxm.photo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gongxm.photo.mapper.ImageInfoMapper;
import com.gongxm.photo.pojo.ImageInfo;
import com.gongxm.photo.pojo.ImageInfoExample;
import com.gongxm.photo.service.ImageService;

/** 
* @author 作者 : gongxm
* @version 创建时间：2019年12月30日 上午11:40:47 
* @description 描述 :
* 		
*/
@Service
public class ImageServiceImpl implements ImageService{
	@Autowired
	ImageInfoMapper imageInfoMapper;

	@Cacheable(value = "ImageInfo",key = "'findImageInfoByGroupId_'+#imageGroupId+'_'+#page+'_'+#pageSize")
	@Override
	public List<ImageInfo> findImageInfoByGroupId(String imageGroupId, Integer page, Integer pageSize) {
		int offset = (page-1) * pageSize;
		return imageInfoMapper.findImageInfoByGroupId(imageGroupId,offset,pageSize);
	}

	@Cacheable(value = "ImageInfo",key = "'getCountByGroupId_'+#imageGroupId")
	@Override
	public int getCountByGroupId(String imageGroupId) {
		ImageInfoExample example = new ImageInfoExample();
		example.createCriteria().andImageGroupIdEqualTo(imageGroupId);
		return imageInfoMapper.countByExample(example );
	}

}
