package com.score.scoredev.oauth;


import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class OAuthService{
    public String getKakaoAccessToken(String restApiKey, String code) throws IOException {
        final String HOST = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(HOST);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String token = "";
        try {
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);    //데이터 기록 알려주기

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            System.out.println("start-------");
            System.out.println("restApiKey: " + restApiKey);
            System.out.println("code2: " + code);
            String sb = "grant_type=authorization_code" + "&client_id= " + restApiKey
                    + "&redirect_uri= http://localhost:8080/app/login/kakao"
                    + "&code= " + code;
            System.out.println("sb: " + sb);

            bw.write(sb);
            bw.flush();

            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null){
                result += line;
            }
            System.out.println("result = " + result);

            // json parsing
            JSONParser parser = new JSONParser();
            JSONObject elem = (JSONObject) parser.parse(result);

            String access_token = elem.get("access_token").toString();
            String refresh_token = elem.get("refresh_token").toString();

            System.out.println("access_token = " + access_token);
            System.out.println("refresh_token = " + refresh_token);

            token = access_token;

            br.close();
            bw.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return token;
    }


}