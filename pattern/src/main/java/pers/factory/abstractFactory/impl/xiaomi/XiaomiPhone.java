package pers.factory.abstractFactory.impl.xiaomi;

import pers.factory.abstractFactory.product.IPhone;

public class XiaomiPhone implements IPhone {
    @Override
    public void call() {
        System.out.println("use xiaomi phone call");
    }
}
