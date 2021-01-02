package com.koleychik.test_spring_security_final.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koleychik.test_spring_security_final.models.AuthRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtUserEmailAndPasswordFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager manager;
    private final JwtUtils jwtUtils;

    public JwtUserEmailAndPasswordFilter(AuthenticationManager manager, JwtUtils jwtUtils) {
        this.manager = manager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthRequest authRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), AuthRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(),
                    authRequest.getPassword()
            );

            return manager.authenticate(authentication);

        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String token = jwtUtils.createToken(authResult.getName(), authResult.getAuthorities());

        response.addHeader(jwtUtils.getHeader(), token);
    }
}
