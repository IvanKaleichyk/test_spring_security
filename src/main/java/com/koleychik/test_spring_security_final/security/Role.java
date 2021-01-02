package com.koleychik.test_spring_security_final.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMIN(Set.of(Permission.INFO_READ, Permission.INFO_POST, Permission.INFO_UPDATE, Permission.INFO_DELETE,
            Permission.USERS_READ, Permission.USERS_POST, Permission.USERS_UPDATE, Permission.USERS_DELETE)),

    USER(Set.of(Permission.INFO_READ, Permission.INFO_POST, Permission.INFO_UPDATE, Permission.INFO_DELETE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getSimpleGrantedAuthorities() {
        Set<SimpleGrantedAuthority> sets = getPermissions().stream()
                .map(it -> new SimpleGrantedAuthority(it.getPermission()))
                .collect(Collectors.toSet());

        sets.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return sets;
    }
}
