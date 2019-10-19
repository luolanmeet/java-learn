package com.mybatis.simple.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mybatis 类型处理器
 * https://mybatis.org/mybatis-3/zh/configuration.html#typeHandlers
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
public class MyStringTypeHandler extends BaseTypeHandler<String> {
    
    // 执行SQL时，为SQL设值
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter + " through setNonNullParameter");
    }
    
    // 解析结果时进入的方法
    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName) + " through getNullableResult(ResultSet rs, String columnName)";
    }
    
    // 解析结果时进入的方法
    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getString(columnIndex) + " through getNullableResult(ResultSet rs, int columnIndex)";
    }
    
    // 解析结果时进入的方法
    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getString(columnIndex) + "through getNullableResult(CallableStatement cs, int columnIndex)";
    }
}
