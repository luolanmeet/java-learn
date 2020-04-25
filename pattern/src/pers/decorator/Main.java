package pers.decorator;

public class Main {

    public static void main(String[] args) {

        BaseCake cake = new BaseCake();
        System.out.println(cake.getMsg() + " 热量：" + cake.getCalorie());

        cake = new MangoCake(cake);
        System.out.println(cake.getMsg() + " 热量：" + cake.getCalorie());

        cake = new StrawberryCake(cake);
        System.out.println(cake.getMsg() + " 热量：" + cake.getCalorie());

        // ... 无限套娃

    }

}
