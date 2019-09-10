package pers.flow;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

public class FlowQpsDemo {

    private static String resource = "doTest";
    
    public static void main(String[] args) throws InterruptedException {
        
        initFlowQpsRule();
        
        while (true) {
            
            Thread.sleep(200);
            Entry entry = null;
            try {
                entry = SphU.entry(resource);
                System.out.println("invoke : " + resource);
            } catch (BlockException e) {
                // 被限流
                System.out.println("被限流");
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }
        }
    }
    
    // 初始化规则
    private static void initFlowQpsRule() {
        
        List<FlowRule> rules = new ArrayList<>();
        FlowRule flowRule = new FlowRule();
        // 限流的目标（方法、接口）
        flowRule.setResource(resource);
        // 限流的类型，qps
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(4);
        
        rules.add(flowRule);
        FlowRuleManager.loadRules(rules);
    }
    
}
