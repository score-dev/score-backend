package com.score.scoredev.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @RequestMapping(value = "/user/auth/naver", method = RequestMethod.GET)
    public ResponseEntity<String> getNaverUserKey(@RequestParam("id") String userKey) {
        System.out.println("test - " + userKey);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(userKey, httpHeaders, OK);
    }

    @RequestMapping(value = "/user/auth/kakao", method = RequestMethod.GET)
    public String getKakaoUserKey(@RequestParam("id") String userKey) {
        return userKey;
    }

    @RequestMapping(value = "/user/auth/google", method = RequestMethod.GET)
    public String getGoogleUserKey(@RequestParam("id") String userKey) {
        return userKey;
    }
}
