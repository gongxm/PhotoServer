package com.gongxm.photo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableCaching
@ComponentScan(basePackages = {"com.gongxm.photo"})
@MapperScan(basePackages = "com.gongxm.photo.mapper")
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);

	}

}
