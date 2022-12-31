package pers.prototype;

import java.io.*;
import java.lang.reflect.Field;

/**
 * 复制bean
 */
public class BeanUtil {

//    public static Object shallowCopy(Object protorype) {
//        Class clazz = protorype.getClass();
//        Object returnValue = null;
//        try {
//            returnValue = clazz.newInstance();
//            for (Field field : clazz.getDeclaredFields()) {
//                field.setAccessible(true);
//                field.set(returnValue, field.get(protorype));
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return returnValue;
//    }

    public static Object deepCopy(Object prototype) {

        try (
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos)) {

                oos.writeObject(prototype);

                try (ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
                     ObjectInputStream ois = new ObjectInputStream(bis)) {
                    return ois.readObject();
                }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

}
