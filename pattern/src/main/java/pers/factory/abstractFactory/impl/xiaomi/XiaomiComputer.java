package pers.factory.abstractFactory.impl.xiaomi;

import pers.factory.abstractFactory.product.IComputer;

public class XiaomiComputer implements IComputer {
    @Override
    public void calc() {
        System.out.println("use xiaomi computer calc");
    }
}
