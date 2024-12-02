package com.token.service;

import com.token.entity.User;
import com.token.eunms.LoginRole;

import java.util.List;

/**
 * 用户业务接口
 */
public interface UserService {

    /**
     * 用户登录
     * @param user
     * @return
     */
    boolean login(LoginRole loginRole,User user);

    /**
     * 新增用户
     * @param user
     */
    boolean insert(User user);

    /**
     * 用户集合
     * @param user
     */
    List<User> userList(User user);
}
