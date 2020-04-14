package pers.factory.simpleFactory;

import pers.factory.simpleFactory.robot.IRobot;
import pers.factory.simpleFactory.robot.RobotType;

public class App {

    public static void main(String[] args) {

        // 创建工程实例
        RobotFactory robotFactory = new RobotFactory();

        // 根据参数创建不同的实例
        IRobot walle = robotFactory.buildRobot(RobotType.SWEEP);
        IRobot eve = robotFactory.buildRobot(RobotType.DETECTION);

        walle.action();
        eve.action();
    }

}
