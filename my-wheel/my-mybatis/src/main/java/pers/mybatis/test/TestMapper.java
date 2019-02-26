package pers.mybatis.test;

import pers.mybatis.annotations.Select;

public interface TestMapper {
    
    @Select("select * from t_mybatis where id = %d")
    Test findByid(Integer id);
}
