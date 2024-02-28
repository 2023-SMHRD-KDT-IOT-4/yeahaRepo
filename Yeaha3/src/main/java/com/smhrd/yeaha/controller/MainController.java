package com.smhrd.yeaha.controller;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

        // 유저 정보를 세션에 저장합니다.
        session.setAttribute("email", email);
        session.setAttribute("accessToken", accessToken);

        model.addAttribute("sessionEmail", email);
        System.out.println(userInfo);
        System.out.println(email);
        System.out.println(accessToken);
        return "receiveAC";
    }

	public String generateState() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

}
