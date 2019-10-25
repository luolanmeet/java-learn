package pers.custom.annotation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

import pers.custom.annotation.handler.ICmdHandler;

@Component
public class HandlerDispatcherServlet implements InitializingBean, ApplicationContextAware {

    private ApplicationContext context;

    private Map<Integer, ICmdHandler> handlers = new HashMap<>();
    
    public void handle(int cmd) {
        handlers.get(cmd).handle();
    }
    
    public void afterPropertiesSet() {
        
        // 获取所有的beanName
        String[] beanNames = this.context.getBeanNamesForType(Object.class);

        for (String beanName : beanNames) {
            
            if (ScopedProxyUtils.isScopedTarget(beanName)) {
                continue;
            }
            
            // 通过beanName获取Bean类型
            Class<?> beanType = this.context.getType(beanName);
            
            if (beanType != null) {
    
                // 看是否带有CmdMapping注解
                CmdMapping annotation = AnnotatedElementUtils.findMergedAnnotation(
                        beanType, CmdMapping.class);
                
                if(annotation != null) {
                    // 获取bena，在handlers中注册
                    handlers.put(annotation.value(), (ICmdHandler) context.getBean(beanType));
                }
            }
        }
        
    }
    
    /**
     * 拿到Spring 上下文对象
     */
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        
        this.context = applicationContext;
    }

}
