package com.mybatis.simple.mapper;

import org.apache.ibatis.annotations.Param;

import com.mybatis.simple.bean.User;

public interface IUserMapper {

    User findById(Integer userId);
    
    User findByIdAndName(
            Integer userId, 
            @Param("name") String name);
    
}
