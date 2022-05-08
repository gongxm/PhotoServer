package com.gongxm.photo.action;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gongxm.photo.domain.ResponseData;
import com.gongxm.photo.utils.ThreadPoolUtils;

/** 
* @author 作者 : gongxm
* @version 创建时间：2020年3月8日 下午5:44:07 
* @description 描述 :
* 		
*/
@RestController
@RequestMapping("/action/")
public class ThreadTaskInfoAction {
	
	@RequestMapping("get_task_info.action")
	public ResponseData getTaskInfo() {
		ResponseData result = new ResponseData();
		result.getData().put("count", ThreadPoolUtils.getTaskCount());
		result.getData().put("size", ThreadPoolUtils.getTaskQueueSize());
		result.setSuccess();
		return result;
	}

}
