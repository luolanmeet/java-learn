package com.mongodb.pojo;

import java.util.Arrays;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * http://mongodb.github.io/mongo-java-driver/3.9/driver/getting-started/quick-start-pojo/#prerequisites
 * @author cck
 */
public class PojoDemo {
    
    public static void main(String[] args) {
        
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        
        ConnectionString connectionString = new ConnectionString("mongodb://119.29.240.120:27017");
        
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .applyConnectionString(connectionString)
                .build();
        
        MongoClient mongoClient = MongoClients.create(settings);
        
        MongoDatabase db = mongoClient.getDatabase("cck2");
        MongoCollection<User> coll = db.getCollection("users", User.class);
        
        User user = new User();
        user.setName("cck");
        user.setAge(24);
        user.setHobby(Arrays.asList("codeing", "reading"));
        
        /**
         * 增加
         */
        coll.insertOne(user);
        
        /**
         * 查询
         */
        user = coll.find(Filters.eq("name", "cck")).first();
        System.out.println(user);
        
        /**
         * 修改
         */
        coll.updateOne(Filters.eq("name", "cck"), new Document("$set", new Document("name", "ck")));
        user = coll.find(Filters.eq("name", "ck")).first();
        System.out.println(user);
        
        /**
         * 删除
         */
        coll.deleteMany(Filters.eq("age", 24));
        System.out.println(coll.countDocuments());
        
    }
    
}
