package pers.flow.util;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * spring 上下文工具类
 */
@Component("ApplicationUtil")
public class ApplicationUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ApplicationUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    public static <T> Collection<T> getBeans(Class<T> clazz) {

        Map<String, T> beanMaps = applicationContext.getBeansOfType(clazz);

        if (beanMaps != null && !beanMaps.isEmpty()) {
            return beanMaps.values();
        }

        return null;
    }

    public static <T> T getBeanByType(Class<T> clazz) {

        Map<String, T> beansOfType = applicationContext.getBeansOfType(clazz);
        if (beansOfType.isEmpty()) {
            return null;
        }

        if (beansOfType.size() == 1){
            return beansOfType.values().iterator().next();
        }

        // 返回clazz相同的
        for (Map.Entry<String, T> entry : beansOfType.entrySet()) {
            // 兼容AOP场景
            if (AopUtils.getTargetClass(entry.getValue()).equals(clazz)) {
                return entry.getValue();
            }
        }

        return null;
    }

    /**
     * <p>返回有指定annotationType的bean<p/>
     *
     * @param annotationType
     * @param <T>
     * @return key=bean，value=annotation
     */
    public static <T extends Annotation> Map<Object, T> getBeanByAnnotation(Class<T> annotationType) {

        Map<Object, T> map = new HashMap<>();
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(annotationType);
        for (Map.Entry<String, Object> entry : beansWithAnnotation.entrySet()) {
            T annotationOnBean = applicationContext.findAnnotationOnBean(entry.getKey(), annotationType);
            map.put(entry.getValue(), annotationOnBean);
        }
        return map;
    }

    /**
     * 获取当前 active profile
     * @return
     */
    public static String getActiveProfile() {

        if (applicationContext == null || applicationContext.getEnvironment() == null) {
            return null;
        }

        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        if (activeProfiles == null || activeProfiles.length == 0) {
            return null;
        }
        return activeProfiles[0];
    }

}
