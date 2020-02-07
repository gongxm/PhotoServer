package com.gongxm.photo.service;

import java.util.List;

import com.gongxm.photo.pojo.ImageGroupInfo;

/**
 * @author 作者 : gongxm
 * @version 创建时间：2019年12月29日 下午11:30:59
 * @description 描述 :
 * 
 */
public interface ImageGroupInfoService {

	List<ImageGroupInfo> findLastRecommend();

	List<ImageGroupInfo> findPageList(Integer page, Integer pageSize);

	int getTotalCount();

	void addImageGroupInfo(ImageGroupInfo groupInfo);

	ImageGroupInfo findImageGroupInfoByUrl(String absUrl);

	int getImageGroupUnCollectCount();

	List<ImageGroupInfo> findUnCollectImageGroup(int page, int pageSize);
	
	void updateImageGroupInfo(ImageGroupInfo groupInfo);

	ImageGroupInfo findImageGroupInfoById(String imageGroupId);

	List<ImageGroupInfo> findSearchList(String keyword, Integer page, Integer pageSize);

	int getCountByKeyword(String keyword);

	List<ImageGroupInfo> findImageGroupByTag(String tag, Integer page, Integer pageSize);

}
