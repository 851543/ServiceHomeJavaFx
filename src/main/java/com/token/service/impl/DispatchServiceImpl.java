package com.token.service.impl;

import com.token.entity.Dispatch;
import com.token.entity.Repair;
import com.token.entity.User;
import com.token.entity.vo.RepairVO;
import com.token.eunms.DispatchStatus;
import com.token.eunms.RepairStatus;
import com.token.mapper.DispatchMapper;
import com.token.mapper.RepairMapper;
import com.token.mapper.UserMapper;
import com.token.service.DispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DispatchServiceImpl implements DispatchService {

    @Autowired
    private DispatchMapper dispatchMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RepairMapper repairMapper;

    @Override
    public void insert(Dispatch dispatch) {
        dispatchMapper.insert(dispatch);
    }
}
