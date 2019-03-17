package pers.mybatis.transaction.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import pers.mybatis.session.TransactionIsolationLevel;
import pers.mybatis.transaction.Transaction;

public class JdbcTransaction implements Transaction {
    
    protected Connection connection;
    
    protected DataSource dataSource;
    
    protected TransactionIsolationLevel level;
    
    protected boolean autoCommmit;
    
    public JdbcTransaction(Connection connection) {
        this.connection = connection;
    }
    
    public JdbcTransaction(DataSource ds, TransactionIsolationLevel desiredLevel, boolean desiredAutoCommit) {
        dataSource = ds;
        level = desiredLevel;
        autoCommmit = desiredAutoCommit;
    }
    
    @Override
    public Connection getConnection() throws SQLException {
        
        if (connection == null) {
            openConnectio1n();
        }
        
        return connection;
    }

    private void openConnectio1n() throws SQLException {
        connection = dataSource.getConnection();
        if (level != null) {
            connection.setTransactionIsolation(level.getLevel());
        }
    }

}
