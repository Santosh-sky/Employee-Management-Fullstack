package com.example.employee_system.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(String email, String role) {

        return Jwts.builder()
                .subject(email)
                .claim("role",role)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60 * 24
                        )
                )
                .signWith(
                        io.jsonwebtoken.security.Keys.hmacShaKeyFor(
                                SECRET_KEY.getBytes()
                        )
                )
                .compact();
    }

    public String extractEmail(String token){
        return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(
                            SECRET_KEY.getBytes())
                    )
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
    }

    public String extractRole(String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role",String.class);
    }
}