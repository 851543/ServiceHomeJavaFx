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
     * 根据用户名查询用户角色数据
     *
     * @return
     */
    List<String> getByRolesUserName(String userName);

    /**
     * 绑定用户角色数据
     * @param roleId
     * @param userId
     */
    void insertUserRole(@Param("roleId") Long roleId, @Param("userId") Long userId);
}
