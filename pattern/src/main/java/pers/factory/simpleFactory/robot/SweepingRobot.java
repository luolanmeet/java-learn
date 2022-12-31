package pers.factory.simpleFactory.robot;

/**
 * 扫地机器人
 */
public class SweepingRobot implements IRobot {

    @Override
    public void action() {
        System.out.println("开始打扫");
    }

}
