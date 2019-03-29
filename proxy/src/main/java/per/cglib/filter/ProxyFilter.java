package per.cglib.filter;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 *
 * @author cck
 */
public class ProxyFilter implements CallbackFilter {

    @Override
    public int accept(Method method) {

        String methodName = method.getName();

        // 如果是sayHello方法，则使用第一个Proxy
        if (methodName.equals("sayHello"))  {
            return 0;
        }

        if (methodName.equals("sayHelloWorld"))  {
            return 1;
        }

        return 2;
    }

}
