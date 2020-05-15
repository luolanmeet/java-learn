package pers.delegate.role;

public class Warrior implements Role {

    @Override
    public void attack(String skill) {
        System.out.println("战士使用" + skill + "攻击");
    }

}
