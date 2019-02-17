package pers.custom.annotation.handler;

import pers.custom.annotation.Cmd;
import pers.custom.annotation.CmdInMapping;

@CmdInMapping(value = Cmd.LOGIN)
public class LoginHandler implements ICmdHandler {

    @Override
    public void handle() {
        System.out.println("handle login request");
    }

}
