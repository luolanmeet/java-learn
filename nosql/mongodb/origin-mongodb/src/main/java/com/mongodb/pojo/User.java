package com.mongodb.pojo;

import java.util.List;

import org.bson.types.ObjectId;

public class User {
    
    private ObjectId id;
    
    private String name;
    
    private Integer age;
    
    private List<String> hobby;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }
    
}
