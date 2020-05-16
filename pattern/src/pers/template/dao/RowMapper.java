package pers.template.dao;

import java.sql.ResultSet;

public interface RowMapper<T> {

    T mapRow(ResultSet rs, int rowNum) throws Exception;

}
