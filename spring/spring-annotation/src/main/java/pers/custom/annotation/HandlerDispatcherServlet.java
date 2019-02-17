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
public class HandlerDispatcherServlet implements 
    InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private Map<Integer, ICmdHandler> handlers = new HashMap<>();
    
    public void handle(int cmd) {
        handlers.get(cmd).handle();
    }
    
    public void afterPropertiesSet() {
        
        String[] beanNames = this.applicationContext.getBeanNamesForType(Object.class);

        for (String beanName : beanNames) {
            if (!ScopedProxyUtils.isScopedTarget(beanName)) {
                Class<?> beanType = null;
                beanType = this.applicationContext.getType(beanName);
                if (beanType != null) {
                    CmdInMapping annotation = AnnotatedElementUtils.findMergedAnnotation(beanType, CmdInMapping.class);
                    if(annotation != null) {
                        handlers.put(annotation.value(), (ICmdHandler) applicationContext.getBean(beanType));
                    }
                }
            }
        }
        
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
