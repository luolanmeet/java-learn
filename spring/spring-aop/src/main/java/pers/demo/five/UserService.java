package pers.demo.five;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class UserService {

    private JdbcTemplate jdbcTemplate;
    
    private final static String INSERT_SQL = "INSERT INTO t_user(name) VALUES('cck')";
    
    public void saveOne() {
        System.out.println("使用tx标签");
        save();
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveTwo() {
        System.out.println("使用事务注解");
        save();
    }
    
    private void save() {
        jdbcTemplate.execute(INSERT_SQL);
        if (Math.random() > 0.8) {
            System.out.println("数据库数据插入失败");
            throw new RuntimeException("meet exception");
        }
        System.out.println("数据库数据插入成功");
    }
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
