package com.token.service;

import com.token.entity.User;
import com.token.eunms.LoginRole;

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
}
