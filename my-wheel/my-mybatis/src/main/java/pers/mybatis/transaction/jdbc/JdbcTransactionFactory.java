package pers.mybatis.transaction.jdbc;

import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import pers.mybatis.session.TransactionIsolationLevel;
import pers.mybatis.transaction.Transaction;
import pers.mybatis.transaction.TransactionFactory;

public class JdbcTransactionFactory implements TransactionFactory {
    
    @Override
    public void setProperties(Properties props) {
    }
    
    @Override
    public Transaction newTransaction(Connection conn) {
        return new JdbcTransaction(conn);
    }

    @Override
    public Transaction newTransaction(
            DataSource dataSource, 
            TransactionIsolationLevel level, 
            boolean autoCommit) {
        
        return new JdbcTransaction(dataSource, level, autoCommit);
    }

}
