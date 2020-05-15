package pers.delegate.role;

public class Mage implements Role {

    @Override
    public void attack(String skill) {
        System.out.println("法师使用" + skill + "攻击");
    }

}
