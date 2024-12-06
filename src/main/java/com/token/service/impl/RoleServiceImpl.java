package com.token.service.impl;

import com.token.mapper.RoleMapper;
import com.token.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 根据用户名查询用户角色
     *
     * @return
     */
    @Override
    public List<String> selectRolesUserName(String name) {
        return roleMapper.getByRolesUserName(name);
    }
}
