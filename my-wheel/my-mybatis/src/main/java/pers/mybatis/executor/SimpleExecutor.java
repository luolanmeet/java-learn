package pers.mybatis.executor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

import pers.mybatis.mapping.BoundSql;
import pers.mybatis.mapping.MappedStatement;
import pers.mybatis.session.Configuration;
import pers.mybatis.transaction.Transaction;

public class SimpleExecutor extends BaseExecutor {
    
    public SimpleExecutor(Configuration configuration, Transaction transaction) {
        super(configuration, transaction);
    }
    
    @Override
    public <T> T query(MappedStatement ms, Object parameter) {
        
        // 拿到我们的SQL
        BoundSql boundSql = ms.getBoundSql(parameter);
        
        
        // FIXME
        String statement = boundSql.getSql();
        
        Object result = null;
        Connection connection = null;
        
        Class resultType = ms.getResultType();
        
        try {
            
            connection = transaction.getConnection();
            result = resultType.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try(PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            
            // 入参
            if (parameter instanceof HashMap) {
                
                HashMap<?, ?> temp = (HashMap<?, ?>) parameter;
                
                int size = temp.size() / 2;
                for (int i = 1; i <= size; i++) {
                    preparedStatement.setObject(i, temp.get("param" + i));
                }
            } else {
                preparedStatement.setObject(1, parameter);
            }
            
            ResultSet rs = preparedStatement.executeQuery();
            
            // 这里进行结果映射
            Map<String, Object> valMap = new HashMap<>();
            ResultSetMetaData metaData = rs.getMetaData();
            if (rs.next()) {
                
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    Object object = rs.getObject(i + 1);
                    valMap.put(columnLabel, object);
                }
            }
            
            // 为结果对象赋值
            for (Map.Entry<String, Object> entry : valMap.entrySet()) {
                
                String fieldName = entry.getKey();
                Object value = entry.getValue();
                Field field = resultType.getDeclaredField(fieldName);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                field.set(result, value);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } 

        return (T)result;
    }

}
