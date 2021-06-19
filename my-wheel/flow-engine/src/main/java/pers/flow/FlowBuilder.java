package pers.flow;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import pers.flow.annotation.JavaMethod;
import pers.flow.annotation.JavaNode;
import pers.flow.node.AbstractFlowNode;
import pers.flow.util.ApplicationUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 流程构造器
 * @auther ken.ck
 * @date 2021/6/19 16:07
 */
@Service
public class FlowBuilder implements InitializingBean {

    /**
     * <JavaCode, Java对象>
     */
    private Map<String, Object> objectMap;

    /**
     * <Java对象, <方法Code, 方法对象>>
     */
    private Map<Object, Map<String, Method>> methodMap;

    /**
     * 构造流程图
     * @param flowMsg
     * @return
     */
    public AbstractFlowNode buildFlow(String flowMsg) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        objectMap = new HashMap<>();
        methodMap = new HashMap<>();

        Map<Object, JavaNode> beanByAnnotations = ApplicationUtil.getBeanByAnnotation(JavaNode.class);

        beanByAnnotations.forEach((k,v) -> {
            objectMap.put(v.value(), k);

            Map<String, Method> objectMethodMap = new HashMap<>();
            methodMap.put(k, objectMethodMap);

            Method[] methods = k.getClass().getDeclaredMethods();
            for (Method method : methods) {
                JavaMethod annotation = method.getAnnotation(JavaMethod.class);
                if (annotation == null) {
                    continue;
                }
                objectMethodMap.put(annotation.value(), method);
            }
        });
    }

}
