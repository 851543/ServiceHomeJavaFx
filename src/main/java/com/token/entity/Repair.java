package com.token.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;


/** 
 * @author 阿俊
 * @description  
 */
@TableName(value ="repair")
public class Repair  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value="id")
    @TableField(value="id")
    private Long id;

    /**
     * 用户id 
     */
    @TableField(value="user_id")
    private Integer userId;

    /**
     * 楼号 
     */
    @TableField(value="floor")
    private String floor;

    /**
     * 房号 
     */
    @TableField(value="room")
    private String room;

    /**
     * 内容 
     */
    @TableField(value="content")
    private String content;

    /**
     * 报修状态（0未提交 1待受理 2已派工 3维修结束） 
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
