package pers.actuator;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pers.job.DelayTimeJob;

/**
 * 任务执行器，不断从任务队列中获取任务，再交由线程池执行。
 * @author cck
 */
public class JobActuator extends Thread {

    /** 线程池 */
    ExecutorService es = Executors.newFixedThreadPool(2);
    
    /** 任务队列 */
    DelayQueue<DelayTimeJob> jobs = new DelayQueue<>();
    
    /** 构造方法，实例化时启动线程 */
    public JobActuator() {
        this.start();
    }
    
    public void addJob(DelayTimeJob job) {
        job.setJobs(jobs);
        jobs.offer(job);
    }
    
    @Override
    public void run() {
     
        while (true) {
            
            try {
                DelayTimeJob job = jobs.take();
                es.submit(job);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
