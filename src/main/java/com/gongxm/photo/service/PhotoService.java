package com.gongxm.photo.service;

import java.util.List;

import com.gongxm.photo.pojo.Category;
import com.gongxm.photo.pojo.ImageCategoryRelation;
import com.gongxm.photo.pojo.ImageInfo;
import com.gongxm.photo.pojo.ImagePage;
import com.gongxm.photo.pojo.ListUrl;

public interface PhotoService {

	void addListUrl(String string);

	List<ListUrl> findUnCollectListUrl(int page, int pageSize);

	int getListUrlUnCollectCount();

	ListUrl getListUrlById(int id);


	void updateListUrl(ListUrl listUrl);

	ImageInfo findImageInfoByUrl(String imgUrl);

	void addImageInfo(ImageInfo imageInfo);

	int getImageInfoUnCollectCount();

	List<ImageInfo> findUnCollectImageInfo(int page, int pageSize);

	Category findCategoryByTag(String text);

	void addImageCategoryRelation(String groupInfoId, Integer categoryId);

	void addImageCategoryRelation(String groupInfoId, String category,String type);

	ImageCategoryRelation findImageCategoryRelation(String groupInfoId, Integer categoryId);

	void updateImageInfo(ImageInfo imageInfo);

	void addImagePage(ImagePage page);

	int getImagePageUnCollectCount();

	List<ImagePage> findUnCollectImagePage(int page, int pageSize);

	void updateImagePage(ImagePage imagePage);
	
}
