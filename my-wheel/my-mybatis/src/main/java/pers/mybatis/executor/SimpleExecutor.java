package pers.mybatis.executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import pers.mybatis.mapping.BoundSql;
import pers.mybatis.mapping.MappedStatement;
import pers.mybatis.session.Configuration;
import pers.mybatis.test.Test;
import pers.mybatis.transaction.Transaction;

public class SimpleExecutor extends BaseExecutor {
    
    public SimpleExecutor(Configuration configuration, Transaction transaction) {
        super(configuration, transaction);
    }
    
    @Override
    public <T> T query(MappedStatement ms, Object parameter) {
        
        // 拿到我们的SQL
        BoundSql boundSql = ms.getBoundSql(parameter);
        
        Connection connection = null;
        
        // FIXME
        String statement = boundSql.getSql();
        
        PreparedStatement preparedStatement = null;

        Test test = null;
        
        try {
            
            connection = transaction.getConnection();
            
//            preparedStatement = connection.prepareStatement(String.format(statement, Integer.parseInt(parameter.toString())));
            preparedStatement = connection.prepareStatement(statement);
            
            if (parameter instanceof HashMap) {
                
                HashMap temp = (HashMap) parameter;
                int size = temp.size() / 2;
                
                for (int i = 1; i <= size; i++) {
                    preparedStatement.setObject(i, temp.get("param" + i));
                }
            } else {
                preparedStatement.setObject(1, parameter);
            }
            
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                test = new Test();
                test.setId(rs.getInt(1));
                test.setNums(2);
                test.setName(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != preparedStatement) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return (T)test;
    }

}
