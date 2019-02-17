package pers.custom.annotation.handler;

import pers.custom.annotation.Cmd;
import pers.custom.annotation.CmdInMapping;

@CmdInMapping(Cmd.LOGOUT)
public class LogoutHandler implements ICmdHandler {

    @Override
    public void handle() {
        System.out.println("handle logout request");
    }

}
