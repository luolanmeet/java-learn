package pers.factory.abstractFactory.impl.apple;

import pers.factory.abstractFactory.factory.IElectricalFactory;
import pers.factory.abstractFactory.product.IComputer;
import pers.factory.abstractFactory.product.IPhone;

public class AppleFactory implements IElectricalFactory {
    @Override
    public IPhone buildPhone() {
        return new ApplePhone();
    }

    @Override
    public IComputer buildComputer() {
        return new AppleComputer();
    }
}
