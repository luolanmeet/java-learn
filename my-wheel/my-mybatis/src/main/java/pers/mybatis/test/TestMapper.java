package pers.mybatis.test;

import pers.mybatis.annotations.Select;

public interface TestMapper {
    
    @Select("select * from t_mybatis where id = #{param1}")
    Test findByid(Integer id);
}
