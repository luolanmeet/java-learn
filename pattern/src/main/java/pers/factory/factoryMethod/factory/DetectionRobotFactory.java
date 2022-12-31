package pers.factory.factoryMethod.factory;

import pers.factory.factoryMethod.robot.DetectionRobot;
import pers.factory.factoryMethod.robot.IRobot;

/**
 * 具体的工厂
 */
public class DetectionRobotFactory implements IRobotFactory {

    @Override
    public IRobot build() {
        return new DetectionRobot();
    }

}
