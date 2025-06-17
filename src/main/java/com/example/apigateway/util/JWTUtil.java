package com.example.apigateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {

    SecretKey secretKey = Jwts.SIG.HS256.key().build();
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new  Date())
                .expiration(new  Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(secretKey)
                .compact();
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

}
