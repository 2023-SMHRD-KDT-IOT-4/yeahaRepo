package com.smhrd.yeaha.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
@NoArgsConstructor
@Service
public class GetUserInfoService {
    private final String HTTP_REQUEST = "https://openapi.naver.com/v1/nid/me";

    public String getUserInfo(String accessToken){
        try {
            String jsonData = "";

            // URI를 URL객체로 저장
            URL url = new URL(HTTP_REQUEST + "?access_token=" + accessToken);

            // 버퍼 데이터(응답 메세지)를 한 줄씩 읽어서 jsonData에 저장
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String line;
            while((line = bf.readLine()) != null){
                jsonData+=line;
            }
            return jsonData;

        } catch(Exception e) {
        	System.err.println("Error in GetUserInfoService: " + e.getMessage());
            e.printStackTrace();
            return "error";
        }
    }
}
