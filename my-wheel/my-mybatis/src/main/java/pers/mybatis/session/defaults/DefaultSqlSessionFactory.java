package pers.mybatis.session.defaults;

import javax.sql.DataSource;

import pers.mybatis.datasource.unpooled.UnpooledDataSourceFactory;
import pers.mybatis.executor.Executor;
import pers.mybatis.session.Configuration;
import pers.mybatis.session.SqlSession;
import pers.mybatis.session.SqlSessionFactory;
import pers.mybatis.transaction.Transaction;
import pers.mybatis.transaction.TransactionFactory;
import pers.mybatis.transaction.jdbc.JdbcTransactionFactory;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    // 构建一个SqlSession
    // 主要是放入配置文件、Executor

    @Override
    public SqlSession openSession() {
        
        
        // 这里直接就使用了没有连接池的数据源
        UnpooledDataSourceFactory unpooledDataSourceFactory = new UnpooledDataSourceFactory(); 
        DataSource dataSource = unpooledDataSourceFactory.getDataSource();
        
        // 构造Transaction（封装了数据库连接）
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Transaction transaction = transactionFactory.newTransaction(dataSource, null, false);
        
        Executor executor = configuration.newExecutor(transaction);
        return new DefaultSqlSession(configuration, executor);
    }
}
