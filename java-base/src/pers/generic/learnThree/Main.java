package pers.generic.learnThree;

/**
 * @auther ken.ck
 * @date 2021/9/11 22:40
 */
public class Main {

    public static void main(String[] args) {
        IUser<StudentDto> user = new StudentUser();
        user.callback("{}");
        user.method(new StudentDto());
    }

}
