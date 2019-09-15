package pers;

import com.alibaba.csp.sentinel.cluster.server.SentinelDefaultTokenServer;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;

import java.util.Collections;

public class ClusterServer {

    private final static String APP_NAME = "DUBBO-SENTINEL-DEMO";

    public static void main(String[] args) throws Exception {

        SentinelDefaultTokenServer tokenServer = new SentinelDefaultTokenServer();
        ClusterServerConfigManager.loadGlobalTransportConfig(
                new ServerTransportConfig().setIdleSeconds(600).setPort(9999)
        );

        ClusterServerConfigManager.loadServerNamespaceSet(Collections.singleton(APP_NAME));
        tokenServer.start();
    }

}
