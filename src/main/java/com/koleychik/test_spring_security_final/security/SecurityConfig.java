package com.koleychik.test_spring_security_final.security;

import com.koleychik.test_spring_security_final.auth.UserAuthService;
import com.koleychik.test_spring_security_final.jwt.JwtTokenVerifier;
import com.koleychik.test_spring_security_final.jwt.JwtUserEmailAndPasswordFilter;
import com.koleychik.test_spring_security_final.jwt.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtUtils jwtUtils;
    private final UserAuthService userAuthService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(JwtUtils jwtUtils, UserAuthService userAuthService, PasswordEncoder passwordEncoder) {
        this.jwtUtils = jwtUtils;
        this.userAuthService = userAuthService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUserEmailAndPasswordFilter(authenticationManager(), jwtUtils))
                .addFilterAfter(new JwtTokenVerifier(jwtUtils),JwtUserEmailAndPasswordFilter.class)
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userAuthService);
        return provider;
    }

}
