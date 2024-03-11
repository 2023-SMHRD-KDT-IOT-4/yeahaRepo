package com.smhrd.yeaha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import redis.clients.jedis.Jedis;
import java.util.List;

import javax.servlet.http.HttpSession;

@SessionAttributes("recommendationTextsFromRedis")
@EnableSpringDataWebSupport
@Controller
public class AlgoController {

	@Autowired
	private HttpSession httpSession;

	@RequestMapping(value = "/iniAlgo", method = RequestMethod.GET)
	public String integrateAndShow(Model model) {
		// Connect to Flask application and get recommendation texts
		String flaskAppUrl = "http://localhost:5008";
		String flaskResultEndpoint = "/";

		 // 세션에서 이메일 가져오기
        String userEmail = (String) httpSession.getAttribute("email");
System.out.println(userEmail);
System.out.println(userEmail);
System.out.println(userEmail);
System.out.println(userEmail);
		// Build the full URL with query parameters
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(flaskAppUrl + flaskResultEndpoint)
				.queryParam("email", userEmail);

		String fullUrl = builder.toUriString();
		System.out.println(fullUrl);
		String recommendationTextsFromFlask = new RestTemplate().getForObject(fullUrl, String.class);

		// Connect to Redis
		Jedis jedis = new Jedis("127.0.0.1", 6379);

		// Get recommendation texts from Redis as a list
		List<String> recommendationTextsFromRedis = jedis.lrange("recommend_texts", 0, -1);

		// Close Redis connection
		jedis.close();

		// Add recommendationTexts to the model
		model.addAttribute("recommendationTextsFromFlask", recommendationTextsFromFlask);
		model.addAttribute("recommendationTextsFromRedis", recommendationTextsFromRedis);

		// Print to console (for verification)
		System.out.println("Recommendation Texts from Flask: " + recommendationTextsFromFlask);
		System.out.println("Recommendation Texts from Redis: " + recommendationTextsFromRedis);

		return "iniAlgo";
	}

	@RequestMapping(value = "/algoResultPage", method = RequestMethod.GET)
	public String showRecommendationPage() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);

		// Get recommendation texts from Redis as a list
		List<String> recommendationTextsFromRedis = jedis.lrange("recommend_texts", 0, -1);

		jedis.close();

		return "algoResultPage";
	}
}
