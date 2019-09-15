package pers.config;

import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

/**
 * 从nacod上获取动态的限流规则
 */
public class NacosDataSourceInitFunc implements InitFunc {

    // token-server的地址
    private final String CLUSTER_SERVER_HOST = "localhost";
    private final int CLUSTER_SERVER_PORT = 9999;
    private final int CLUSTER_TIME_OUT = 20000;

    private final String APP_NAME = "DUBBO-SENTINEL-DEMO";

    // nacos的配置
    private final String remoteAddress = "localhost";
    private final String groupId = "SENTINEL_GROUP";
    private final String FLOW_POSTFIX = "-flow-rules";

    @Override
    public void init() throws Exception {
        loadClusterClientConfig();
        registryClusterFlowRuleProperty();
    }

    // 加载集群-信息
    private void loadClusterClientConfig() {

        ClusterClientAssignConfig assignConfig = new ClusterClientAssignConfig();
        assignConfig.setServerHost(CLUSTER_SERVER_HOST);
        assignConfig.setServerPort(CLUSTER_SERVER_PORT);
        ClusterClientConfigManager.applyNewAssignConfig(assignConfig);

        ClusterClientConfig clusterConfig = new ClusterClientConfig();
        clusterConfig.setRequestTimeout(CLUSTER_TIME_OUT);
        ClusterClientConfigManager.applyNewConfig(clusterConfig);
    }

    // 注册动态数据源，如果token-server挂了，会直接使用nacos上的限流规则
    // 此时会由原来的集群限流变成单机限流
    private void registryClusterFlowRuleProperty() {

        NacosDataSource<List<FlowRule>> rds = new NacosDataSource<>(
                remoteAddress, groupId, APP_NAME + FLOW_POSTFIX,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>(){}));

        FlowRuleManager.register2Property(rds.getProperty());
    }

}
