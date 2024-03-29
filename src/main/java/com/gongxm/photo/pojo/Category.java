package com.gongxm.photo.pojo;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class Category implements Serializable{
	private static final long serialVersionUID = 1L;
	@Field
	private Integer id;
	@Field("category_tag")
    private String tag;
	@Field("category_type")
    private String type;

    public Category(String tag, String type) {
    	this.tag = tag;
    	this.type = type;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}