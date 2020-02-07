package com.gongxm.photo.pojo;

import java.io.Serializable;

import com.gongxm.photo.MyConstants;
import com.gongxm.photo.utils.MD5Utils;

public class ImageInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;

    private String url;

    private Integer status = MyConstants.COLLECT_STATUS_UNCOLLECT;

    private Integer position;

    private String imageGroupId;
    
    public ImageInfo() {
	}

    public ImageInfo(String url, String imageGroupId, int position) {
    	this.url = url;
    	this.imageGroupId = imageGroupId;
    	this.position = position;
    	this.id = MD5Utils.creatID(url);
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getImageGroupId() {
        return imageGroupId;
    }

    public void setImageGroupId(String imageGroupId) {
        this.imageGroupId = imageGroupId == null ? null : imageGroupId.trim();
    }

	@Override
	public String toString() {
		return "ImageInfo [id=" + id + ", url=" + url + ", status=" + status + ", position=" + position
				+ ", imageGroupId=" + imageGroupId + "]";
	}
    
}