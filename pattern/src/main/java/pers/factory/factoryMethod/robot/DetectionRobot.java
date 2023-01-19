package pers.factory.factoryMethod.robot;

/**
 * 探测机器人
 */
public class DetectionRobot implements IRobot {

    @Override
    public void action() {
        System.out.println("进入搜索状态");
    }

}
