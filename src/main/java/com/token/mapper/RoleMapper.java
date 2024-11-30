package com.token.mapper;

import com.token.entity.Role;
import com.token.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户数据接口
 */
@Mapper
public interface RoleMapper {

    /**
     * 根据用户名查询用户角色
     *
     * @return
     */
    List<String> getByRolesUserName(String userName);
}
