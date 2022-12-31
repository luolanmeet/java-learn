package pers.factory.abstractFactory.factory;

import pers.factory.abstractFactory.product.IComputer;
import pers.factory.abstractFactory.product.IPhone;

public interface IElectricalFactory {

    IPhone buildPhone();

    IComputer buildComputer();

}
