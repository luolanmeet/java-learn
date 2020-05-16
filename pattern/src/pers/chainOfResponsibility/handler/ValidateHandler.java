package pers.chainOfResponsibility.handler;

public class ValidateHandler extends Handler {

    @Override
    public void doHandle(String username, String password) {
        if (username == null || password == null) {
            System.out.println("帐号或密码为空");
            return ;
        }
        chain.doHandle(username, password);
    }

}
