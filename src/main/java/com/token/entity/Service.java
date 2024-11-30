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
@TableName(value ="service")
public class Service  implements Serializable {

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
     * 报修id 
     */
    @TableField(value="repair_id")
    private Integer repairId;

    /**
     * 派修id 
     */
    @TableField(value="dispatch_id")
    private Integer dispatchId;

    /**
     * 故障分析 
     */
    @TableField(value="fault_analysis")
    private String faultAnalysis;

    /**
     * 维修过程 
     */
    @TableField(value="service_process")
    private String serviceProcess;

    /**
     * 维修结果 
     */
    @TableField(value="service_result")
    private String serviceResult;

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
