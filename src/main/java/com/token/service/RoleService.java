package com.token.service;

import java.util.List;

public interface RoleService {

    /**
     * 根据用户名查询用户角色
     *
     * @return
     */
    List<String> selectRolesUserName(String name);
}
