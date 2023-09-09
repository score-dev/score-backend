package com.score.scoredev.oauth;


import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

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