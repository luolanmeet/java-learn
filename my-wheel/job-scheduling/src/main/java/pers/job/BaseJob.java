package pers.job;

import lombok.Setter;

/**
 * 基础任务，用户继承此抽象类
 * 在run方法中编写业务代码
 * 通过控制isExit变量，控制任务是否执行
 * @author cck
 */
public abstract class BaseJob implements Runnable {

    /** 用于控制任务是否退出 */
    @Setter
    boolean isExit = false;
    
}
