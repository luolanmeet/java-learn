package pers.mybatis.test;

import pers.mybatis.annotations.Select;

public interface TestMapper {
    
    @Select("SELECT * FROM t_mybatis "
            + "WHERE "
            + "id = #{param1}")
    Test findByid(Integer id);
    
    @Select("SELECT * FROM t_mybatis "
            + "WHERE "
            + "id = #{param1} "
            + "AND name = #{param2}")
    Test findByIdAndName(Integer id, String name);
    
    @Select("SELECT * FROM t_mybatis "
            + "WHERE "
            + "id = #{param1} "
            + "AND nums = #{param2}" )
    Test findByIdAndNums(Integer id, Integer nums);
    
}
