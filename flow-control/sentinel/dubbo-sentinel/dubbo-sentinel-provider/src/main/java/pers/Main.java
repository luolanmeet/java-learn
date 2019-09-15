package pers;

import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws InterruptedException {

        // 表示当前的节点是集群客户端
        ClusterStateManager.applyState(ClusterStateManager.CLUSTER_CLIENT);

        new SpringApplicationBuilder()
                .web(WebApplicationType.NONE)
                .sources(Main.class)
                .run(args);

        new CountDownLatch(1).await();
    }

}
