package pers.mapper;

import org.apache.ibatis.annotations.Param;
import pers.bean.User;

public interface IUserMapper {

    void save(User user);
    
    User findById(Integer userId);
    
    User findByIdAndName(
            Integer userId,
            @Param("name") String name);
    
}
