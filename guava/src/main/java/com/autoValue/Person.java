package com.autoValue;

import com.google.auto.value.AutoValue;

/**
 * @author cck
 * @date 2020/11/14 16:46
 */
@AutoValue
public abstract class Person {

    abstract String getName();
    abstract int getAge();

    /**
     * 工厂方法
     * @param name
     * @param age
     * @return
     */
    static Person create(String name, int age) {
        return new AutoValue_Person(name, age);
    }

}