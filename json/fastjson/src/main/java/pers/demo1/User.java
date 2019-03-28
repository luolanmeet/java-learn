package pers.demo1;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class User {
    
    @JSONField(name="id")
    private Integer userId;
    
    private String name;
    
    private List<String> hobby;

    @JSONField(format="yyyy-MM-dd")
    private Date birthday;
    
    public User() { }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    @Override
    public String toString() {
        return "User [userId=" + userId + ", name=" + name + ", hobby=" + hobby + "]";
    }
    
}
