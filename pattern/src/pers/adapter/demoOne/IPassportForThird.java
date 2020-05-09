package pers.adapter.demoOne;

/**
 * 目标角色（target）：期望的接口
 */
public interface IPassportForThird {

    String loginForQQ(String openId);

    String loginForTelphone(String phone, String code);

}
