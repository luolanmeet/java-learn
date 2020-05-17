package pers.state;

public class LoginState extends UserState {

    @Override
    public void watch() {
        System.out.println("会员观看视频");
    }

    @Override
    public void getLike() {
        System.out.println("点赞");
    }

    @Override
    public void collec() {
        System.out.println("收藏");
    }

}
