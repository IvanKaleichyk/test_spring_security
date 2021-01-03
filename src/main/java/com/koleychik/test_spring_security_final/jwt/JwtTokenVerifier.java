package com.koleychik.test_spring_security_final.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(JwtTokenVerifier.class);

    private final JwtUtils jwtUtils;

    public JwtTokenVerifier(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(jwtUtils.getHeader());

        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtUtils.getTokenPrefix()))
            throw new IOException("failed authorizationHeader, is = " + authorizationHeader);

        String token = authorizationHeader.replace(jwtUtils.getTokenPrefix(), "");

        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(jwtUtils.getKey()).build().parseClaimsJws(token);

        Claims body = claimsJws.getBody();

        String username = body.getSubject();

        List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("role");

        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                .collect(Collectors.toSet());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username,
                null,
                simpleGrantedAuthorities
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        logger.error("request.getServletPath() = " + request.getServletPath());
        return request.getServletPath().startsWith("/auth");
    }
}
