package com.score.scoredev.oauth;

import com.score.scoredev.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    public TokenDto getNewToken(String userKey) {
        return new TokenDto(createAccessToken(userKey), createRefreshToken(userKey));
    }

    public Jws<Claims> getClaimsFromJwt(String token) {
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
    }

    private String createAccessToken(String userKey) {
        Claims claims = Jwts.claims().setSubject(userKey);
        Date presentDate = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(presentDate)
                .setExpiration(new Date(presentDate.getTime() + ACCESS_TOKEN_VALID_MILISECOND))
                .signWith(getSecretKey())
                .compact();
    }

    private String createRefreshToken(String userKey) {
        Claims claims = Jwts.claims().setSubject("RefreshToken");
        Date presentDate = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(presentDate)
                .setExpiration(new Date(presentDate.getTime() + REFRESH_TOKEN_VALID_MILISECOND))
                .signWith(getSecretKey())
                .compact();
    }
}
