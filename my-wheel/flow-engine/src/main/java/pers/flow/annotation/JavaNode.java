package pers.flow.annotation;

import java.lang.annotation.*;

/**
 * 声明 Java节点 逻辑提供类
 * @auther ken.ck
 * @date 2021/6/19 16:37
 */
@Documented
@Target(ElementType.TYPE_PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface JavaNode {

    String value();

}
