package com.score.scoredev.oauth;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class OAuthAuthorization {
    private String HOST;

    public String getKakaoAccessToken(String code) throws IOException {
        HOST = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(HOST);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);    // 데이터 기록 알려주기
            Map<String, String> authInfo = new HashMap<>();
            authInfo.put("grant_type=", "authorization_code");
            authInfo.put("&client_id=", "8b17f5e6120af126b2b89855e487272f");
            authInfo.put("redirect_uri=", "http://localhost:8080/app/login/kakao");
            authInfo.put("code", code);
            buildRequest(authInfo, urlConnection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseToken(getResponse(urlConnection));
    }


    private void buildRequest(Map<String, String> authInfo, HttpURLConnection urlConnection) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
//      System.out.println("start-------");
//      System.out.println("restApiKey: " + restApiKey);
//      System.out.println("code2: " + code);

        StringBuilder sb = new StringBuilder();
        for (String key : authInfo.keySet()) {
            sb.append(key).append("=").append(authInfo.get(key)).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);

//      System.out.println("sb: " + sb);

        bw.write(sb.toString());
//      int responseCode = urlConnection.getResponseCode();
//      System.out.println("responseCode = " + responseCode);
        bw.flush();
        bw.close();
    }

    static String getResponse(HttpURLConnection urlConnection) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String line = "";
        StringBuilder result = new StringBuilder();
        while ((line = br.readLine()) != null) {
            result.append(line);
        }
//      System.out.println("response body = " + result);
        br.close();

        return result.toString();
    }

    private String parseToken(String responseBody) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject elem = (JSONObject) parser.parse(responseBody);

            String access_token = elem.get("access_token").toString();
            String refresh_token = elem.get("refresh_token").toString();

//            System.out.println("access_token = " + access_token);
//            System.out.println("refresh_token = " + refresh_token);

            return access_token;
        } catch (ParseException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

}
