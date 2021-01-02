package com.koleychik.test_spring_security_final.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    private final UserDetailsService userDetailsService;
    private SecretKey key;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @Value("${jwt.authorities}")
    private String authorities;

    public JwtUtils(@Qualifier("userAuthService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init(){
        key = getSecretKey();
    }

    public String createToken(String username, Object role){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        Date now = new Date();
        Date validateAt = new Date(now.getTime() + expiration);

        return tokenPrefix + Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validateAt)
                .signWith(key)
                .compact();
    }

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public SecretKey getKey() {
        return key;
    }

    public String getHeader() {
        return header;
    }

    public String getSecret() {
        return secret;
    }

    public long getExpiration() {
        return expiration;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public String getAuthorities() {
        return authorities;
    }
}
