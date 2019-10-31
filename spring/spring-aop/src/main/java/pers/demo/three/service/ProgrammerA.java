package pers.demo.three.service;

import org.springframework.stereotype.Service;

import pers.demo.three.annotation.WorkLog;

@WorkLog
@Service
public class ProgrammerA {
    
    public String writeCode(String language) {
        return "user programing language:" + language;
    }
    
    public void learn() {
        
    }
    
}
