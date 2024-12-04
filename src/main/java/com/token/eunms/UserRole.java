package com.token.eunms;

/**
 * 登陆角色枚举
 * @author 阿俊
 * @description
 */
public enum UserRole {
    STUDENT(1L),ADMIN(0L),SERVICE(2L);

    private final Long id;

    UserRole(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
