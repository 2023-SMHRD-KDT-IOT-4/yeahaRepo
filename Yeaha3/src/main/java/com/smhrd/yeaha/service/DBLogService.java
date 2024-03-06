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
	public void processLogin(String email, String name, int genderVal, int ageCal, String phone) {
		YeahaUser existingUser = userMapper.selectUserByEmail(email);
		System.out.println(email);
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
			newUser.setEmail(email);
			newUser.setName(name);
			newUser.setGenderVal(genderVal);
			newUser.setAgeCal(ageCal);
			newUser.setPhone(phone);

			userMapper.insertUser(newUser);
			System.out.println("User with email " + email + " inserted successfully.");
		} else {
			// 이미 존재하는 경우, 업데이트 수행
			existingUser.setEmail(email);
			existingUser.setName(name);
			existingUser.setGenderVal(genderVal);
			existingUser.setAgeCal(ageCal);
			existingUser.setPhone(phone);

			userMapper.updateUser(existingUser);
			System.out.println("User with email " + email + " updated successfully.");
		}
	}
}