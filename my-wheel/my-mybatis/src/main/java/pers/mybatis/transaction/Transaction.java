package pers.mybatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 对数据库连接的包装
 * @author cck
 */
public interface Transaction {
    
    Connection getConnection() throws SQLException;
    
}
