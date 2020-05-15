package pers.delegate.role;

import java.util.HashMap;
import java.util.Map;

public class Player implements Role {

    Map<String, Role> roles = new HashMap<>();
    public Player() {
        roles.put("火球术", new Mage());
        roles.put("蓄气斩", new Warrior());
    }

    @Override
    public void attack(String skill) {

        if (!roles.containsKey(skill)) {
            System.out.println("不能使用该技能");
            return ;
        }
        roles.get(skill).attack(skill);
    }

}
