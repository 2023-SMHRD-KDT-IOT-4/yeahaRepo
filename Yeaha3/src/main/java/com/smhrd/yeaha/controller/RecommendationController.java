package com.smhrd.yeaha.controller;

import javax.servlet.http.HttpSession;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import redis.clients.jedis.Jedis;

@SessionAttributes("recommendationTextsFromRedis")
@EnableSpringDataWebSupport
@Controller
public class RecommendationController {

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String integrateAndShow(Model model) {
		// Connect to Flask application and get recommendation texts
		String flaskAppUrl = "http://localhost:5005";
		String flaskResultEndpoint = "/";

		// Build the full URL with query parameters
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(flaskAppUrl + flaskResultEndpoint)
				.queryParam("val1", 1).queryParam("val2", 0).queryParam("val3", 1).queryParam("val4", 0);

		String fullUrl = builder.toUriString();
		System.out.println(fullUrl);
		String recommendationTextsFromFlask = new RestTemplate().getForObject(fullUrl, String.class);

		// Connect to Redis
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		// jedis.auth("1234");
		// Get recommendation texts from Redis
		String recommendationTextsFromRedis = jedis.get("recommend_texts");
		System.out.println("제디스 닫기 전 Recommendation Texts from Flask: " + recommendationTextsFromFlask);
		model.addAttribute("제디스 닫기 전 recommendationTextsFromRedis", recommendationTextsFromRedis);

		// Close Redis connection
		jedis.close();

		// Add recommendationTexts to the model
		model.addAttribute("recommendationTextsFromFlask", recommendationTextsFromFlask);
		model.addAttribute("recommendationTextsFromRedis", recommendationTextsFromRedis);

		// Print to console (for verification)
		System.out.println("Recommendation Texts from Flask: " + recommendationTextsFromFlask);
		System.out.println("Recommendation Texts from Redis: " + recommendationTextsFromRedis);

		return "redirect:/recommendationPage";
	}

	@RequestMapping(value = "/recommendationPage", method = RequestMethod.GET)
	public String showRecommendationPage() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		String recommendationTextsFromRedis = jedis.get("recommend_texts");
		System.out.println(recommendationTextsFromRedis);
		
		jedis.close();

		return "recommendationPage";
	}
}