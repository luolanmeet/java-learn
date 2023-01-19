package pers.factory.factoryMethod.factory;

import pers.factory.factoryMethod.robot.IRobot;
import pers.factory.factoryMethod.robot.SweepingRobot;

/**
 * 具体的工厂
 */
public class SweepingRobotFactory implements IRobotFactory {

    @Override
    public IRobot build() {
        return new SweepingRobot();
    }

}
