package com.score.scoredev.domain.user;

import com.score.scoredev.oauth.JwtProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @RequestMapping(value = "/user/auth/naver", method = RequestMethod.GET)
    public ResponseEntity<Object> getNaverUserKey(@RequestParam("id") String userKey, HttpServletResponse response) {
        HttpHeaders httpHeaders = checkWhetherNewOrNot(userKey, response);
        return new ResponseEntity<>(response, httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    @RequestMapping(value = "/user/auth/kakao", method = RequestMethod.GET)
    public ResponseEntity<Object> getKakaoUserKey(@RequestParam("id") String userKey, HttpServletResponse response) {
        HttpHeaders httpHeaders = checkWhetherNewOrNot(userKey, response);
        return new ResponseEntity<>(response, httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    @RequestMapping(value = "/user/auth/google", method = RequestMethod.GET)
    public ResponseEntity<Object> getGoogleUserKey(@RequestParam("id") String userKey, HttpServletResponse response) {
        HttpHeaders httpHeaders = checkWhetherNewOrNot(userKey, response);
        return new ResponseEntity<>(response, httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    // 해당 회원 정보가 이미 db에 존재하는지 여부에 따라 메인 페이지 혹은 온보딩 페이지로 이동
    private HttpHeaders checkWhetherNewOrNot(String userKey, HttpServletResponse response) {
        Optional<User> userOption = userService.findUserByUserKey(userKey);
        HttpHeaders httpHeaders = new HttpHeaders();

        userOption.ifPresentOrElse(
                // if present: 일치하는 회원 데이터가 존재하는 경우 -> 인증 수행 후 메인 페이지로 이동
                user -> {
                    boolean isAuthenticated = jwtProvider.validateToken(user.getNickname(), response);
                    if (isAuthenticated) {
                        httpHeaders.setLocation(URI.create("http://localhost:8080/user/score/main"));
                    }
                },
                // or else: 존재하지 않는 회원인 경우 -> 회원 정보 입력 페이지로 이동
                () -> httpHeaders.setLocation(URI.create("http://localhost:8080/user/score/join/#1")));
        return httpHeaders;
    }
}