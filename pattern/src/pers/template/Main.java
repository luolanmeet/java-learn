package pers.template;

import pers.template.dao.UserDao;
import pers.template.entity.User;

import javax.sql.DataSource;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        DataSource dataSource = null;
        UserDao userDao = new UserDao(dataSource);
        List<User> users = userDao.selectAll();
        System.out.println(users);
    }

}
