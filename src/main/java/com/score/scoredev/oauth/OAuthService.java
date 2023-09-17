package com.score.scoredev.oauth;


import com.score.scoredev.domain.user.UserService;
import com.score.scoredev.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuthService {

    private final JwtProvider jwtProvider;
    private final UserService userService;

    public TokenDto loginWithToken(String userKey) {
        userService.findUserByUserKey(userKey);
        return jwtProvider.getNewToken(userKey);
    }


    public void createKakaoUser(String token) {
        // 회원가입시 새로운 유저 엔티티 생성

    }

    public void createGoogleUser(String token) {
        // 회원가입시 새로운 유저 엔티티 생성
    }
}