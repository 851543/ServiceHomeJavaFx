package com.token.service;

import com.token.entity.Dispatch;
import com.token.entity.vo.RepairVO;

import java.util.List;

public interface DispatchService {
    void insert(Dispatch dispatch);

    List<Dispatch> List(Dispatch dispatch);

    void update(Dispatch dispatch);
}
