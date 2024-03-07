package com.smhrd.yeaha.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.smhrd.yeaha.model.SurveyResponse;

@Mapper
public interface SurveyResponseMapper {

	@Insert("INSERT INTO tbl_user (user_email, gender, age, height, weight, bloodPressure, chol, glucose, smokingStatus, bmi, connected_at) "
			+ "VALUES (#{user_email}, #{gender}, #{age}, #{height}, #{weight}, #{bloodPressure}, #{chol}, #{glucose}, #{smokingStatus}, #{bmi}, NOW())")
	void insertSurveyResponse(SurveyResponse surveyResponse);
}