package pers.mybatis.executor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pers.mybatis.mapping.BoundSql;
import pers.mybatis.mapping.MappedStatement;
import pers.mybatis.test.Test;

public class SimpleExecutor implements Executor {

    @Override
    public <T> T query(MappedStatement ms, Object parameter) {
        
        // 拿到我们的SQL
        BoundSql boundSql = ms.getBoundSql(parameter);
        
        // FIXME
        String statement = boundSql.getSql();
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Test test = null;

        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis?useUnicode=true&characterEncoding=utf-8&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root",
                    "121213");

            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(String.format(statement, Integer.parseInt(parameter.toString())));
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                test = new Test();
                test.setId(rs.getInt(1));
                test.setNums(2);
                test.setName(rs.getString(3));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != connection) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return (T)test;
    }

}
