package pers.factory.simpleFactory;

import pers.factory.simpleFactory.robot.DetectionRobot;
import pers.factory.simpleFactory.robot.IRobot;
import pers.factory.simpleFactory.robot.RobotType;
import pers.factory.simpleFactory.robot.SweepingRobot;

import java.util.Calendar;

public class App {

    public static void main(String[] args) {

        // 创建工程实例
        RobotFactory robotFactory = new RobotFactory();

        // 根据参数创建不同的实例，使用者依赖工厂
        IRobot walle = robotFactory.buildRobotByType(RobotType.SWEEP);
        IRobot eve = robotFactory.buildRobotByType(RobotType.DETECTION);

        walle.action();
        eve.action();

        // 也是一样的，通过入参的不同返回不同的实例
        walle = robotFactory.buildRobotByClass(SweepingRobot.class);
        eve = robotFactory.buildRobotByClass(DetectionRobot.class);

        walle.action();
        eve.action();

        // 可以看下Java这个类的实现，也是简单工厂
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());
    }

}
