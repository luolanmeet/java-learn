package pers.process.impl;

import pers.process.AbstractProcess;

public class PreProcess extends AbstractProcess {
    
    @Override
    public void doProcess(String msg) {
        
        System.out.println("check request : " + msg);
        
        // 这里可以决定是否将请求交给下一个处理对象
        next(msg);
    }
    
}
