package pers;

import pers.process.IProcess;
import pers.process.ProcessChain;
import pers.process.impl.MainProcess;
import pers.process.impl.PreProcess;

public class Main {
    
    public static void main(String[] args) throws Exception {
    
        // 构造责任链
        ProcessChain chain = new ProcessChain()
                .addChain(new PreProcess())
                .addChain(new MainProcess());
    
        // 开启线程
        IProcess process = chain.start();
        
        // 处理请求
        process.process("ck");
        process.process("cck");
    
        System.out.println("main thread do work");
        Thread.sleep(5000);
        
        // 关闭线程
        chain.stop();
    }
    
}
