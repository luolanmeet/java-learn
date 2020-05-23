package pers.visitor;

import pers.visitor.element.Employee;
import pers.visitor.element.Engineer;
import pers.visitor.element.Manager;
import pers.visitor.visitor.IVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * 报表
 */
public class BusinessReport {

    private List<Employee> employees = new ArrayList<>();

    public BusinessReport() {
        employees.add(new Engineer("e1"));
        employees.add(new Engineer("e2"));
        employees.add(new Engineer("e3"));
        employees.add(new Manager("m1"));
        employees.add(new Manager("m2"));
    }

    public void showReport(IVisitor visitor) {
        employees.forEach(e -> e.accept(visitor));
    }

}
