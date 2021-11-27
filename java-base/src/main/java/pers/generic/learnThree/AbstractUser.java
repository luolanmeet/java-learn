package pers.generic.learnThree;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @auther ken.ck
 * @date 2021/9/11 22:26
 */
public abstract class AbstractUser<T> implements IUser<T> {

    @Override
    public void callback(String jsonStr) {
        // 序列化对象，可以保持实现类中，始终使用dto做业务逻辑
//        doCallback(JSON.parseObject(jsonStr, getModelClass()));
    }

    protected abstract void doCallback(T t);

    /**
     * 获取子类泛型类型
     * @return
     */
    private Type getModelClass() {
        Type type = this.getClass().getGenericSuperclass();
        return ((ParameterizedType) type).getActualTypeArguments()[0];
    }

}
