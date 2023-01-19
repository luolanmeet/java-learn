package pers.visitor.element;

import pers.visitor.visitor.IVisitor;

import java.util.Random;

/**
 * 工程师
 */
public class Engineer extends Employee {

    public Engineer(String name) {
        super(name);
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    public int getCodeLines() {
        return new Random().nextInt(10 * 10000);
    }

}
