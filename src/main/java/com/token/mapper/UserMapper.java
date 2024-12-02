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
public interface UserMapper {

    /**
     * 新增用户数据
     * @param user
     */
    void insert(User user);

    /**
     * 根据用户信息获取用户数据
     * @param user
     * @return
     */
    User getUserByUser(User user);

    /**
     * 根据用户信息获取用户数据集合
     * @param user
     * @return
     */
    List<User> getUserListByUser(User user);
}
