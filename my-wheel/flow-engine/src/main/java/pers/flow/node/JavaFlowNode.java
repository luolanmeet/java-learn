package pers.flow.node;

import lombok.Data;
import pers.flow.constant.FlowNodeType;

import java.lang.reflect.Method;

/**
 * Java类节点
 * @auther ken.ck
 * @date 2021/6/19 16:08
 */
@Data
public class JavaFlowNode extends AbstractFlowNode {

    private FlowNodeType flowNodeType = FlowNodeType.JAVA;

    private Object javaObject;

    private Method javaMethod;

    public void invode() {

    }

}
