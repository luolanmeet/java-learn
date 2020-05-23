package pers.visitor.visitor;

import pers.visitor.element.Engineer;
import pers.visitor.element.Manager;

/**
 * 数据类型应该是较为固定的，如果经常变化就不适用访问者模式了
 */
public abstract class IVisitor {

    public abstract void visit(Engineer engineer);

    public abstract void visit(Manager manager);

}