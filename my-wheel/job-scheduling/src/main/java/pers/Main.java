package pers;

import java.io.IOException;

import org.junit.Test;

import pers.actuator.JobActuator;
import pers.delay.DesignatDTGenerator;
import pers.delay.FixedRateDTGenerator;
import pers.job.BaseJob;
import pers.job.DelayTimeJob;

public class Main {

    // 实际要执行的任务
    BaseJob task = new BaseJob() {
        
        int runTime = 1;
        
        @Override
        public void run() {
            
            System.out.println("hello world");

            // 如果执行了15次，则退出执行。这里模拟了业务退出的逻辑
            if (++runTime > 15) {
                this.setExit(true);
            }
        }
    };
    
    /**
     * 测试按照固定时间间隔执行某个任务
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {
        
        // 新建一个执行器
        JobActuator actuator = new JobActuator(); 
        
        // 新建一个产生固定时间的延迟时间生成器
        FixedRateDTGenerator fixedRateDTGenerator = new FixedRateDTGenerator(3000);
        
        // 新建一个任务
        DelayTimeJob delayTimeJob = new DelayTimeJob(task, fixedRateDTGenerator);

        // 开始执行任务
        actuator.addJob(delayTimeJob);
        
        System.in.read();
    }
    
    /**
     * 测试按照指定时间隔执行某个任务
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        
        // 新建一个产生指定时间的延迟时间生成器
        DesignatDTGenerator designatDTGenerator = new DesignatDTGenerator();
        
        // 设置时间间隔
        designatDTGenerator.addDelayTime(1_000) // 1秒后执行
                           .addDelayTime(2_000) // 2秒后执行
                           .addDelayTime(4_000) // 4秒后执行
                           .addDelayTime(15_000) // 15秒后执行
                           .addDelayTime(40_000) // 40秒后执行
                           .addDelayTime(180_000) // 3分钟后执行
                           .addDelayTime(180_000) // 3分钟后执行
                           .addDelayTime(360_000) // 6分钟后执行
                           .addDelayTime(3_600_000); // 1小时后执行
        
        // 新建一个任务
        DelayTimeJob delayTimeJob = new DelayTimeJob(task, designatDTGenerator);
        
        // 新建一个执行器
        JobActuator actuator = new JobActuator(); 
        
        // 开始执行任务
        actuator.addJob(delayTimeJob);
        
        System.in.read();
    }
    
}
