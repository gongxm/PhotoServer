package com.gongxm.photo.pojo;

import java.io.Serializable;

public class Category implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String tag;

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