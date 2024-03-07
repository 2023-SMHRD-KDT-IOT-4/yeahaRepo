package com.smhrd.yeaha.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smhrd.yeaha.service.DBLogService;
import com.smhrd.yeaha.service.GetUserInfoService;
import com.smhrd.yeaha.service.RestJsonService;
import org.springframework.web.bind.annotation.RequestParam;
import org.json.JSONObject;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;


@EnableSpringDataWebSupport
@Controller
public class MainController {

	private final GetUserInfoService getUserInfoService;
	private final RestJsonService restJsonService;
	private final DBLogService dbLogService;

	@Autowired
	public MainController(GetUserInfoService getUserInfoService, RestJsonService restJsonService,
			DBLogService dbLogService) {
		this.getUserInfoService = getUserInfoService;
		this.restJsonService = restJsonService;
		this.dbLogService = dbLogService;
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "index";
	}

	@RequestMapping(value = "/survey", method = RequestMethod.GET)
	public String initSurvey() {
		return "survey";
	}

	@RequestMapping(value = "/naver_success", method = RequestMethod.GET)
	public String showNaverLogin() {
		return "naver_success";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home1(HttpSession session, Model model) {

		// 인코딩된 state 값 생성 아래 메소드 참조(위조방지)
		String state = generateState();
		String encodedState = "";
		try {
			encodedState = URLEncoder.encode(state, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		session.setAttribute("state", encodedState);
		session.setMaxInactiveInterval(60 * 60);
		String stateValue = (String) session.getAttribute("state");
		System.out.println("State 값: " + stateValue);
		model.addAttribute("state", encodedState);
		return "index";
	}

	@RequestMapping(value = "/receiveAC", method = RequestMethod.GET)
	public String receiveAC(@RequestParam("code") String code, Model model, HttpSession session) {
		RestJsonService restJsonService = new RestJsonService();

		// access_token이 포함된 JSON String 받기
		String accessTokenJsonData = restJsonService.getAccessTokenJsonData(code);
		if ("error".equals(accessTokenJsonData))
			return "error";

		// JSON String -> JSON Object
		JSONObject accessTokenJsonObject = new JSONObject(accessTokenJsonData);

		// access_token 추출
		String accessToken = accessTokenJsonObject.getString("access_token");

		// 유저 정보가 포함된 JSON String 받기
		GetUserInfoService getUserInfoService = new GetUserInfoService();
		String userInfo = getUserInfoService.getUserInfo(accessToken);

		// JSON String -> JSON Object
		JSONObject userInfoJsonObject = new JSONObject(userInfo);

		// 유저의 정보 추출
		JSONObject responseJsonObject = userInfoJsonObject.getJSONObject("response");
		String user_name = responseJsonObject.getString("name");
		String user_nickname = responseJsonObject.getString("nickname");
		String user_age = responseJsonObject.getString("age");
		String user_gender = responseJsonObject.getString("gender");
		String user_birthyear = responseJsonObject.getString("birthyear");
		String user_birthday = responseJsonObject.getString("birthday");
		String user_email = responseJsonObject.getString("email");
		String user_phone = responseJsonObject.getString("mobile");
		int user_ageCal = calculateAge(user_birthyear, user_birthday);

		// 성별 매핑
		int user_genderVal = mapGenderToValue(user_gender);
		// 유저 정보를 세션에 저장
		session.setAttribute("name", user_name);
		session.setAttribute("age", calculateAge(user_birthyear, user_birthday));
		session.setAttribute("email", user_email);
		session.setAttribute("gender", user_genderVal);
		session.setAttribute("nickname", user_nickname);
		session.setAttribute("accessToken", accessToken);

		// 로그인 시(access token 발급할 때) DB에 유저 정보 삽입 또는 업데이트
		System.out.println(user_genderVal);
		System.out.println();
		dbLogService.processLogin(user_email, user_name, user_genderVal, user_ageCal, user_phone);

		// 콘솔 창 로깅
		System.out.println(userInfo);
		System.out.println(user_name);
		System.out.println(user_nickname);
		System.out.println("연령대: " + user_age);
		System.out.println("만 나이: " + calculateAge(user_birthyear, user_birthday));
		System.out.println(user_gender);
		System.out.println(user_birthyear);
		System.out.println(user_birthday);
		System.out.println(user_email);
		System.out.println(user_phone);
		return "receiveAC";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		// Access Token 가져오기
		String accessToken = (String) session.getAttribute("accessToken");

		// Access Token이 있다면 API에 로그아웃 요청
		if (accessToken != null) {
			logoutApiRequest(accessToken);
		}

		// 세션 초기화
		session.invalidate();

		// 홈 페이지로 리다이렉트
		return "redirect:/home";
	}

	private void logoutApiRequest(String accessToken) {
		// API 로그아웃 요청 코드
		// 네이버 API에 로그아웃 요청을 RestTemplate 등을 사용하여 보냄
		String apiUrl = "https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=puXPnu8QxCDBHhnD9gRj&client_secret=ed7dNaKXIo&access_token="
				+ accessToken + "&service_provider=NAVER";

		// HTTP 요청설정
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		HttpEntity<String> entity = new HttpEntity<>(headers);

		// HTTP 요청
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

		// 응답 확인 등 콘솔창 로깅 코드
		if (response.getStatusCode() == HttpStatus.OK) {
			System.out.println("Logout request successful");
		} else {
			System.err.println("Logout request failed. Status code: " + response.getStatusCode());
		}
	}

	public String generateState() {
		// CSRF 방지를 위한 상태 토큰 생성 코드
		// 상태 토큰은 추후 검증을 위해 세션에 저장되어야 한다.
		// The CSRF sample from Naver Login API example page.
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

	private int calculateAge(String user_birthYear, String user_birthday) {

		// 만 나이생성기
		// 현재 날짜 가져오기
		LocalDate currentDate = LocalDate.now();

		// 생일 정보로 LocalDate 객체 생성
		LocalDate birthDate = LocalDate.of(Integer.parseInt(user_birthYear), Integer.parseInt(user_birthday.split("-")[0]),
				Integer.parseInt(user_birthday.split("-")[1]));

		// 나이 계산
		Period user_age = Period.between(birthDate, currentDate);

		// 만 나이 반환
		return user_age.getYears();
	}

	private int mapGenderToValue(String user_gender) {
		return "M".equals(user_gender) ? 1 : 0;
		// 성별 DB 저장하기위해 int 변환
	}

}
