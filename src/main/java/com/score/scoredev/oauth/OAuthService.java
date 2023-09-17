package com.score.scoredev.oauth;


import com.score.scoredev.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuthService {

    private final JwtProvider jwtProvider;

    public TokenDto loginWithToken(String userKey) {
        return jwtProvider.getNewToken(userKey);
    }


    public void createKakaoUser(String token) {
        // 회원가입시 새로운 유저 엔티티 생성

    }

    public void createGoogleUser(String token) {
        // 회원가입시 새로운 유저 엔티티 생성
    }

    public void createNaverUser(String token){

    }
}