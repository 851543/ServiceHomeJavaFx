package com.token.eunms;

/**
 * 登陆角色枚举
 *
 * @author 阿俊
 * @description
 */
public enum UserRole {
    STUDENT(1L, "student"), ADMIN(0L, "admin"), SERVICE(2L, "service");

    private final Long id;

    private final String role;

    UserRole(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return this.role;
    }
}
