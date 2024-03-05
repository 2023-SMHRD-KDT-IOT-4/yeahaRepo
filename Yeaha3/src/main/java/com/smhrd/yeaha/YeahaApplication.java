package com.smhrd.yeaha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;




@EnableSpringDataWebSupport
@SpringBootApplication
@MapperScan("com.smhrd.yeaha.mapper")
public class YeahaApplication {

	public static void main(String[] args) {
		SpringApplication.run(YeahaApplication.class, args);
	}

}
