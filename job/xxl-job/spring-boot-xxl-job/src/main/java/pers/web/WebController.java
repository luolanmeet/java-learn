package pers.web;

import com.xxl.job.core.biz.ExecutorBiz;
import com.xxl.job.core.biz.client.ExecutorBizClient;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.biz.model.TriggerParam;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class WebController {

    @Value("${xxl.job.api.call.host}")
    private String addressUrl;

    @Value("${xxl.job.accessToken}")
    private String accessToken;

    @RequestMapping("/run")
    @ResponseBody
    public String run() {

        ExecutorBiz executorBiz = new ExecutorBizClient(addressUrl, accessToken);

        // trigger data
        TriggerParam triggerParam = new TriggerParam();
        // 要做成配置
        triggerParam.setJobId(1); // 实验过 可不传
        triggerParam.setExecutorHandler("demoJobHandler");

        triggerParam.setExecutorParams("web run");
        triggerParam.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.COVER_EARLY.name());
        triggerParam.setGlueType(GlueTypeEnum.BEAN.name());
        triggerParam.setGlueSource(null);
        triggerParam.setGlueUpdatetime(System.currentTimeMillis());
        triggerParam.setLogId(1);
        triggerParam.setLogDateTime(System.currentTimeMillis());

        // Act
        ReturnT<String> retval = executorBiz.run(triggerParam);

        return retval.toString();
    }

}