package pers.job;

import lombok.Setter;

/**
 *
 * @author cck
 * @date 2020/4/27 20:02
 */
public abstract class Job implements Runnable {

    /**
     * 一次队列循环是1小时
     */
    @Setter
    long cycleNum = 0L;

    public long reduceCycleNum() {
        return -- this.cycleNum;
    }

}
