package com.token.mapper;

import com.token.annotation.AutoFill;
import com.token.entity.Dispatch;
import com.token.eunms.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DispatchMapper {
    @Insert("insert into dispatch(repair_id, student_id,service_id, fault_analysis, maintenance_process, repair_Results, status, del_flag, create_by, create_time, update_by, update_time) values " +
            "(#{repairId},#{studentId},#{serviceId},#{faultAnalysis},#{maintenanceProcess},#{repairResults},#{status},#{delFlag},#{createBy},#{createTime},#{updateBy},#{updateTime})")
    @AutoFill(OperationType.INSERT)
    void insert(Dispatch dispatch);

    List<Dispatch> List(Dispatch dispatch);

    @AutoFill(OperationType.UPDATE)
    void update(Dispatch dispatch);
}
