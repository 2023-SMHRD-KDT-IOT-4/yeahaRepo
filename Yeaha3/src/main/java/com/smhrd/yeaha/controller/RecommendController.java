package com.smhrd.yeaha.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RecommendController {

	@Value("${flask.server.url}")
	private String flaskServerUrl;

	@RequestMapping(value = "/algo", method = RequestMethod.GET)
	public ResponseEntity<String> fetchDataFromFlaskServer() {
		RestTemplate restTemplate = new RestTemplate();
		String url = flaskServerUrl + "/recommend?val1=1&val2=0&val3=1&val4=0"; // Flask 서버의 URL
		 
		// GET 요청을 보내고 JSON 응답을 받음
		ResponseEntity<String> recommendEntity = restTemplate.getForEntity(url, String.class);
		System.out.println(recommendEntity);
		return recommendEntity;
	}
	@RequestMapping(value = "/highbp", method = RequestMethod.GET)
	public ResponseEntity<String> fetchDataFromFlaskServer1() {
		RestTemplate restTemplate = new RestTemplate();
		String url = flaskServerUrl + "/recommend?val1=1&val2=0&val3=0&val4=0"; // Flask 서버의 URL
		
		// GET 요청을 보내고 JSON 응답을 받음
		ResponseEntity<String> recommendEntity = restTemplate.getForEntity(url, String.class);
		System.out.println(recommendEntity);
		return recommendEntity;
	}
	@RequestMapping(value = "/diabetes", method = RequestMethod.GET)
	public ResponseEntity<String> fetchDataFromFlaskServer2() {
		RestTemplate restTemplate = new RestTemplate();
		String url = flaskServerUrl + "/recommend?val1=0&val2=1&val3=0&val4=0"; // Flask 서버의 URL
		
		// GET 요청을 보내고 JSON 응답을 받음
		ResponseEntity<String> recommendEntity = restTemplate.getForEntity(url, String.class);
		System.out.println(recommendEntity);
		return recommendEntity;
	}
	@RequestMapping(value = "/stroke", method = RequestMethod.GET)
	public ResponseEntity<String> fetchDataFromFlaskServer3() {
		RestTemplate restTemplate = new RestTemplate();
		String url = flaskServerUrl + "/recommend?val1=0&val2=0&val3=1&val4=0"; // Flask 서버의 URL
		
		// GET 요청을 보내고 JSON 응답을 받음
		ResponseEntity<String> recommendEntity = restTemplate.getForEntity(url, String.class);
		System.out.println(recommendEntity);
		return recommendEntity;
	}
	@RequestMapping(value = "/hd", method = RequestMethod.GET)
	public ResponseEntity<String> hd() {
		RestTemplate restTemplate = new RestTemplate();
		String url = flaskServerUrl + "/recommend?val1=0&val2=0&val3=0&val4=1"; // Flask 서버의 URL
		
		// GET 요청을 보내고 JSON 응답을 받음
		ResponseEntity<String> recommendEntity = restTemplate.getForEntity(url, String.class);
		System.out.println(recommendEntity);
		return recommendEntity;
	}
}