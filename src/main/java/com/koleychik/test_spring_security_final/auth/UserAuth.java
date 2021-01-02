package com.koleychik.test_spring_security_final.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserAuth  {
}

//
//    private final String username, password;
//    private final boolean isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled;
//    private final Collection<? extends GrantedAuthority> authorities;
//
//    public UserAuth(String username, String password, boolean isAccountNonExpired,
//                    boolean isAccountNonLocked, boolean isCredentialsNonExpired,
//                    boolean isEnabled, Collection<? extends GrantedAuthority> authorities) {
//        this.username = username;
//        this.password = password;
//        this.isAccountNonExpired = isAccountNonExpired;
//        this.isAccountNonLocked = isAccountNonLocked;
//        this.isCredentialsNonExpired = isCredentialsNonExpired;
//        this.isEnabled = isEnabled;
//        this.authorities = authorities;
//    }
//
//    @Override
//
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return isAccountNonExpired;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return isAccountNonLocked;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return isCredentialsNonExpired;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return isEnabled;
//    }
//}
