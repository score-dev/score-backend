package com.score.scoredev.oauth;


import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


@Service
public class OAuthService {

    private String reqURL;

    public String createKakaoUser(String token) throws IOException {
            reqURL = "https://kapi.kakao.com/v2/user/me";
            URL url = new URL(reqURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);

            return OAuthAuthorization.getResponse(urlConnection);
    }
}