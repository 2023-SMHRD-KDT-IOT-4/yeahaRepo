package com.smhrd.yeaha.model;

import lombok.Data;

@Data
public class SurveyResponse {


    private String user_email;
    private int gender;
    private int age;
    private int height;
    private int weight;
    private int bloodPressure;
    private int chol;
    private int glucose;
    private String smokingStatus;
    private double bmi;

    // 기본 생성자
    public SurveyResponse() {
    }
}