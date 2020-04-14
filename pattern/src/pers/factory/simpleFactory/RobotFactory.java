package pers.factory.simpleFactory;

import pers.factory.simpleFactory.robot.DetectionRobot;
import pers.factory.simpleFactory.robot.IRobot;
import pers.factory.simpleFactory.robot.RobotType;
import pers.factory.simpleFactory.robot.SweepingRobot;

/**
 * 简单工厂
 */
public class RobotFactory {

    public IRobot buildRobot(int type) {

        switch (type) {
            case RobotType.SWEEP:
                return new SweepingRobot();

            case RobotType.DETECTION:
                return new DetectionRobot();
        }

        return null;
    }

}
