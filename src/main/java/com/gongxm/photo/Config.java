package com.gongxm.photo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 作者 : gongxm
 * @version 创建时间：2020年2月28日 下午4:22:37
 * @description 描述 :
 * 		用于方便spring注入配置文件的内容, 因为非spring管理的对象中无法注入配置文件内容
 */
@Component
public class Config {

	@Value("${image-dir-path}")
	private String imagePath;

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
