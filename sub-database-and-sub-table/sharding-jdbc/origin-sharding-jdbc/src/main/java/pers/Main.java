package pers;

import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Main {
    
    public static void main(String[] args) throws SQLException {
    
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        
        // 配置第一个数据源
        BasicDataSource dataSource1 = new BasicDataSource();
        dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://localhost:3306/db_shard_0");
        dataSource1.setUsername("admin");
        dataSource1.setPassword("1234567");
        // 配置第二个数据源
        BasicDataSource dataSource2 = new BasicDataSource();
        dataSource2.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource2.setUrl("jdbc:mysql://localhost:3306/db_shard_1");
        dataSource2.setUsername("admin");
        dataSource2.setPassword("1234567");
        
        dataSourceMap.put("ds0", dataSource1);
        dataSourceMap.put("ds1", dataSource2);
    
        // 配置t_order表规则
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();
        orderTableRuleConfig.setLogicTable("t_order");
        orderTableRuleConfig.setActualDataNodes("ds${0..1}.t_order_${0..2}");
        
        // 配置分库策略 行表达式策略InlineShardingStrategyConfiguration
        orderTableRuleConfig.setDatabaseShardingStrategyConfig(
                new InlineShardingStrategyConfiguration("order_id", "ds${order_id % 2}"));
        // 配置分表策略
        orderTableRuleConfig.setTableShardingStrategyConfig(
                new InlineShardingStrategyConfiguration("order_id", "t_order_${order_id % 3}"));
        
        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);
        
        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(
                dataSourceMap,
                shardingRuleConfig,
                new HashMap<>(),
                new Properties());
        
        String sql = "SELECT * FROM t_order WHERE order_id = ?";
    
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
             // 会落在 db_shard_1库 的 t_order_0表
            statement.setInt(1, 9);
            
            try (ResultSet rs = statement.executeQuery()) {
    
                while (rs.next()) {
                    System.out.println(rs.getInt(1));
                    System.out.println(rs.getInt(2));
                    System.out.println(rs.getDate(3));
                    System.out.println(rs.getInt(4));
                }
            }
        }
    
    }
    
}
