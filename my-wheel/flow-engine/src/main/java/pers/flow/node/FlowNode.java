package pers.flow.node;

import pers.flow.content.FlowContent;

/**
 * 流程节点的抽象
 * @auther ken.ck
 * @date 2021/6/19 16:03
 */
public abstract class FlowNode {

    abstract void invode(FlowContent content);

}
