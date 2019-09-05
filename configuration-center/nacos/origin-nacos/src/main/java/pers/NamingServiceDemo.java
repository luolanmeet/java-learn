package pers;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;

/**
 * Nacos 做服务发现
 * @author cck
 */
public class NamingServiceDemo {
    
    public static void main(String[] args) throws NacosException {
        
        NamingService naming = NacosFactory.createNamingService("localhost");

        // 注册一个服务实例（服务名,服务实例IP,	服务实例port,集群名）
        naming.registerInstance("nacos.test.3", "11.11.11.11", 8888, "TEST1");
    
    }
    
}
