package pers.job;

import com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 六一信息科技
 *
 * @author chenken
 * @date 2020/4/27 20:07
 */
public class JobActuator extends TimerTask {

    private int index = 0;
    private Executor executor;
    private final Set<Job>[] circleQueue;

    public JobActuator() {
        circleQueue = new CopyOnWriteArraySet[3600];
        for (int i = 0; i < 3600; i++) {
            circleQueue[i] = new CopyOnWriteArraySet<Job>();
        }
        executor = Executors.newFixedThreadPool(10);

        // 每秒执行一次
        new Timer().scheduleAtFixedRate(this, 1000L, 1000L);
    }

    public void addJob(Job job, long delay) {

        // 计算时间应该在哪个slot
        long second = delay / 1000;
        second += index - 1;
        job.setCycleNum(second / 3600L);
        circleQueue[(int) (second % 3600)].add(job);
    }

    @Override
    public void run() {

        Set<Job> jobHashSet = circleQueue[index++];
        index %= circleQueue.length;

        // set是线程不安全的
        jobHashSet.forEach(job -> {

            if (job.cycleNum != 0) {
                job.reduceCycleNum();
                return ;
            }

            jobHashSet.remove(job);
            executor.execute(job);
        });

    }

    public static void main(String[] args) throws InterruptedException {

        JobActuator jobActuator = new JobActuator();

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        for (int i = 0; i < 100; i += 4) {

            Job job = new Job() {
                @Override
                public void run() {
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    System.out.println(format.format(new Date()));
                }
            };

            // 中途加入的任务，延迟应该从当前时间算起
            TimeUnit.SECONDS.sleep(5);
            System.out.println("add job " + format.format(new Date()));
            jobActuator.addJob(job,4000);
        }

    }

}
