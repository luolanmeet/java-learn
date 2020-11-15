package com.autoValue;

/**
 * @author cck
 * @date 2020/11/14 16:33
 */
public class Test {

    public static void main(String[] args) {

        Person person = Person.create("cck", 25);
        System.out.println(person);
        // 没有 setter 方法

        PersonWithBuilder personWithBuilder = PersonWithBuilder.builder()
                .name("cck")
                .age(25).build();
        System.out.println(personWithBuilder);
    }

}
