package pers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pers.bean.User;
import pers.mapper.IUserMapper;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    @Qualifier("IUserMapper")
    private IUserMapper userMasterMapper;

    @Resource
    @Qualifier("slaveIUserMapper")
    private IUserMapper userSlaveMapper;

    public void getById(Integer userId) {
        System.out.println(userSlaveMapper.findById(userId));
    }

    public void addUser() {
        User user = new User();
        user.setName("cck");
        user.setAddress("china");
        userMasterMapper.save(user);
    }

}
