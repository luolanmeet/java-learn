package pers.syntax;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

/**
 * try catch 语法
 * @auther ken.ck
 * @date 2023/11/19 00:10
 */
public class TryCatchSyntax {

    public static void main(String[] args) throws IOException {
        tryAndFinally();
        System.out.println("------");
        finallyConvertReturn();
        System.out.println("------");
        noExeFinally();
    }

    public static void finallyConvertReturn() {

        // finally 中的 return 会覆盖 try 和 catch 中的 return
        // finally 中的 return 会覆盖 try 和 catch 中再次抛出的异常
        Supplier<String> action = () -> {
            try {
                System.out.println("Inside try");
                if (1 == 1) {
                    throw new RuntimeException("");
                }
                return "from try";
            } finally {
                System.out.println("Inside finally");
                return "from finally";
            }
        };
        System.out.println(action.get());

        // finally 中抛出的异常，将没有返回值返回
        action = () -> {
            try {
                System.out.println("Inside try");
                return "from try";
            } finally {
                throw new RuntimeException("finally exception");
            }
        };
        System.out.println(action.get());
    }

    public static void tryAndFinally() throws IOException {

        // try 可以只和 finally一起使用
        try {
            System.out.println("1");
        } finally {
            System.out.println("2");
        }

        // try 可以自己单独使用
        try (InputStream in = System.in;) {
            System.out.println("3");
        }

        System.out.print("");

        // finally 不可以自己单独使用
//        finally {
//        }
    }

    public static void noExeFinally() {

        // 不会执行 finally 的情况

        // 操作系统中断了程序，那么finally代码块就不能被执行
        try {
            System.out.println("Inside try");
            System.exit(1);
        } finally {
            System.out.println("Inside finally");
        }

        try {
            System.out.println("Inside try");
            Runtime.getRuntime().halt(1);
        } finally {
            System.out.println("Inside finally");
        }
    }

}
