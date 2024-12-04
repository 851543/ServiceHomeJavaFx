package com.token.service;

import com.token.entity.User;
import com.token.eunms.UserRole;

import java.util.List;

/**
 * 用户业务接口
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    boolean login(UserRole loginRole, User user);

    /**
     * 新增用户
     *
     * @param user
     */
    void insert(UserRole loginRole, User user);

    /**
     * 用户集合
     *
     * @param user
     */
    List<User> userList(User user);

    /**
     * 查询学号
     */
    User selectName(String name);
}
