package pers.factory.abstractFactory.impl.apple;

import pers.factory.abstractFactory.product.IPhone;

public class ApplePhone implements IPhone {
    @Override
    public void call() {
        System.out.println("use apple phone call");
    }
}
