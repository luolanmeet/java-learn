package com.mongodb;

import java.util.Arrays;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 *       mysql          mongodb
 *      ========================
 *      database  |  database
 *      table     |  collection
 *      row       |  document
 * 
 * 简单的 CRUD api
 * http://mongodb.github.io/mongo-java-driver/3.9/driver/getting-started/quick-start/
 * @author Ryan
 */
public class CrudDemo {

    public static void main(String[] args) {

        // 连接mongodb
        MongoClient mongoClient = MongoClients.create("mongodb://119.29.240.120:27017");
        
        // 获取数据库对象
        MongoDatabase db = mongoClient.getDatabase("cck");
        // 获取集合对象
        MongoCollection<Document> coll = db.getCollection("users");
        
        Document document = new Document("name", "cck")
                .append("age", 24)
                .append("hobby", Arrays.asList("codeing", "reading"));
        
        /**
         * 新增
         */
        coll.insertOne(document);
        
        /**
         * 查找
         */
        // 计数
        System.out.println(coll.countDocuments());
        // 查第一个
        document = coll.find().first();
        System.out.println(document);
        String str = document.toJson();
        System.out.println(str);
        
        // 查全部
        MongoCursor<Document> cursor = coll.find().iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
        
        // 带条件查询
        document = coll.find(Filters.eq("name", "cck")).first();
        System.out.println(document);
        
        /** 
         * 修改 
         */
        coll.updateOne(Filters.eq("name", "cck"), new Document("$set", new Document("name", "ck")));
        document = coll.find(Filters.eq("name", "ck")).first();
        System.out.println(document);
        
        /** 
         * 删除 
         */
        coll.deleteOne(Filters.eq("name", "ck"));
        document = coll.find(Filters.eq("name", "ck")).first();
        System.out.println(document);
    }

}
