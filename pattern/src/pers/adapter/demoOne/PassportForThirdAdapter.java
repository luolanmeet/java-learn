package pers.adapter.demoOne;

import pers.adapter.demoOne.adapter.ILoginAdapter;
import pers.adapter.demoOne.adapter.LoginForQQAdapter;
import pers.adapter.demoOne.adapter.LoginForTelAdapter;

/**
 * 适配器（adapter）：讲源角色（adaptee）转化为目标角色（target）的类实例。
 */
public class PassportForThirdAdapter implements IPassportForThird {

    @Override
    public String loginForQQ(String openId) {
        return processLogin(openId, LoginForQQAdapter.class);
    }

    @Override
    public String loginForTelphone(String phone, String code) {
        return processLogin(phone, LoginForTelAdapter.class);
    }

    private String processLogin(String id,Class<? extends ILoginAdapter> clazz){
        try {
            ILoginAdapter adapter = clazz.newInstance();
            if (adapter.support(adapter)){
                return adapter.login(id,adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new RuntimeException("no support");
    }

}
