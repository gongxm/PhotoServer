package com.gongxm.photo.domain;

import java.util.HashMap;
import java.util.Map;

import com.gongxm.photo.MyConstants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/** 
* @author 作者 : gongxm
* @version 创建时间：2020年3月8日 下午5:45:55 
* @description 描述 :
* 		
*/
@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Accessors //链式访问
public class ResponseData {
	private int errcode= MyConstants.FAILURE;
	private String errmsg = "请求失败!";
	private Map<String, Object> data = new HashMap<>();
	
	public void setSuccess() {
		this.errcode = MyConstants.SUCCESS;
		this.errmsg = "请求成功!";
	}
}
