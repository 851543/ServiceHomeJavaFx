package com.token.mapper;

import com.token.annotation.AutoFill;
import com.token.entity.User;
import com.token.entity.dto.UserRoleDTO;
import com.token.eunms.OperationType;
import org.apache.ibatis.annotations.Mapper;

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
    @AutoFill(OperationType.INSERT)
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

    /**
     * 指定用户角色数据集合
     * @param userRoleListDTO
     * @return
     */
    List<User> getUserRoleListByUser(UserRoleDTO userRoleListDTO);

    /**
     * 修改用户
     * @param user
     */
    @AutoFill(OperationType.UPDATE)
    void update(User user);

    /**
     * 用户角色查询标识
     * @param roleDTO
     * @return
     */
    User getUserByUserRole(UserRoleDTO roleDTO);
}
