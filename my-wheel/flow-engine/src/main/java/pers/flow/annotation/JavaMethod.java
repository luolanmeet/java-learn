package pers.flow.annotation;

import java.lang.annotation.*;

/**
 * 声明 Java方法 逻辑提供类
 * @auther ken.ck
 * @date 2021/6/19 16:37
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JavaMethod {

    String value();

}
