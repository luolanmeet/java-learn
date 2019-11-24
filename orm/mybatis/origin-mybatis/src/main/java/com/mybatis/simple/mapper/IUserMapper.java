package com.mybatis.simple.mapper;

import org.apache.ibatis.annotations.Param;

import com.mybatis.simple.bean.User;

import java.util.List;

public interface IUserMapper {

    void save(User user);
    
    User findById(Integer userId);
    
    User findByIdAndName(
            Integer userId, 
            @Param("name") String name);

    List<User> findByName(@Param("name") String name);
    
}
