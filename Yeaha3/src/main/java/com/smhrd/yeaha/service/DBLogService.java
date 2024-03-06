package com.smhrd.yeaha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smhrd.yeaha.mapper.YeahaUserMapper;
import com.smhrd.yeaha.model.YeahaUser;

@Service
public class DBLogService {

	private final YeahaUserMapper userMapper;

	@Autowired
	public DBLogService(YeahaUserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Transactional
	public void processLogin(String user_email, String user_name, int user_genderVal, int user_ageCal, String user_phone) {
		YeahaUser existingUser = userMapper.selectUserByEmail(user_email);
		System.out.println(user_genderVal);
		System.out.println(user_genderVal);
		System.out.println(user_genderVal);
		System.out.println(user_genderVal);
		System.out.println(user_genderVal);
System.out.println(existingUser);
System.out.println(existingUser);
System.out.println(existingUser);
System.out.println(existingUser);
System.out.println(existingUser);
System.out.println(existingUser);
System.out.println(existingUser);
		if (existingUser == null) {
			// 존재하지 않는 경우, 삽입 수행
			YeahaUser newUser = new YeahaUser();
			newUser.setUser_email(user_email);
			newUser.setUser_name(user_name);
			newUser.setUser_genderVal(user_genderVal);
			newUser.setUser_ageCal(user_ageCal);
			newUser.setUser_phone(user_phone);

			userMapper.insertUser(newUser);
			System.out.println("User with email " + user_email + " inserted successfully.");
		} else {
			// 이미 존재하는 경우, 업데이트 수행
			existingUser.setUser_email(user_email);
			existingUser.setUser_name(user_name);
			existingUser.setUser_genderVal(user_genderVal);
			existingUser.setUser_ageCal(user_ageCal);
			existingUser.setUser_phone(user_phone);

			userMapper.updateUser(existingUser);
			System.out.println("User with email " + user_email + " updated successfully.");
		}
	}
}