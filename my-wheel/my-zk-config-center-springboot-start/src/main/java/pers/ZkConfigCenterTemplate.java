package pers;

import pers.autoconfiguration.ZkConfigCenterProperties;
import pers.center.ZkConfigCenter;

/**
 * 
 * @author cck
 */
public class ZkConfigCenterTemplate {
    
    private ZkConfigCenter zkConfigCenter;
    
    public ZkConfigCenterTemplate(ZkConfigCenterProperties properties) {
    
        String address = new StringBuilder()
                .append(properties.getAddress())
                .append(":")
                .append(properties.getPort())
                .toString();
        
        zkConfigCenter = new ZkConfigCenter(address);
    }
    
    public <T> T getConfig(String configName, Class<T> clazz) {
        return zkConfigCenter.getConfig(configName, clazz);
    }
    
}
