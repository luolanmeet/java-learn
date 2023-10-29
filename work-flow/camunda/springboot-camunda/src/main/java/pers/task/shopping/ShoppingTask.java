package pers.task.shopping;

import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;
import pers.task.AbstractTask;

/**
 * @auther ken.ck
 * @date 2023/10/28 13:46
 */
@Component
public class ShoppingTask extends AbstractTask {

    @Override
    public String getTopicName() {
        return "shopping_car";
    }

    @Override
    public String getProcessDefinitionKey() {
        return "Process_shopping";
    }

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {

        System.out.println("购物车 " + externalTask);

        // 业务操作..

        // 设置变量
        VariableMap goodVariable = Variables.createVariables()
                .putValue("shopName", "滑板")
                .putValue("count", 2);
        // 完成任务
        externalTaskService.complete(externalTask, goodVariable);
    }
}
