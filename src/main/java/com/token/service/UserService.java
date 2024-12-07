package com.token.service;

import com.token.entity.User;
import com.token.entity.dto.UserRoleListDTO;
import com.token.eunms.UserRole;

import java.util.List;

/**
 * 用户业务接口
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @return
     */
    void login(User user);

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

    /**
     * 指定用户角色集合
     * @param userRoleListDTO
     * @return
     */
    List<User> userRoleList(UserRoleListDTO userRoleListDTO);

    /**
     * 修改用户
     *
     * @param user
     */
    void update(User user);
}
