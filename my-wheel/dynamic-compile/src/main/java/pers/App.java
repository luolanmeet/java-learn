package pers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

public class App {
    
    private static Map<String, JavaFileObject> fileObjects = new ConcurrentHashMap<>();
    
    public static void main( String[] args ) throws Exception {
        
        // 代码
        String code = "public class Man {\n" +
                        "\t public void hello(){\n" +
                            "\t System.out.println(\"hello world\");\n" +
                        "\t }\n" +
                       "}";
        
        // 从字符串中找到类名
        Pattern CLASS_PATTERN = Pattern.compile("class\\s+([$_a-zA-Z][$_a-zA-Z0-9]*)\\s*");
        Matcher matcher = CLASS_PATTERN.matcher(code);
        matcher.find();
        String className = matcher.group(1);
        
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
        
        JavaFileManager javaFileManager = new MyJavaFileManager(
                fileObjects,
                compiler.getStandardFileManager(collector, null, null));

        // 指定编译时的参数
        List<String> options = new ArrayList<>();
        options.add("-target");
        options.add("1.8");
        
        // 提交一个编译任务
        JavaFileObject javaFileObject = new MyJavaFileObject(className, code);
        compiler.getTask(
                null, 
                javaFileManager, 
                collector, 
                options, 
                null, 
                Arrays.asList(javaFileObject))
        .call();

        // 加载类
        ClassLoader classloader = new MyClassLoader(fileObjects);
        Class<?> clazz = classloader.loadClass(className);

        Method method = clazz.getMethod("hello");
        method.invoke(clazz.newInstance());
        
    }

}
