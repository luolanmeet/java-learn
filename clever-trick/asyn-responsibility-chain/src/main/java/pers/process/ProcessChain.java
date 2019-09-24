package pers.process;

import java.util.ArrayList;
import java.util.List;

/**
 * 构造责任链的工具
 */
public class ProcessChain {
    
    public AbstractProcess firstProcess = null;
    
    List<AbstractProcess> processs = new ArrayList<>();
    
    public ProcessChain addChain(AbstractProcess process) {
        processs.add(process);
        return this;
    }

    // 设置调用责任链
    public IProcess start() {
    
        if (processs.isEmpty()) {
            throw new RuntimeException("chain is empty");
        }
    
        for (int i = 1; i < processs.size(); i++) {
            processs.get(i - 1).setNext(processs.get(i));
        }
    
        // 启动所有线程
        processs.forEach(Thread::start);
    
        firstProcess = processs.get(0);
        return firstProcess;
    }

    // 关闭线程
    public void stop() {
        processs.forEach(AbstractProcess::shutdown);
    }
    
}
