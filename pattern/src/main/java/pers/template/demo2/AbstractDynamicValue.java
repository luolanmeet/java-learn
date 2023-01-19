package pers.template.demo2;

import pers.template.demo2.ignreo.JSON;
import pers.template.demo2.ignreo.ValueOperations;

import javax.annotation.Resource;

/**
 * @auther ken.ck
 * @date 2021/7/17 09:31
 */
public abstract class AbstractDynamicValue<I, V>  {

    /**
     * 日常注入redis客户端的bean
     */
    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> valueOperations;

    /** 设置值 */
    public void set(I id, V value) {
        valueOperations.set(getKey(id), JSON.toJSONString(value));
    }

    /** 获取值 */
    public V get(I id) {
        return JSON.parseObject(valueOperations.get(getKey(id)), getValueClass());
    }

    /** 获取主键 */
    protected abstract String getKey(I id);

    /** 获取值类 */
    protected abstract Class<V> getValueClass();

}
