package pers;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;

import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Nacos 做配置中心
 * @author cck
 */
public class ConfigServiceDemo {
    
    public static void main(String[] args) throws Exception {

        // 构造连接参数
        String serverAddr = "localhost"; // nacos 服务器地址，默认端口8848
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        // 获取一个配置服务实例
        ConfigService configService = NacosFactory.createConfigService(properties);

        // 获取配置
        String dataId = "database"; // 数据id
        String group = "DEFAULT_GROUP"; // 组号
        String content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);
        
        // 增加 Nacos 动态监听，配置变化时，会接到 Nacos 的变更推送
        configService.addListener(dataId, group, new Listener() {
            
            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("recieve:" + configInfo);
            }
        
            @Override
            public Executor getExecutor() {
                return null;
            }
            
        });
    
        // 发布配置，原来的配置信息如果存在则会覆盖，否则自动创建
        boolean isPublishOk = configService.publishConfig(dataId, group, "content");
        System.out.println(isPublishOk);
    
        // 打印配置信息
        Thread.sleep(3000);
        content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);
    
        // 删除配置
        boolean isRemoveOk = configService.removeConfig(dataId, group);
        System.out.println(isRemoveOk);
        Thread.sleep(3000);

        // 打印配置信息
        content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);
        Thread.sleep(300000);
    }
    
}
