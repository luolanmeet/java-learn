package pers.agent.premain;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @auther ken.ck
 * @date 2022/5/5 22:56
 */
public class SimpleClassTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        System.out.println("transform");

        //return null或者执行异常会执行原来的字节码
        return null;

//        if (className.endsWith("sun/net/www/protocol/http/HttpURLConnection")) {
//            ClassPool classPool = ClassPool.getDefault();
//            CtClass clazz = null;
//            try {
//                clazz = classPool.get("sun.net.www.protocol.http.HttpURLConnection");
//
//                CtConstructor[] cs = clazz.getConstructors();
//                for (CtConstructor constructor : cs) {
//                    // 在构造函数结束的位置插入如下的内容
//                    constructor.insertAfter("System.out.println(this.getURL());");
//                }
//
//                byte[] byteCode = clazz.toBytecode();
//
//                // 将类移出
//                clazz.detach();
//
//                return byteCode;
//            } catch (NotFoundException | CannotCompileException | IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
    }

}
