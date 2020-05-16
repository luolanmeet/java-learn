package pers.chainOfResponsibility;

import pers.chainOfResponsibility.handler.Handler;
import pers.chainOfResponsibility.handler.LoginHandler;
import pers.chainOfResponsibility.handler.ValidateHandler;

public class UserService {

    public void login(String username, String password) {

        Handler.Builder builder = new Handler.Builder();
        Handler handler = builder
                .addHandler(new ValidateHandler())
                .addHandler(new LoginHandler())
                .build();

        handler.doHandle(username, password);
    }

}
