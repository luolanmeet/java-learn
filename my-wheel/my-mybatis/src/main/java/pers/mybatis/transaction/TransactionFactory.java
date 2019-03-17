package pers.mybatis.transaction;

import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import pers.mybatis.session.TransactionIsolationLevel;

public interface TransactionFactory {
    
    void setProperties(Properties props);

    Transaction newTransaction(Connection conn);
    
    Transaction newTransaction(
            DataSource dataSource, 
            TransactionIsolationLevel level, 
            boolean autoCommit);
    
}
