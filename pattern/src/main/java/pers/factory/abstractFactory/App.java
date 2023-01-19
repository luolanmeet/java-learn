package pers.factory.abstractFactory;

import pers.factory.abstractFactory.factory.IElectricalFactory;
import pers.factory.abstractFactory.impl.apple.AppleFactory;
import pers.factory.abstractFactory.impl.xiaomi.XiaomiFactory;

public class App {

    public static void main(String[] args) {
        IElectricalFactory appleFactory = new AppleFactory();
        IElectricalFactory xiaomiFactory = new XiaomiFactory();

        appleFactory.buildPhone().call();
        appleFactory.buildComputer().calc();

        xiaomiFactory.buildPhone().call();
        xiaomiFactory.buildComputer().calc();
    }

}
