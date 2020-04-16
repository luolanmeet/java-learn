package pers.factory.abstractFactory.impl.apple;

import pers.factory.abstractFactory.product.IComputer;

public class AppleComputer implements IComputer {
    @Override
    public void calc() {
        System.out.println("use apple computer calc");
    }
}
