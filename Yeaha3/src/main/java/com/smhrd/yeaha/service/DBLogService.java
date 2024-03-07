package com.smhrd.yeaha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smhrd.yeaha.mapper.SurveyResponseMapper;
import com.smhrd.yeaha.mapper.YeahaUserMapper;
import com.smhrd.yeaha.model.SurveyResponse;
import com.smhrd.yeaha.model.YeahaUser;

@Service
public class DBLogService {

	private final YeahaUserMapper userMapper;
	private final SurveyResponseMapper surveyMapper;

	@Autowired
	public DBLogService(YeahaUserMapper userMapper, SurveyResponseMapper surveyMapper) {
		this.userMapper = userMapper;
		this.surveyMapper = surveyMapper;
	}

	@Transactional
	public void processLogin(String user_email, String user_name, int user_genderVal, int user_ageCal,
			String user_phone) {
		YeahaUser existingUser = userMapper.selectUserByEmail(user_email);

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

	@Transactional
	public void processSurveyResponses(SurveyResponse surveyResponse) {
		// 설문 결과를 무조건 삽입 수행
		SurveyResponse newSurveyResponse = new SurveyResponse();
		newSurveyResponse.setUser_email(surveyResponse.getUser_email());
		newSurveyResponse.setGender(surveyResponse.getGender());
		newSurveyResponse.setAge(surveyResponse.getAge());
		newSurveyResponse.setHeight(surveyResponse.getHeight());
		newSurveyResponse.setWeight(surveyResponse.getWeight());
		newSurveyResponse.setBloodPressure(surveyResponse.getBloodPressure());
		newSurveyResponse.setChol(surveyResponse.getChol());
		newSurveyResponse.setGlucose(surveyResponse.getGlucose());
		newSurveyResponse.setSmokingStatus(surveyResponse.getSmokingStatus());
		newSurveyResponse.setBmi(calculateBMI(surveyResponse.getHeight(), surveyResponse.getWeight()));

		surveyMapper.insertSurveyResponse(newSurveyResponse);
		System.out.println("Survey result for email " + surveyResponse.getUser_email() + " inserted successfully.");

		// 설문 결과를 처리
		processSurveyResponse(newSurveyResponse, surveyResponse);
	}

	private void processSurveyResponse(SurveyResponse SurveyResponse, SurveyResponse surveyResponse) {
		// 설문 결과를 사용자와 관련된 로직에 따라 처리
		// 여기에서는 간단하게 설문 결과를 출력하는 것으로 가정
		System.out.println("Processing survey response for email " + SurveyResponse.getUser_email() + ": " + surveyResponse);
	}

	private double calculateBMI(int height, int weight) {
		// BMI 계산 로직 추가
		return (double) weight / ((double) height / 100.0 * (double) height / 100.0);
	}
}