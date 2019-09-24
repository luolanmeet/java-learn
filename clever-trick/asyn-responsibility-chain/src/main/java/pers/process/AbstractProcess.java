package pers.process;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *  每个IProcess 都会开启一个线程，从队列中获取请求
 *  从而组成异步的责任链
 */
public abstract class AbstractProcess extends Thread implements IProcess {
    
    // 阻塞队列，存储请求
    protected LinkedBlockingQueue<String> requests = new LinkedBlockingQueue<>();
    
    // 持有下一个处理对象
    private IProcess nextProcess;
    
    // 控制线程退出
    public volatile boolean isFinish = false;
    
    // 对外提供关闭的方法
    public void shutdown(){
        isFinish = true;
    }
    
    // 抽象方法，放置业务逻辑
    public abstract void doProcess(String msg);
    
    // 将请求添加进阻塞队列
    @Override
    public void process(String msg) {
        this.requests.add(msg);
    }
    
    // 构建调用顺序
    @Override
    public void setNext(IProcess nextProcess) {
        this.nextProcess = nextProcess;
    }
    
    // 将请求交给下一个处理对象
    public void next(String msg) {
        if (nextProcess != null)
            nextProcess.process(msg);
    }
    
    // 从队列获取请求，5000ms超时
    private final static long TAKE_REQUEST_WAIT_TIME = 5000l;
    
    @Override
    public void run() {
    
        try {
            
            while (!isFinish) {

                // 不用take是因为take会一直阻塞，导致无法关闭线程
                String request = requests.poll(TAKE_REQUEST_WAIT_TIME, TimeUnit.MILLISECONDS);
                if (request != null) {
                    this.doProcess(request);
                }
            }
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
