package pers.delay;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 指定时间的时间生成器
 * @author cck
 */
public class DesignatDTGenerator implements IDelayTimeGenerator {

    private final Deque<Integer> delayTimeQueue = new ArrayDeque<>();
    
    /**
     * 添加延迟时间
     * @param delayTime
     */
    public DesignatDTGenerator addDelayTime(Integer delayTime) {
        delayTimeQueue.offer(delayTime);
        return this;
    }
    
    @Override
    public Integer getDelayTime() {
        return delayTimeQueue.poll();
    }

}
