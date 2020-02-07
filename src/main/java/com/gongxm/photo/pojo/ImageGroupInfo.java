package com.gongxm.photo.pojo;

import java.io.Serializable;
import java.util.Date;

import com.gongxm.photo.MyConstants;
import com.gongxm.photo.utils.MD5Utils;

public class ImageGroupInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;

    private String cover;

    private String title;

    private String url;

    private Date updateTime = new Date();

    private Integer status = MyConstants.COLLECT_STATUS_UNCOLLECT;
    
    public ImageGroupInfo() {
	}

    public ImageGroupInfo(String cover, String title, String url) {
    	this.cover = cover;
    	this.title = title;
    	this.url = url;
    	this.id = MD5Utils.creatID(url);
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "ImageGroupInfo [id=" + id + ", cover=" + cover + ", title=" + title + ", url=" + url + ", updateTime="
				+ updateTime + ", status=" + status + "]";
	}
}