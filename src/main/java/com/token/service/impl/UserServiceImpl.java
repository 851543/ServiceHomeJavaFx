package com.token.service.impl;

import com.token.constant.DefaultPassword;
import com.token.entity.User;
import com.token.entity.dto.UserRoleDTO;
import com.token.eunms.UserRole;
import com.token.mapper.RoleMapper;
import com.token.mapper.UserMapper;
import com.token.service.UserService;
import com.token.utils.ServiceHomeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 用户登录
     * @return
     */
    @Override
    public void login(User user) {
        ServiceHomeUtils.setLoginUserInfo(user);
    }

    /**
     * 新增用户
     * @param user
     */
    @Override
    @Transactional
    public void insert(UserRole loginRole, User user) {
        user.setPassword(DigestUtils.md5DigestAsHex(DefaultPassword.USER_PASSWORD.getBytes()));
        userMapper.insert(user);
        setUserRole(loginRole,user.getId());
    }

    /**
     * 用户集合
     * @param user
     * @return
     */
    @Override
    public List<User> userList(User user) {
        return userMapper.getUserListByUser(user);
    }

    /**
     * 用户角色查询标识
     * @param roleDTO
     * @return
     */
    @Override
    public User selectName(UserRoleDTO roleDTO) {
        return userMapper.getUserByUserRole(roleDTO);
    }

    /**
     * 指定用户角色集合
     * @param userRoleListDTO
     * @return
     */
    @Override
    public List<User> userRoleList(UserRoleDTO userRoleListDTO) {
        return  userMapper.getUserRoleListByUser(userRoleListDTO);
    }

    /**
     * 修改用户
     * @param user
     */
    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    /**
     * 设置用户角色
     * @param loginRole
     */
    private void setUserRole(UserRole loginRole,Long userId){
        switch (loginRole){
            case ADMIN:
                roleMapper.insertUserRole(loginRole.ADMIN.getId(),userId);
                break;
            case STUDENT:
                roleMapper.insertUserRole(loginRole.STUDENT.getId(),userId);
                break;
            case SERVICE:
                roleMapper.insertUserRole(loginRole.SERVICE.getId(),userId);
                break;
            default:
                break;
        }
    }
}
