package com.smhrd.yeaha.controller;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.smhrd.yeaha.service.GetUserInfoService;
import com.smhrd.yeaha.service.RestJsonService;
import org.springframework.web.bind.annotation.RequestParam;
import org.json.JSONObject;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.springframework.http.HttpHeaders;


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

        // 유저 정보가 포함된 JSON String 받기
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
        
        // 콘솔창 로깅
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
        // 네이버 API에 로그아웃 요청을 RestTemplate 등을 사용하여 보냄
       	String apiUrl = "https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=puXPnu8QxCDBHhnD9gRj&client_secret=ed7dNaKXIo&access_token=" + accessToken + "&service_provider=NAVER";
        
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
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

}
