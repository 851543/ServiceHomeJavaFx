package com.token.service.impl;

import com.token.constant.DefaultPassword;
import com.token.entity.User;
import com.token.eunms.UserRole;
import com.token.mapper.RoleMapper;
import com.token.mapper.UserMapper;
import com.token.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 用户登录
     * @param userDTO
     * @return
     */
    @Override
    public boolean login(UserRole loginRole, User userDTO) {
        if (!userEmptyCheck(userDTO)){
            return false;
        }
        String pws = userDTO.getPassword();
        userDTO.setPassword(null);
        User user = userMapper.getUserByUser(userDTO);
        if (ObjectUtils.isEmpty(user) || StringUtils.isEmpty(user.getUserName())){
            return false;
        }
        if (!roleMapper.getByRolesUserName(userDTO.getUserName()).stream().anyMatch(role -> role.equals(getRole(loginRole)))) return false;
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(pws.getBytes()))) return false;
        return true;
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
     * 查询学号
     * @return
     */
    @Override
    public User selectName(String name) {
        User user = new User();
        user.setUserName(name);
        return userMapper.getUserByUser(user);
    }

    /**
     * 设置用户角色
     * @param loginRole
     */
    private void setUserRole(UserRole loginRole,Long userId){
        switch (loginRole){
            case ADMIN:
                roleMapper.insertUserRole(loginRole.ADMIN.getId(),userId);
            case STUDENT:
                roleMapper.insertUserRole(loginRole.STUDENT.getId(),userId);
            case SERVICE:
                roleMapper.insertUserRole(loginRole.SERVICE.getId(),userId);
            default:
                break;
        }
    }

    /**
     * 获取角色
     * @param loginRole
     * @return
     */
    private String getRole(UserRole loginRole){
        switch (loginRole){
            case ADMIN:
                return  "admin";
            case STUDENT:
                return  "student";
            case SERVICE:
                return  "service";
            default:
                break;
        }
        return "";
    }

    /**
     * 用户校验
     * @param userDTO
     * @return
     */
    private boolean userEmptyCheck(User userDTO){
        if (ObjectUtils.isEmpty(userDTO) || StringUtils.isEmpty(userDTO.getUserName())){
            return false;
        }
        return true;
    }
}
