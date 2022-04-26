package pers.test;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

/**
 * @auther ken.ck
 * @date 2022/4/26 20:07
 */
public class CxfClient4Test {

    static final String wsdlUrl = "http://localhost:8080/services/ws/api?wsdl";

    public static void main(String[] args) throws Exception {

        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(wsdlUrl);
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME, PASS_WORD));

        // invoke("方法名",参数1,参数2,参数3....);
        Object[] objects = client.invoke("queryUser", "hello world");
        System.out.println("返回数据:" + objects[0]);

        // 只能传对应类型的参数，没法直接用字符串去传递。 如何做一个通用的客户端
        String user = "<user><id>55</id><name>Luffer</name><cat><name>huahua</name></cat></user>";
        String arg1 = "hi ";
        objects = client.invoke("changeUser", user, arg1);
        System.out.println("返回数据:" + objects[0]);
    }

}
