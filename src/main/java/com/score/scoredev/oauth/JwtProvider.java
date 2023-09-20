package com.score.scoredev.oauth;

import com.score.scoredev.domain.user.User;
import com.score.scoredev.domain.user.UserService;
import com.score.scoredev.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final long ACCESS_TOKEN_VALID_MILISECOND = 1000L * 60 * 60;
    private final long REFRESH_TOKEN_VALID_MILISECOND = 1000L * 60 * 60 * 24 * 14;

    @Value("${custom.jwt.secretKey}")
    private String plainSecretKey;
    private SecretKey cachedSecretKey;
    private final UserService userService;


    // plain key to secret key
    private SecretKey _getSecretKey() {
        String keyBase64Encoded = Base64.getEncoder().encodeToString(plainSecretKey.getBytes());
        return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
    }
    public SecretKey getSecretKey() {
        if (cachedSecretKey == null) {
            cachedSecretKey = _getSecretKey();
        }
        return cachedSecretKey;
    }

    public TokenDto getNewToken(String token) {
        return new TokenDto(createAccessToken(token), createRefreshToken());
    }

    // 토큰으로부터 세부 정보(claims) 얻기
    public Jws<Claims> getClaimsFromJwt(String token) {
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
    }

    //////////// 백엔드 코드에 있는 모든 'access token'은 소셜 로그인 인증 과정에서 사용되는 access token이 아니라 소셜 로그인 인증 완료 이후 발급하는 자체 jwt 토큰입니다! ////////////

    // access token 생성
    private String createAccessToken(String nickname) {
        Claims claims = Jwts.claims().setSubject(nickname);
        Date presentDate = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(presentDate)
                .setExpiration(new Date(presentDate.getTime() + ACCESS_TOKEN_VALID_MILISECOND))
                .signWith(getSecretKey())
                .compact();
    }

    // access token 만료시 갱신을 위한 refresh token 생성
    private String createRefreshToken() {
        Claims claims = Jwts.claims().setSubject("RefreshToken");
        Date presentDate = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(presentDate)
                .setExpiration(new Date(presentDate.getTime() + REFRESH_TOKEN_VALID_MILISECOND))
                .signWith(getSecretKey())
                .compact();
    }

    // 권한 소유 여부 확인을 위한 jwt 토큰 검증
    public boolean validateToken(String accessToken, HttpServletResponse response) {
        Jws<Claims> claims = getClaimsFromJwt(accessToken);

        // 인자로 받은 jwt access token이 유효한 경우 true 반환
        if (claims.getBody().getExpiration().after(new Date())) {
            return true;
        }

        // access token이 만료된 경우 db 내 해당 유저 엔티티에서 refresh token 정보 찾기
        User user = userService.findUserByNickname(claims.getBody().getSubject());
        String refreshToken = user.getRefreshToken();

        // 찾은 refresh token 만료 여부 확인
        Jws<Claims> refreshTokenClaims = getClaimsFromJwt(refreshToken);
        if (refreshTokenClaims.getBody().getExpiration().after(new Date())) {
            // refresh token이 유효한 경우 access token 갱신 후 true 반환
            response.setHeader(HttpHeaders.AUTHORIZATION, createAccessToken(claims.getBody().getSubject()));
        }
        // access token, refresh token이 모두 만료된 경우 false 반환
        response.setHeader(HttpHeaders.AUTHORIZATION, null);
        return false;
    }
}
