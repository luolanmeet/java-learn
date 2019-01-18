package com.mongodb.morphia;

import java.util.Arrays;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.mongodb.MongoClient;

/**
 * http://morphiaorg.github.io/morphia/1.3/getting-started/quick-tour/
 * @author cck
 */
public class MorphiaDemo {
    
    public static void main(String[] args) {
        
        /**
         * 创建Morphia实例
         * 扫描带@Entity注解的实体类
         */
        Morphia morphia = new Morphia();
        morphia.mapPackage("com.mongodb.morphia");
        
        /**
         * 创建MongoClient
         */
        MongoClient mongoClient = new MongoClient("119.29.240.120", 27017);
        Datastore ds = morphia.createDatastore(mongoClient, "cck3");
        
        /**
         * 增加
         */
        Friend friend = new Friend("ac");
        ds.save(friend);
        
        User user = new User();
        user.setName("cck");
        user.setAge(24);
        user.setHobby(Arrays.asList("codeing", "reading"));
        user.setFriends(Arrays.asList(friend));
        
        ds.save(user);
        
        /**
         * 查询
         */
        Query<User> query = ds.createQuery(User.class);
        
        List<User> asList = query.field("name").equal("cck").asList();
        System.out.println(asList);
        
        /**
         * 修改
         */
        UpdateOperations<User> operations = ds.createUpdateOperations(User.class)
                .set("name", "ck");
        ds.update(user, operations);
        asList = ds.createQuery(User.class).field("name").equal("ck").asList();
        System.out.println(asList);
        
        /**
         * 删除
         */
        Query<User> delQuery = ds.createQuery(User.class).field("age").greaterThan(18);
        ds.delete(delQuery);
        System.out.println(ds.getCount(User.class));
        System.out.println(ds.getCount(Friend.class)); // 不会随User document的删除而删除
        
    }
    
}
