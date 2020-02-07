package com.gongxm.photo.action;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PropertySource("classpath:config.properties")
public class ViewController {
	@Value("${admin}")
	boolean show;
	
	@RequestMapping(value = { "/admin", "/admin.html" })
	public String admin() {
		if(show) {
			return "admin";
		}else {
			return "redirect:/index.html";
		}
	}

}
