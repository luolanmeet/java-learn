package pers.prototype;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Person person = new Person();
        person.setAge(25);
        person.setName("ccc");

        List<String> hobbies = new ArrayList<>();
        hobbies.add("game");
        person.setHobbies(hobbies);

//        Person p1 = (Person) BeanUtil.shallowCopy(person);
//        System.out.println(p1);

        Person p2 = (Person) BeanUtil.deepCopy(person);
        System.out.println(p2);
        System.out.println(person.getHobbies() == p2.getHobbies());
    }

}
