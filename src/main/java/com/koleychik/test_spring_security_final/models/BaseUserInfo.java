package com.koleychik.test_spring_security_final.models;

import com.koleychik.test_spring_security_final.security.Role;
import lombok.Data;

@Data
public class BaseUserInfo {

    private String name, lastName, email, password;
    private int age;
    private boolean isAccountNonExpired = true, isAccountNonLocked = true,
            isCredentialsNonExpired = true, isEnabled = true;

    private Role role = Role.USER;

    public BaseUserInfo(String name, String lastName, String email, String password, int age) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public BaseUserInfo(String name, String lastName, String email, String password, int age,
                        boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired,
                        boolean isEnabled, Role role) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.age = age;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
        this.role = role;
    }
}
