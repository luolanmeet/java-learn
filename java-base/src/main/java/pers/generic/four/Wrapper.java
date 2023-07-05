package pers.generic.four;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @auther ken.ck
 * @date 2023/7/5 20:48
 */
public class Wrapper<T> {

    /**
     * 创建匿名派生类的实例，配合反射API，先获取superClass的泛型信息，
     * 如果是ParameterizedType，就尝试获取真实的Type Argument信息，就可以获取T的运行时类型了。
     *
     * 利用了JSR1414中，对类中的泛型信息，保存到类签名（Signature）的一个技巧。
     * Java的编译器将泛型信息写入到ClassFile的Signature属性中。然后通过JRE的反射接口解析Signature中的字符串。
     * 最终拿到被隐藏的运行时类型信息。
     * https://jcp.org/aboutJava/communityprocess/review/jsr014/index.html?spm=ata.21736010.0.0.7a50736e8HduZC
     *
     * @param wrapper
     * @return
     * @param <T>
     */
    public static <T> Type getGenericRuntimeType(Wrapper<T> wrapper) {
        Type type = wrapper.getClass().getGenericSuperclass();
        if (type == null) {
            return null;
        }
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType)type).getActualTypeArguments();
            return types[0];
        }
        return null;
    }

}
