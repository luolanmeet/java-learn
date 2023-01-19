package pers.chainOfResponsibility;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserService();
        userService.login("cck", "777");
        userService.login("cck", "555");
    }

}
