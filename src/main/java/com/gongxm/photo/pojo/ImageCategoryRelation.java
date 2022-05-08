package com.gongxm.photo.pojo;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class ImageCategoryRelation implements Serializable {
	private static final long serialVersionUID = 1L;
	@Field
	private Integer id;
	@Field("image_group_id")
	private String imageGroupId;
	@Field("category_id")
	private Integer categoryId;
	
	public ImageCategoryRelation() {
	}

	public ImageCategoryRelation(String imageGroupId, Integer categoryId) {
		this.imageGroupId = imageGroupId;
		this.categoryId = categoryId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImageGroupId() {
		return imageGroupId;
	}

	public void setImageGroupId(String imageGroupId) {
		this.imageGroupId = imageGroupId == null ? null : imageGroupId.trim();
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
}