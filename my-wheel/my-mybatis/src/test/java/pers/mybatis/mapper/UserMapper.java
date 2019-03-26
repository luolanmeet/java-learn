package pers.mybatis.mapper;

import pers.mybatis.annotations.Select;

public interface UserMapper {
    
    @Select("SELECT * FROM t_user "
            + "WHERE "
            + "id = #{param1}")
    User findByid(Integer id);
    
}
