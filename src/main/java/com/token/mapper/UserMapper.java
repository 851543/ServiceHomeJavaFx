package com.token.mapper;

import com.token.entity.Role;
import com.token.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 用户数据接口
 */
@Mapper
public interface UserMapper {

    /**
     * 新增用户数据
     * @param user
     */
    void insert(User user);

    /**
     * 根据用户名查询用户数据
     * @param userName
     * @return
     */
    User getByUserName(@Param("userName") String userName);
}
