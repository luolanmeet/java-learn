package pers.factory.simpleFactory.robot;

import pers.factory.simpleFactory.robot.IRobot;

/**
 * 扫地机器人
 */
public class SweepingRobot implements IRobot {

    @Override
    public void action() {
        System.out.println("开始打扫");
    }

}
