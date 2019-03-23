package pers.mybatis.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import pers.mybatis.binding.MapperMethod.ParamMap;
import pers.mybatis.session.Configuration;

/**
 * 入参的名字解析器
 * @author cck
 */
public class ParamNameResolver {
    
    /** 默认的参数名字前缀 */
    private static final String GENERIC_NAME_PREFIX = "param";
    
    /** 存储方法的参数名字映射 */
    private final SortedMap<Integer, String> names;
    
    public ParamNameResolver(Configuration config, Method method) {
        
        // 拿到方法的参数类型
        final Class<?>[] paramTypes = method.getParameterTypes();
        // 拿到方法参数的注解，比如注解了 @Param
        final Annotation[][] paramAnnotations = method.getParameterAnnotations();
        final SortedMap<Integer, String> map = new TreeMap<Integer, String>();
        
        int paramCount = paramAnnotations.length;
        
        for (int paramIndex = 0; paramIndex < paramCount; paramIndex++) {
            map.put(paramIndex, "arg" + paramIndex);
        }
        
        // 返回指定不可修改的的Map
        names = Collections.unmodifiableSortedMap(map);
    }
    
    /**
     * 没有入参时返回空
     * 只有一个入参时，直接返回
     * 有多个入参时，将入参封装进Map中
     * @param args
     * @return
     */
    public Object getNamedParams(Object[] args) {
        
        final int paramCount = names.size();
        
        if (args == null || paramCount == 0) {
            return null;
        } else if (paramCount == 1) {
            return args[names.firstKey()];
        } else {
            
            final Map<String, Object> param = new ParamMap<Object>();
            int i = 0;
            
            for (Map.Entry<Integer, String> entry : names.entrySet()) {
                
                // 把指定的名字放入Map
                param.put(entry.getValue(), args[entry.getKey()]);
                
                // 把通用的名字放入到Map
                final String genericParamName = GENERIC_NAME_PREFIX + String.valueOf(i + 1);
                if (!names.containsValue(genericParamName)) {
                    param.put(genericParamName, args[entry.getKey()]);
                }
                i++;
            }
            
            return param;
        }
        
    }
    
}
