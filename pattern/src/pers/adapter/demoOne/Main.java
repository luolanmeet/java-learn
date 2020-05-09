package pers.adapter.demoOne;

public class Main {

    public static void main(String[] args) {
        IPassportForThird adapter = new PassportForThirdAdapter();
        adapter.loginForQQ("****");
        adapter.loginForTelphone("123", "5555");
    }

}
