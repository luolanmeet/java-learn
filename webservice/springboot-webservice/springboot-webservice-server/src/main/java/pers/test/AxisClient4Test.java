//package pers;
//
//import org.apache.axis.client.Call;
//import org.apache.axis.client.Service;
//import org.apache.axis.encoding.XMLType;
//
//import javax.xml.namespace.QName;
//import javax.xml.rpc.ParameterMode;
//import javax.xml.rpc.ServiceException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.rmi.RemoteException;
//
///**
// * @auther ken.ck
// * @date 2022/4/25 20:54
// */
//public class Client4Test {
//
//    //一般如果没有注解targetNamespace的话，默认生成的就是接口这个文件的路径名
//    static final String nameSpaceURI = "http://service.pers/";
//    static final String publishUrl = "http://localhost:8080/services/ws/api";
//
//    public static void main(String[] args) throws Exception {
//        queryUser();
//    }
//
//    private static void queryUser() throws ServiceException, MalformedURLException, RemoteException {
//
//        Service service = new Service();
//        Call call = (Call) service.createCall();
//        call.setTargetEndpointAddress(new URL(publishUrl));
//        //指定接口路径，要调用的方法名
//        call.setOperationName(new QName(nameSpaceURI, "queryUser"));
//        call.addParameter("xml", XMLType.XSD_STRING, ParameterMode.IN);
//        call.setReturnType(XMLType.XSD_STRING);
//        String xml = " xml data";
//        Object[] obj = new Object[] { xml};
//        String result = (String) call.invoke(obj);
//
//        System.out.println(result);
//    }
//
//}
