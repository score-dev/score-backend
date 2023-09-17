package com.score.scoredev.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigInteger;
import java.security.SecureRandom;

@Controller
@RequestMapping("/api/naver")
public class NaverController {

    @GetMapping("/oauth")
    public String naverConnect(){
        // state용 난수 생성
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString(32);

        // redirect
        StringBuffer url = new StringBuffer();
        url.append(NAVER_AUTH_URI + "")
    }
}
