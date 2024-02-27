package com.smhrd.yeaha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

//이 어노테이션이 있는 클래스의 위치로부터 하위에 있는 클래스들만 자동으로 객체화 시켜줌 


@SpringBootApplication
public class YeahaApplication {

	public static void main(String[] args) {
		SpringApplication.run(YeahaApplication.class, args);
	}

}
