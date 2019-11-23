package com.mybatis.simple.objectfactory;

import com.mybatis.simple.bean.User;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

/**
 * MyBatis 每次创建结果对象的新实例时，它都会使用一个对象工厂（ObjectFactory）实例来完成。
 * 默认的对象工厂需要做的仅仅是实例化目标类，
 * 要么通过默认构造方法，要么在参数映射存在的时候通过参数构造方法来实例化。
 * 如果想覆盖对象工厂的默认行为，则可以通过创建自己的对象工厂来实现。
 */
public class MyObjectFactory extends DefaultObjectFactory {

    @Override
    public <T> T create(Class<T> type) {

        if (type.equals(User.class)) {
            User user = (User) super.create(type);
            // setName 是不起作用的，会被结果集中真正的name值覆盖
            user.setTime(System.currentTimeMillis());
            return (T) user;
        }

        return super.create(type);
    }

    public static void main(String[] args) {
        MyObjectFactory objectFactory = new MyObjectFactory();
        System.out.println(objectFactory.create(User.class));
    }

}
