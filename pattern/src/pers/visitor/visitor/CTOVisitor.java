package pers.visitor.visitor;

import pers.visitor.element.Engineer;
import pers.visitor.element.Manager;

public class CTOVisitor extends IVisitor {

    @Override
    public void visit(Engineer engineer) {
        System.out.println("工程师：" + engineer.name + " KPI：" + engineer.kpi + " 代码量：" + engineer.getCodeLines());
    }

    @Override
    public void visit(Manager manager) {
        System.out.println("经理：" + manager.name + " KPI：" + manager.kpi);
    }

}
