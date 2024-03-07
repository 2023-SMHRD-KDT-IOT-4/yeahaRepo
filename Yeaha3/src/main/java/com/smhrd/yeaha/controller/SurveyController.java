package com.smhrd.yeaha.controller;


import com.smhrd.yeaha.model.SurveyResponse;
import com.smhrd.yeaha.service.DBLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/surveyres")
public class SurveyController {

	private final DBLogService dbLogService;

	@Autowired
	public SurveyController(DBLogService dbLogService) {
		this.dbLogService = dbLogService;
	}

	@PostMapping  // POST 요청에 매핑
    public String postSurveyResponses(@RequestBody SurveyResponse surveyResponse) {
        dbLogService.processSurveyResponses(surveyResponse);

        System.out.println("Received survey results:");
        System.out.println(surveyResponse);

        return "proceedresult";
    }
}