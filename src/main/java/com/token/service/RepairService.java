package com.token.service;

import com.token.entity.Repair;
import com.token.entity.vo.RepairVO;

import java.util.List;

public interface RepairService {

    /**
     * 新增报修信息
     * @param repairVO
     */
    void insert(RepairVO repairVO);

    /**
     * 查询报修信息集合
     * @param repair
     * @return
     */
    List<RepairVO> repairList(Repair repair);

    /**
     * 修改报修信息
     * @param repairVO
     */
    void update(RepairVO repairVO);

    /**
     * 删除报修信息
     * @param repairVO
     */
    void delete(RepairVO repairVO);
}
