package com.score.scoredev.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @RequestMapping(value = "/user/auth/naver", method = RequestMethod.GET)
    public String getNaverUserKey(@RequestParam("id") String userKey) {
        return userKey;
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
