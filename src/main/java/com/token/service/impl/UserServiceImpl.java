package com.token.service.impl;

import com.token.constant.MessageConstant;
import com.token.entity.Role;
import com.token.entity.User;
import com.token.eunms.LoginRole;
import com.token.exception.UserException;
import com.token.mapper.RoleMapper;
import com.token.mapper.UserMapper;
import com.token.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public boolean login(LoginRole loginRole, User userDTO) {
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
     * @param userDTO
     */
    @Override
    public boolean insert(User userDTO) {
        if (!userEmptyCheck(userDTO)){
            return false;
        }
        if (StringUtils.isEmpty(userDTO.getPhoneNumber())){
            return false;
        }
        User user = userMapper.getUserByUser(userDTO);
        if (!ObjectUtils.isEmpty(user) && !StringUtils.isEmpty(user.getUserName())){
            return false;
        }
        userDTO.setPassword(DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes()));
        userMapper.insert(userDTO);
        return true;
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
     * 获取角色
     * @param loginRole
     * @return
     */
    private String getRole(LoginRole loginRole){
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
