package pers;

import pers.job.Job;
import pers.job.JobActuator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author cck
 * @date 2020/4/28 19:55
 */
public class Main {

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
