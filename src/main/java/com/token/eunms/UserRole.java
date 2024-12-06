package com.token.eunms;

/**
 * 登陆角色枚举
 *
 * @author 阿俊
 * @description
 */
public enum UserRole {
    STUDENT(2L, "student"), ADMIN(1L, "admin"), SERVICE(3L, "service");

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
