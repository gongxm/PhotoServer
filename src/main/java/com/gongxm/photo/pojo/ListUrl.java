package com.gongxm.photo.pojo;

import java.io.Serializable;

import com.gongxm.photo.MyConstants;

public class ListUrl implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer id;

    private String url;

    private Integer status = MyConstants.COLLECT_STATUS_UNCOLLECT;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}