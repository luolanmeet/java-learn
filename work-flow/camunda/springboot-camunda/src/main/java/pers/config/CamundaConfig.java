package pers.config;

import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther ken.ck
 * @date 2023/10/28 13:47
 */
@Configuration
public class CamundaConfig {

    @Value("${camunda.server.url:http://localhost:8080/engine-rest}")
    private String serverUrl;

    @Bean
    public ExternalTaskClient externalTaskClient() {
        return ExternalTaskClient.create().baseUrl(serverUrl).build();
    }

}
