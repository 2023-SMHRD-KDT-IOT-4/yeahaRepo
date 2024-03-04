package com.smhrd.yeaha.controller;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.http.HttpHeaders;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.smhrd.yeaha.service.GetUserInfoService;
import com.smhrd.yeaha.service.RestJsonService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.apache.http.HttpEntity;
import org.json.JSONObject;
import java.math.BigInteger;
import java.security.SecureRandom;

@Controller
public class MainController {

	private final GetUserInfoService getUserInfoService;
	private final RestJsonService restJsonService;

	@Autowired
	public MainController(GetUserInfoService getUserInfoService, RestJsonService restJsonService) {
		this.getUserInfoService = getUserInfoService;
		this.restJsonService = restJsonService;
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

	@RequestMapping(value = "/naver_login", method = RequestMethod.GET)
	public String home1(HttpSession session, Model model) {

		String state = generateState();
		String encodedState = "";
		try {
			encodedState = URLEncoder.encode(state, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		session.setAttribute("state", encodedState);
		session.setMaxInactiveInterval(60 * 60);

		model.addAttribute("state", encodedState);
		return "naver_login";
	}

    @RequestMapping(value = "/receiveAC", method = RequestMethod.GET)
    public String receiveAC(@RequestParam("code") String code, Model model, HttpSession session) {
        RestJsonService restJsonService = new RestJsonService();

        // access_token이 포함된 JSON String을 받아옵니다.
        String accessTokenJsonData = restJsonService.getAccessTokenJsonData(code);
        if ("error".equals(accessTokenJsonData))
            return "error";

        // JSON String -> JSON Object
        JSONObject accessTokenJsonObject = new JSONObject(accessTokenJsonData);

        // access_token 추출
        String accessToken = accessTokenJsonObject.getString("access_token");

        // 유저 정보가 포함된 JSON String을 받아옵니다.
        GetUserInfoService getUserInfoService = new GetUserInfoService();
        String userInfo = getUserInfoService.getUserInfo(accessToken);

        // JSON String -> JSON Object
        JSONObject userInfoJsonObject = new JSONObject(userInfo);

        // 유저의 Email 추출
        JSONObject responseJsonObject = userInfoJsonObject.getJSONObject("response");
        String email = responseJsonObject.getString("email");

        // 유저 정보를 세션에 저장
        session.setAttribute("email", email);
        session.setAttribute("accessToken", accessToken);

        model.addAttribute("accessToken", accessToken);
        System.out.println(userInfo);
        System.out.println(email);
        System.out.println(accessToken);
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

        // 세션 무효화
        session.invalidate();

        // 홈 페이지로 리다이렉트
        return "redirect:/home";
    }

    private void logoutApiRequest(String accessToken) {
        // API 로그아웃 요청 코드
        // RestTemplate 등을 사용하여 API에 로그아웃 요청을 보낼 수 있습니다.
        // 자세한 구현은 사용하는 API에 따라 다를 수 있습니다.
        // 예시:
        String apiUrl = "https://example.com/logout";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

        // 응답 확인 등 로깅 코드를 추가할 수 있습니다.
        // 예시:
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Logout request successful");
        } else {
            System.err.println("Logout request failed. Status code: " + response.getStatusCode());
        }
    }


	public String generateState() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

}
