package pers.factory.factoryMethod.robot;

/**
 * 扫地机器人
 */
public class SweepingRobot implements IRobot {

    @Override
    public void action() {
        System.out.println("开始打扫");
    }

}
