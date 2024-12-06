package com.token.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;

public class UserRoleListDTO {

    /**
     * 用户账号
     */
    @TableField(value="user_name")
    private String userName;

    /**
     * 用户昵称
     */
    @TableField(value="nick_name")
    private String nickName;

    /**
     * 帐号状态（0正常 1停用）
     */
    @TableField(value="status")
    private String status;

    private String role;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
