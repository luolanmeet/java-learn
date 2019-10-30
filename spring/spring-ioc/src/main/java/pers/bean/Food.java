package pers.bean;

import lombok.ToString;

@ToString
public class Food {

    private String name;
    
    public Food(String name) {
        this.name = name;
    }
    
}
