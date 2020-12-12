package pers.string;

import java.text.MessageFormat;

/**
 * @author cck
 * @date 2020/12/11 19:50
 */
public class MessageFormatDemo {

    public static void main(String[] args) {

        // MessageFormat.format 性能要比 String.format 好

        String pattern1 = "age:%d,name:%s";
        System.out.println(String.format(pattern1, 11, "cck"));

        String pattern2 = "age:{0},name:{1}";
        System.out.println(MessageFormat.format(pattern2, 12, "ck"));

        test1();
        test2();
        test3();
    }

    static void test1() {
        // 入口可以是数组
        String message = "{0}{1}{2}{3}{4}{5}{6}{7}{8}{9}{10}{11}{12}{13}{14}{15}{16}";
        Object[] array = new Object[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q"};
        String value = MessageFormat.format(message, array);
        System.out.println(value);
    }

    static void test2() {
        // 单引号使某个字符串保持原型
        String message = "i like '{0}'";
        Object[] array = new Object[]{"computer game"};
        String value = MessageFormat.format(message, array);
        System.out.println(value);
    }

    static void test3() {
        // 使用FormatType FormatStyle
        // {0, number, #.##} 里面的number属于FormatType，#.##则属于FormatStyle
        String message = "π is {0, number, #.##}";
        String value = MessageFormat.format(message, 3.14159);
        System.out.println(value);
    }

}
