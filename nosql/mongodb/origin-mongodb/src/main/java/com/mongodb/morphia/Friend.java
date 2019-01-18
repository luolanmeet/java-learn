package com.mongodb.morphia;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("friends")
public class Friend {
    
    @Id
    private ObjectId id;
    
    private String name;
    
    public Friend() {
    }
    
    public Friend(String name) {
        this.name = name;
    }
    
    public Friend(ObjectId id, String name) {
        this.id = id;
        this.name = name;
    }
    
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

    @Override
    public String toString() {
        return "Friend [id=" + id + ", name=" + name + "]";
    }
    
}
