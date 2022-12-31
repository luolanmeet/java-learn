package pers.factory.factoryMethod;

import pers.factory.factoryMethod.factory.DetectionRobotFactory;
import pers.factory.factoryMethod.factory.IRobotFactory;
import pers.factory.factoryMethod.factory.SweepingRobotFactory;
import pers.factory.factoryMethod.robot.IRobot;

public class App {

    public static void main(String[] args) {

        // 使用者依赖工厂接口以及具体的接口实现类
        IRobotFactory sweepingRobotFactory = new SweepingRobotFactory();
        IRobot walle = sweepingRobotFactory.build();

        IRobotFactory detectionRobotFactory = new DetectionRobotFactory();
        IRobot eve = detectionRobotFactory.build();

        walle.action();
        eve.action();
    }

}
