package com.token.mapper;

import com.token.annotation.AutoFill;
import com.token.entity.Repair;
import com.token.entity.vo.RepairVO;
import com.token.eunms.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface RepairMapper {

    @Insert("insert into repair(user_id, floor, room, avatar, content, del_flag, status, create_time, create_by, update_time, update_by) value " +
            "(#{userId},#{floor},#{room},#{avatar},#{content},#{delFlag},#{status},#{createTime},#{createBy},#{updateTime},#{updateBy})")
    @AutoFill(OperationType.INSERT)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Repair repair);

    List<Repair> repairList(Repair repair);

    @AutoFill(OperationType.UPDATE)
    void update(Repair repair);
}
