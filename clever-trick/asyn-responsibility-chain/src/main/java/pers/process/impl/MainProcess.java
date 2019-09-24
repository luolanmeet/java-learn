package pers.process.impl;

import pers.process.AbstractProcess;

public class MainProcess extends AbstractProcess {
    
    @Override
    public void doProcess(String msg) {
        
        System.out.println("handle request : " + msg);
        
    }
    
}
