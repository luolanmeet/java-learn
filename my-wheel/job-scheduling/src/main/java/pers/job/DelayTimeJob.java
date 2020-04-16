package pers.job;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import lombok.Setter;
import pers.delay.IDelayTimeGenerator;

/**
 * 主要处理是任务是否重行入队的逻辑
 * 还有提供Delayed接口的方法实现
 * @author cck
 */
public class DelayTimeJob implements Runnable, Delayed {
    
    /** 执行器的任务队列，用于任务重新入队 */
    @Setter
    private DelayQueue<DelayTimeJob> jobs;

    /** 延迟时间生成器 */
    IDelayTimeGenerator delayTimeGenerator;
    
    /** 具体要执行的任务 */
    private BaseJob realJob;
    
    private long time = 0L;
    
    public DelayTimeJob(BaseJob baseJob, IDelayTimeGenerator delayTimeGenerator) {
        
        this.realJob = baseJob;
        this.delayTimeGenerator = delayTimeGenerator;
        
        Integer delayTime = delayTimeGenerator.getDelayTime();
        if (delayTime == null) {
            return ;
        }
        
        this.time = delayTime + System.currentTimeMillis();
    }
    
    @Override
    public void run() {
        
        realJob.run();
        
        // 任务主动退出
        if (realJob.isExit) {
            return ;
        }
        
        Integer delayTime = delayTimeGenerator.getDelayTime();
        
        // 执行的时间结束， 则任务不再执行
        if (delayTime == null) {
            return ;
        }
        
        // 重新入队
        time = System.currentTimeMillis() + delayTime;
        jobs.offer(this);
        return ;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.time - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        
        DelayTimeJob other = (DelayTimeJob) o;  
        long diff = time - other.time;  
        
        if (diff > 0) {  
            return 1;  
        } 
        if (diff < 0) {  
            return -1;  
        }
        return 0;
    }
    
}
