package com.token.mapper;

import com.token.entity.User;
import com.token.entity.UserConfig;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserConfigMapper {

    @Select("select id, user_id, config_name, config_key, config_value, config_type, create_by, create_time, update_by, update_time from user_config")
    UserConfig selectId(Long id);

    @Insert("insert into user_config(user_id, config_name, config_key, config_value) value " +
            "(#{userId},#{configName},#{configKey},#{configValue})")
    void insert(UserConfig userConfig);

    void update(UserConfig userConfig);
}
