package pers;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.core.ConfigConsts;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;

/**
 * apollo客户端demo
 * 启动的参数配上
 * -Dapp.id=SampleApp -Dapollo.meta=http://localhost:8080
 */
public class ApolloClientDemo {

    private final static String DEFAULT_VALUE = "none";
    private final static String KEY = "timeout";

    public static void main(String[] args) throws InterruptedException {

        // config instance is singleton for each namespace and is never null
        // 默认拿到application命名空间
//        Config config = ConfigService.getAppConfig();
        Config config = ConfigService.getConfig(ConfigConsts.NAMESPACE_APPLICATION);

        // 增加监听
        addListener(config);

        while (true) {
            Thread.sleep(4000);
            System.out.println(getConfig(config, KEY));
        }

    }

    // 增加数据变化监听
    private static void addListener(Config config) {

        ConfigChangeListener changeListener = new ConfigChangeListener() {
            @Override
            public void onChange(ConfigChangeEvent changeEvent) {
                for (String key : changeEvent.changedKeys()) {
                    ConfigChange change = changeEvent.getChange(key);
                    System.out.println("Change - key: " + change.getPropertyName());
                    System.out.println("oldValue: " + change.getOldValue());
                    System.out.println("newValue: " + change.getNewValue());
                    System.out.println("changeType: " + change.getChangeType());
                }
            }
        };
        config.addChangeListener(changeListener);
    }

    private static String getConfig(Config config, String someKey) {
        return config.getProperty(someKey, DEFAULT_VALUE);
    }

}
