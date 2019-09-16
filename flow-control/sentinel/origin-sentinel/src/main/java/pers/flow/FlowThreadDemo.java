package pers.flow;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlowThreadDemo {
    
    private static String resource = "doTest";
    
    public static void main(String[] args) throws IOException {
    
        initFlowQpsRule();
        
        for (int i = 0; i < 4; i++) {
    
            Thread thread = new Thread(() -> {
            
                while (true) {

                    String threadName = Thread.currentThread().getName();
                    
                    try (Entry entry = SphU.entry(resource)) {
                        System.out.println(threadName + " invoke : " + resource);
                    } catch (BlockException e) {
                        System.out.println(threadName + " 被限流");
                    }
                }
            
            });
            thread.setName("thread-" + i);
            thread.start();
        }
        
        System.in.read();
    }
    
    // 初始化规则
    private static void initFlowQpsRule() {
    
        List<FlowRule> rules = new ArrayList<>();
        FlowRule flowRule = new FlowRule();
        // 限流的目标（方法、接口）
        flowRule.setResource(resource);
        // 限流的类型，线程数
        // 并发线程数限流 用于保护业务线程数不被耗尽
        // 当前请求上下文的线程数目，如果超出阈值，新的请求会被立即拒绝
        flowRule.setGrade(RuleConstant.FLOW_GRADE_THREAD);
        flowRule.setCount(2);
        
        rules.add(flowRule);
        FlowRuleManager.loadRules(rules);
    }
    
}
