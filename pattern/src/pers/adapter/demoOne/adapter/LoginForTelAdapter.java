package pers.adapter.demoOne.adapter;

public class LoginForTelAdapter extends AbstraceAdapter {

    @Override
    public boolean support(Object adapter) {
        return adapter instanceof LoginForTelAdapter;
    }

    @Override
    public String login(String id, Object adapter) {

        if (!support(adapter)) {
            throw new RuntimeException("no support");
        }

        // something

        return super.loginForRegist(id, null);
    }

}
