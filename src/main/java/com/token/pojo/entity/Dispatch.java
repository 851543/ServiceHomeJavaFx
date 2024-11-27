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
@TableName(value ="dispatch")
public class Dispatch  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value="id")
    @TableField(value="id")
    private Long id;

    /**
     * 报修id 
     */
    @TableField(value="repair_id")
    private Integer repairId;

    /**
     * 报修状态（0未处理 1已处理） 
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
