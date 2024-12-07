package com.token.service;

import com.token.entity.User;
import com.token.entity.dto.UserRoleDTO;
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
     * 用户角色查询标识
     * @param roleDTO
     * @return
     */
    User selectName(UserRoleDTO roleDTO);

    /**
     * 指定用户角色集合
     * @param userRoleListDTO
     * @return
     */
    List<User> userRoleList(UserRoleDTO userRoleListDTO);

    /**
     * 修改用户
     *
     * @param user
     */
    void update(User user);
}
