package pers.clone;

/**
 * @author cck
 * @date 2020/11/15 9:15
 */
public class Demo {

    public static class User extends UserOne implements Cloneable {

        String name;

        @Override
        public String toString() {
            return "User{" + "name='" + name + '\'' + '}';
        }

        @Override
        protected User clone() throws CloneNotSupportedException {
//            return new User();
            return (User) super.clone();
        }
    }

    public static class UserOne implements Cloneable {
        @Override
        protected Object clone() throws CloneNotSupportedException {
            // 如果父类没有使用super.clone()方法，子类使用super.clone()方法时，就得到错误的类。
//            return new UserOne();
            return super.clone();
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {

        User user = new User();
        user.name = "cck";
        System.out.println(user.hashCode());
        System.out.println(user);

        User userClone = user.clone();
        System.out.println(userClone.hashCode());
        System.out.println(userClone);
    }

}
