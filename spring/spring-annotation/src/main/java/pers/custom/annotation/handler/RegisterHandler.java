package pers.custom.annotation.handler;

import pers.custom.annotation.Cmd;
import pers.custom.annotation.CmdInMapping;

@CmdInMapping(Cmd.REGISTER)
public class RegisterHandler implements ICmdHandler {

    @Override
    public void handle() {
        System.out.println("handle register request");
    }

}
