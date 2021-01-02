package com.koleychik.test_spring_security_final.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userAuthService")
public class UserAuthService implements UserDetailsService {

    private final AuthDao dao;

    @Autowired
    public UserAuthService(@Qualifier("authDaoImpl") AuthDao dao) {
        this.dao = dao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return dao.getUserAuthByName(username).orElseThrow(() -> new NullPointerException("cannot find user with name = " + username));
    }
}
