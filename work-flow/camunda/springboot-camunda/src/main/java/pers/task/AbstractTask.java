package pers.task;

import jakarta.annotation.PostConstruct;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @auther ken.ck
 * @date 2023/10/28 13:51
 */
public abstract class AbstractTask implements ExternalTaskHandler {

    @Autowired
    private ExternalTaskClient externalTaskClient;

    @PostConstruct
    public void subscribe() {
        externalTaskClient
                .subscribe(getTopicName())
                .processDefinitionKey(getProcessDefinitionKey())
                .handler(this)
                .open();
    }

    /**
     * 获取订阅的主题
     * @return
     */
    public abstract String getTopicName();

    /**
     * 获取流程key
     * @return
     */
    public abstract String getProcessDefinitionKey();

}
