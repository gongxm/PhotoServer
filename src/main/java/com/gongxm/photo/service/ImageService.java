package com.gongxm.photo.service;

import java.util.List;

import com.gongxm.photo.pojo.ImageInfo;

/** 
* @author 作者 : gongxm
* @version 创建时间：2019年12月30日 上午11:40:16 
* @description 描述 :
* 		
*/
public interface ImageService {

	List<ImageInfo> findImageInfoByGroupId(String imageGroupId, Integer page, Integer pageSize);

	int getCountByGroupId(String imageGroupId);

}
