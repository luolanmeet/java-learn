package pers.delay;

/**
 * 延迟时间生成器接口，返回延迟的时间
 * @author cck
 */
public interface IDelayTimeGenerator {
    
    /** 返回延迟的时间，单位：毫秒 */
    Integer getDelayTime();
    
}
