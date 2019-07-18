package pers.delay;

/**
 * 固定间隔的时间生成器
 * @author cck
 */
public class FixedRateDTGenerator implements IDelayTimeGenerator {

    private Integer delayTime;
    
    public FixedRateDTGenerator(Integer delayTime) {
        this.delayTime = delayTime;
    }
    
    @Override
    public Integer getDelayTime() {
        return delayTime;
    }

}
