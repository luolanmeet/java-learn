package pers.delegate;

import pers.delegate.role.Player;

public class Main {

    public static void main(String[] args) {

        Player player = new Player();

        player.attack("旋风斩");
        player.attack("蓄气斩");
        player.attack("火球术");
    }

}
