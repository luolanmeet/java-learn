package pers.flow.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 声明 Java节点 逻辑提供类
 * @auther ken.ck
 * @date 2021/6/19 16:37
 */
@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JavaNode {

    String value();

}
