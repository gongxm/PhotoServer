package com.gongxm.photo.service;

import java.util.List;

import com.gongxm.photo.pojo.ImageCategoryRelation;
import com.gongxm.photo.pojo.ImagePage;
import com.gongxm.photo.pojo.ListUrl;

public interface PhotoService {

	void addListUrl(String string);

	List<ListUrl> findUnCollectListUrl(int page, int pageSize);

	int getListUrlUnCollectCount();

	ListUrl getListUrlById(int id);

	void updateListUrl(ListUrl listUrl);

	void addImageCategoryRelation(String groupInfoId, Integer categoryId);

	void addImageCategoryRelation(String groupInfoId, String category, String type);

	ImageCategoryRelation findImageCategoryRelation(String groupInfoId, Integer categoryId);

	void addImagePage(ImagePage page);

	int getImagePageUnCollectCount();

	List<ImagePage> findUnCollectImagePage(int page, int pageSize);

	void updateImagePage(ImagePage imagePage);

	ImagePage findImagePageById(String id);

}
