package pers.factory.simpleFactory;

import pers.factory.simpleFactory.robot.DetectionRobot;
import pers.factory.simpleFactory.robot.IRobot;
import pers.factory.simpleFactory.robot.RobotType;
import pers.factory.simpleFactory.robot.SweepingRobot;

/**
 * 简单工厂
 */
public class RobotFactory {

    /**
     * 根据type去创建
     * @param type
     * @return
     */
    public IRobot buildRobotByType(int type) {

        switch (type) {
            case RobotType.SWEEP:
                return new SweepingRobot();

            case RobotType.DETECTION:
                return new DetectionRobot();

            default:
                throw new RuntimeException("no support type:" + type);
        }

    }

    public IRobot buildRobotByClass(Class<? extends IRobot> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
