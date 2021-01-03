package com.koleychik.test_spring_security_final.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

//    public boolean validateToken(String token){
//        if (token.startsWith(tokenPrefix)){
//            token = token.replace(tokenPrefix, "");
//        }
//            try {
//                Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//                return !claimsJws.getBody().getExpiration().before(new Date());
//            } catch (JwtException | IllegalArgumentException e) {
////                throw new JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
//                return false;
//            }
//
//    }

    public String getTokenFromRequest(HttpServletRequest request) throws IOException {
        String authorizationHeader = request.getHeader(getHeader());

        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(getTokenPrefix()))
            throw new IOException("failed authorizationHeader, is = " + authorizationHeader);

        return authorizationHeader.replace(getTokenPrefix(), "");
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
