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

	@RequestMapping(value = "/test_home1", method = RequestMethod.GET)
	public String login() {
		return "test_home1";
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
		String name = responseJsonObject.getString("name");
		String nickname = responseJsonObject.getString("nickname");
		String age = responseJsonObject.getString("age");
		String gender = responseJsonObject.getString("gender");
		String birthyear = responseJsonObject.getString("birthyear");
		String birthday = responseJsonObject.getString("birthday");
		String email = responseJsonObject.getString("email");
		String phone = responseJsonObject.getString("mobile");
		int ageCal = calculateAge(birthyear, birthday);

		// 성별 매핑
		int genderVal = mapGenderToValue(gender);
		// 유저 정보를 세션에 저장
		session.setAttribute("name", name);
		session.setAttribute("age", calculateAge(birthyear, birthday));
		session.setAttribute("email", email);
		session.setAttribute("gender", genderVal);
		session.setAttribute("nickname", nickname);
		session.setAttribute("accessToken", accessToken);

		// 로그인 시(access token 발급할 때) DB에 유저 정보 삽입 또는 업데이트
		System.out.println(genderVal);
		System.out.println();
		dbLogService.processLogin(email, name, genderVal, ageCal, phone);

		// 콘솔 창 로깅
		System.out.println(userInfo);
		System.out.println(name);
		System.out.println(nickname);
		System.out.println("연령대: " + age);
		System.out.println("만 나이: " + calculateAge(birthyear, birthday));
		System.out.println(gender);
		System.out.println(birthyear);
		System.out.println(birthday);
		System.out.println(email);
		System.out.println(phone);
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

	private int calculateAge(String birthYear, String birthday) {

		// 만 나이생성기
		// 현재 날짜 가져오기
		LocalDate currentDate = LocalDate.now();

		// 생일 정보로 LocalDate 객체 생성
		LocalDate birthDate = LocalDate.of(Integer.parseInt(birthYear), Integer.parseInt(birthday.split("-")[0]),
				Integer.parseInt(birthday.split("-")[1]));

		// 나이 계산
		Period age = Period.between(birthDate, currentDate);

		// 만 나이 반환
		return age.getYears();
	}

	private int mapGenderToValue(String gender) {
		return "M".equals(gender) ? 1 : 0;
		// 성별 DB 저장하기위해 int 변환
	}

}
