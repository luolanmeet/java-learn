package pers.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pers.bean.User;
import pers.mapper.IUserMapper;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource(name = "IUserMapper")
    private IUserMapper userMasterMapper;

    @Resource(name = "slaveIUserMapper")
    private IUserMapper userSlaveMapper;

    public void getById(Integer userId) {
        // 使用从库读取数据
        System.out.println(userSlaveMapper.findById(userId));
    }

    public void addUser() {
        // 使用主库写入数据
        User user = new User();
        user.setName("cck");
        user.setAddress("china");
        userMasterMapper.save(user);
    }

}
