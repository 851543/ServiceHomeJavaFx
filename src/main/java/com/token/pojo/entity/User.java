package com.token.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;


/** 
 * @author 阿俊
 * @description  
 */
@TableName(value ="user")
public class User  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value="id")
    @TableField(value="id")
    private Long id;

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
     * 用户邮箱 
     */
    @TableField(value="email")
    private String email;

    /**
     * 手机号码 
     */
    @TableField(value="phone_number")
    private String phoneNumber;

    /**
     * 用户性别（0男 1女 2未知） 
     */
    @TableField(value="sex")
    private String sex;

    /**
     * 头像地址 
     */
    @TableField(value="avatar")
    private String avatar;

    /**
     * 密码 
     */
    @TableField(value="password")
    private String password;

    /**
     * 帐号状态（0正常 1停用） 
     */
    @TableField(value="status")
    private String status;

    /**
     * 删除标志（0代表存在 1代表删除） 
     */
    @TableField(value="del_flag")
    private String delFlag;

    /**
     * 创建者 
     */
    @TableField(value="create_by")
    private String createBy;

    /**
     * 创建时间 
     */
    @TableField(value="create_time")
    private LocalDateTime createTime;

    /**
     * 更新者 
     */
    @TableField(value="update_by")
    private String updateBy;

    /**
     * 更新时间 
     */
    @TableField(value="update_time")
    private LocalDateTime updateTime;

}
