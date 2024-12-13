package com.token.service.impl;

import com.token.entity.Repair;
import com.token.entity.User;
import com.token.entity.vo.RepairVO;
import com.token.mapper.RepairMapper;
import com.token.mapper.UserMapper;
import com.token.service.RepairService;
import com.token.utils.ServiceHomeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepairServiceImpl implements RepairService {

    @Autowired
    private RepairMapper repairMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 新增报修信息
     * @param repairVO
     */
    @Override
    public void insert(RepairVO repairVO) {
        User user = new User();
        if (!ObjectUtils.isEmpty(repairVO.getUserName())){
            user.setUserName(repairVO.getUserName());
            user = userMapper.getUserByUser(user);
        }else {
            user = ServiceHomeUtils.getLoginUserInfo();
        }
        Repair repair = new Repair();
        BeanUtils.copyProperties(repairVO,repair);
        repair.setUserId(Math.toIntExact(user.getId()));
        repairMapper.insert(repair);


        BeanUtils.copyProperties(repair,repairVO);
    }

    /**
     * 查询报修信息集合
     * @param repair
     * @return
     */
    @Override
    public List<RepairVO> repairList(Repair repair) {
        List<Repair> repairList = repairMapper.repairList(repair);
        List<RepairVO> repairVOList = repairList.stream().map(item -> {
            User user = new User();
            user.setId(Long.valueOf(item.getUserId()));
            RepairVO repairVO = new RepairVO();
            user = userMapper.getUserByUser(user);
            BeanUtils.copyProperties(user,repairVO);
            BeanUtils.copyProperties(item,repairVO);
            return repairVO;
        }).collect(Collectors.toList());
        return repairVOList;
    }

    @Override
    public void update(RepairVO repairVO) {
        Repair repair = new Repair();
        BeanUtils.copyProperties(repairVO,repair);
        repairMapper.update(repair);
    }

    @Override
    public void delete(RepairVO repairVO) {
        Repair repair = new Repair();
        BeanUtils.copyProperties(repairVO,repair);
        repairMapper.update(repair);
    }
}
