package pers.factory.abstractFactory.impl.xiaomi;

import pers.factory.abstractFactory.factory.IElectricalFactory;
import pers.factory.abstractFactory.product.IComputer;
import pers.factory.abstractFactory.product.IPhone;

public class XiaomiFactory implements IElectricalFactory {
    @Override
    public IPhone buildPhone() {
        return new XiaomiPhone();
    }

    @Override
    public IComputer buildComputer() {
        return new XiaomiComputer();
    }
}
