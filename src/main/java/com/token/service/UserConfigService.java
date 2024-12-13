package com.token.service;

import com.token.entity.User;
import com.token.entity.UserConfig;

public interface UserConfigService {
    UserConfig select(Long id);

    void insert(UserConfig userConfig);

    void update(UserConfig userConfig);
}
