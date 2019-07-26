package pers.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import pers.annotation.ConfigItem;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author cck
 */
@Slf4j
public class ConfigHolder {
    
    @Setter
    @Getter
    private Object config;
    
    @Setter
    @Getter
    private Class clazz;
    
    private String configName;
    
    private CuratorFramework curatorFramework;
    
    private Map<String, Field> feildPathMapper = new HashMap<>();
    
    public ConfigHolder(Class clazz,
                        String configName,
                        CuratorFramework curatorFramework) throws Exception {
        
        this.clazz = clazz;
        this.configName = configName;
        this.curatorFramework = curatorFramework;

        this.config = clazz.newInstance();
        
        // 解析字段
        parseField();
        // 设置字段
        setField();
        // 添加监听
        addListenerWithPathChildCache();
        
        log.info("configName[{}] config[{}]", configName, config);
    }
    
    public void addListenerWithPathChildCache() throws Exception {
        
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, configName, true);
        PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
    
                PathChildrenCacheEvent.Type eventType = event.getType();
    
                // 修改和删除和新增的监听需要处理
                if (eventType != PathChildrenCacheEvent.Type.CHILD_REMOVED
                        && eventType != PathChildrenCacheEvent.Type.CHILD_ADDED
                        && eventType != PathChildrenCacheEvent.Type.CHILD_UPDATED) {
                    return;
                }
    
                // 影响的字段
                String effectPath = event.getData().getPath();
                
                Field field = feildPathMapper.get(effectPath);
                if (field == null) {
                    return;
                }
    
                log.info("eventType[{}] effectPath[{}] before config[{}]", eventType, effectPath, config);
    
                switch (eventType) {
                    case CHILD_REMOVED:
                        field.set(config, null);
                        break;
                        
                    case CHILD_ADDED:
                    case CHILD_UPDATED:
                        byte[] bytes = event.getData().getData();
                        String valStr = new String(bytes);
                        Class<?> fieldType = field.getType();
    
                        if (Integer.class.equals(fieldType)) {
                            field.set(config, Integer.valueOf(valStr));
                        } else {
                            field.set(config, valStr);
                        }
                        break;
                }
                
                log.info("eventType[{}] effectPath[{}] after config[{}]", eventType, effectPath, config);
            }
        };
        
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
        pathChildrenCache.start(PathChildrenCache.StartMode.NORMAL);
    }
    
    private void setField() throws Exception {
        
        for (Map.Entry<String, Field> entry : feildPathMapper.entrySet()) {
            
            String childPath = entry.getKey();
            
            // 从zk获取配置
            byte[] bytes = curatorFramework.getData().forPath(childPath);
            
            String valStr = new String(bytes);
            Class<?> fieldType = entry.getValue().getType();
            
            if (Integer.class.equals(fieldType)) {
                entry.getValue().set(config, Integer.valueOf(valStr));
            } else {
                entry.getValue().set(config, valStr);
            }
        }
        
    }
    
    private void parseField() {
    
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            
            ConfigItem annotation = field.getAnnotation(ConfigItem.class);
            if (annotation == null) {
                continue;
            }
            // 设置属性可达a
            field.setAccessible(true);
            feildPathMapper.put(configName + "/" + annotation.value(), field);
        }
    }
    
    
    
}
