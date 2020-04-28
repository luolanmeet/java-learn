package pers.job;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * @author cck
 * @date 2020/4/27 20:07
 */
public class JobActuator extends TimerTask {

    private int index = -1;
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
        second += index;
        job.setCycleNum(second / 3600L);
        circleQueue[(int) (second % 3600)].add(job);
    }

    @Override
    public void run() {

        Set<Job> jobHashSet = circleQueue[++index];
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

}
