package com.score.scoredev.oauth;


import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


@Service
public class OAuthService {
    private final String KAKAO_USERINFO_REQUEST_URL = "https://kapi.kakao.com/v2/user/me";
    private final String GOOGLE_USERINFO_REQUEST_URL = "https://www.googleapis.com/oauth2/v1/userinfo";



    public String createKakaoUser(String token) throws IOException {
            URL url = new URL(KAKAO_USERINFO_REQUEST_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);

            return OAuthAuthorization.getResponse(urlConnection);
    }
}