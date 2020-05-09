package pers.adapter.demoOne.adapter;

import pers.adapter.demoOne.PassportService;

public abstract class AbstraceAdapter extends PassportService implements ILoginAdapter {

    protected String loginForRegist(String username, String password){
        if(null == password){
            password = "THIRD_EMPTY";
        }
        super.regist(username,password);
        return super.login(username,password);
    }

}
