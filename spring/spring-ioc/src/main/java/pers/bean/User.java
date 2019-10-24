package pers.bean;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    
    private int age;
    
    private String name;

}
