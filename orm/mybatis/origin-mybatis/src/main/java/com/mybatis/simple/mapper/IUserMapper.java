package com.mybatis.simple.mapper;

import com.mybatis.simple.bean.User;

public interface IUserMapper {

    User findById(Integer userId);
    
}
