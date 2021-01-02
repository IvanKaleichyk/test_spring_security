package com.koleychik.test_spring_security_final.security;

public enum Permission {
    INFO_READ("info:read"), INFO_POST("info:post"), INFO_DELETE("info:delete"), INFO_UPDATE("info:update"),
    USERS_READ("users:read"), USERS_POST("users:post"), USERS_DELETE("users:delete"), USERS_UPDATE("users:update");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
