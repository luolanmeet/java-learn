package pers.state;

import pers.factory.factoryMethod.App;

public class UnLoginState extends UserState {

    @Override
    public void watch() {
        System.out.println("游客观看视频");
    }

    @Override
    public void getLike() {
        switchLoginState();
        this.appContext.getLike();
    }

    @Override
    public void collec() {
        switchLoginState();
        this.appContext.collec();
    }

    /**
     * 每个状态都知道自己的下一个状态是什么
     */
    public void switchLoginState() {
        System.out.println("跳转到登录页面");
        this.appContext.setState(AppContext.STATE_LOGIN);
    }

}
