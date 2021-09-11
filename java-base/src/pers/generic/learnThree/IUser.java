package pers.generic.learnThree;

/**
 * @auther ken.ck
 * @date 2021/9/11 22:34
 */
public interface IUser<T> {

    void method(T t);

    void callback(String jsonStr);

}
