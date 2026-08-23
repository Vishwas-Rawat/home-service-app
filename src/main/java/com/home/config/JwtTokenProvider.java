package com.home.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final String jwtSecret = "m4Gq8FPNyzafKFUcFmwBFVWSan8ROca78mPoDecNdBa";
    private final long jwtExpirationInMs = 86400000;

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // Generates the token when a user logs in successfully
    public String generateToken(Authentication authentication){
        User userPrincipal = (User) authentication.getPrincipal();
        Date date = new Date();
        Date expiryTime = new Date(date.getTime() + jwtExpirationInMs);
        return Jwts.builder()
                .subject(userPrincipal.getUsername()) // Subject is the user's email
                .issuedAt(new Date())
                .expiration(expiryTime)
                .signWith(getSigningKey())
                .compact();
    }

    // Decodes the token to read the user's email
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    // Validates if the token is authentic and not expired
    public boolean isTokenValid(String token){
        try{
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
                    return true;
        }catch(Exception exception){
            return false;
        }
    }
}
