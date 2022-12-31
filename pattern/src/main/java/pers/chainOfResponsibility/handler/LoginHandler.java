package pers.chainOfResponsibility.handler;

public class LoginHandler extends Handler {

    @Override
    public void doHandle(String username, String password) {

        if (!"555".equals(password)) {
            System.out.println("登录信息错误");
            return ;
        }

        System.out.println(username + " 登录成功");
    }

}
