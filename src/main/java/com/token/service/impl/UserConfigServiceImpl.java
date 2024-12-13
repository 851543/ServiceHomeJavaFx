package com.token.service.impl;

import com.token.entity.User;
import com.token.entity.UserConfig;
import com.token.mapper.UserConfigMapper;
import com.token.service.UserConfigService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserConfigServiceImpl implements UserConfigService {

    @Autowired
    private UserConfigMapper userConfigMapper;

    @Override
    public UserConfig select(Long id) {
        return userConfigMapper.selectId(id);
    }

    @Override
    public void insert(UserConfig userConfig) {
        userConfigMapper.insert(userConfig);
    }

    @Override
    public void update(UserConfig userConfig) {
        userConfigMapper.update(userConfig);
    }
}
