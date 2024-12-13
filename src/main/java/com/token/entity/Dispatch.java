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
@TableName(value ="dispatch")
public class Dispatch  implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id")
    @TableField(value="id")
    private Long id;

    /**
     * 报修id 
     */
    @TableField(value="repair_id")
    private Integer repairId;

    /**
     * 学生id 
     */
    @TableField(value="student_id")
    private Integer studentId;

    /**
     * 维修员id
 
     */
    @TableField(value="service_id")
    private Integer serviceId;

    /**
     * 故障分析 
     */
    @TableField(value="fault_analysis")
    private String faultAnalysis;

    /**
     * 维修过程 
     */
    @TableField(value="maintenance_process")
    private String maintenanceProcess;

    /**
     * 维修结果 
     */
    @TableField(value="repair_Results")
    private String repairResults;

    /**
     * 维修状态（0未处理 1已处理）
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRepairId() {
        return repairId;
    }

    public void setRepairId(Integer repairId) {
        this.repairId = repairId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getFaultAnalysis() {
        return faultAnalysis;
    }

    public void setFaultAnalysis(String faultAnalysis) {
        this.faultAnalysis = faultAnalysis;
    }

    public String getMaintenanceProcess() {
        return maintenanceProcess;
    }

    public void setMaintenanceProcess(String maintenanceProcess) {
        this.maintenanceProcess = maintenanceProcess;
    }

    public String getRepairResults() {
        return repairResults;
    }

    public void setRepairResults(String repairResults) {
        this.repairResults = repairResults;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
