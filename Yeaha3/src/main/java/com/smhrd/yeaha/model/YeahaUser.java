package com.smhrd.yeaha.model;

import lombok.Data;

@Data
public class YeahaUser {

	// 기본 생성자 추가
	public YeahaUser() {
	}

	private String user_email;
	private String user_name;
	private int user_genderVal;
	private int user_ageCal;
	private String user_phone;
	private String connected_at;
	
	
	

}

