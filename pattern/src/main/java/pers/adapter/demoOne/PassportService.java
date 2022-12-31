package pers.adapter.demoOne;

/**
 * 源角色（adaptee）：存在于系统中，内容满足客户需求，但接口不匹配的接口实例。
 */
public class PassportService {

    /**
     * 注册方法
     * @param username
     * @param password
     * @return
     */
    public String regist(String username, String password) {
        System.out.println("regist");
        return "200";
    }

    /**
     * 登录的方法
     * @param username
     * @param password
     * @return
     */
    public String login(String username, String password) {
        System.out.println("login");
        return "token";
    }

}
