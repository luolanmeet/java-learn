package pers.demo2;

import java.util.List;

import lombok.Data;

@Data
public class ClassA {
    
    private String name;
    
    private List<ClassB> cbs;
    
}
