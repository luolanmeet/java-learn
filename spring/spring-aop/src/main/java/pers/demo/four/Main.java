package pers.demo.four;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

/**
 * SpringAOP 事务管理
 * 编程式事务
 */
public class Main {
    
    public static void main(String[] args) {
    
        // 初始化jdbcTemplate
        DataSource dataSource = getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    
        // 创建事务管理器
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(dataSource);
        
        // 定义事务属性
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        // 开启事务
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        String insertSql = "INSERT INTO t_user(name) VALUES('cck')";

        try {
            jdbcTemplate.execute(insertSql);
            txManager.commit(txStatus);
        } catch (Exception e) {
            e.printStackTrace();
            txManager.rollback(txStatus);
        }
    
    }
    
    private static DataSource getDataSource() {
    
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/db_user?useSSL=false");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("admin");
        dataSource.setPassword("1234567");
        
        return dataSource;
    }
    
}
