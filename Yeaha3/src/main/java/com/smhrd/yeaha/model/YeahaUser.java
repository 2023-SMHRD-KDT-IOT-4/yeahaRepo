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
	
	//public YeahaUser(String email, String name, int genderVal, int ageCal, String phone) {
	//}

	
	
	//private String name;
	//private int ageCal;
	//private int age;
	//private String email;
	//private int gender;
	//private int genderVal;
	//private String nickname;
	//private String phone;
	

}

