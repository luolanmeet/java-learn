package pers.template.dao;

import pers.template.entity.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

public class UserDao extends JdbcTemplate {

    public UserDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<User> selectAll() {

        String sql = "SELECT * FROM t_user";
        return (List<User>) super.executeQuery(sql, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws Exception {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                return user;
            }

        }, null);

    }

}
