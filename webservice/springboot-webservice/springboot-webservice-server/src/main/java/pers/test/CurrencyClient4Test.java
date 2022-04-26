package pers.test;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther ken.ck
 * @date 2022/4/26 20:41
 */
public class CurrencyClient4Test {

    static final Map<String, String> schemasMap = new HashMap<>();
    static {
        schemasMap.put("1.1", "http://schemas.xmlsoap.org/soap/envelope/");
        schemasMap.put("1.2", "http://www.w3.org/2003/05/soap-envelope");
    }

    public static String buildSoapData(
            String soapVersion, String namespace, String operationName, String content) {

        return "<soapenv:Envelope xmlns:soapenv=" + "\"" + schemasMap.get(soapVersion) + "\" xmlns:ser=\"" + namespace + "\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<ser:" + operationName + ">" +
                content +
                "</ser:" + operationName + ">" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";
    }

    private static void doRequest(String soapVersion, String data) {

        MediaType mt;
        if ("1.1".equals(soapVersion)) {
            mt = new MediaType("text", "xml", StandardCharsets.UTF_8);
        } else if ("1.2".equals(soapVersion)) {
            mt = new MediaType("application", "soap+xml", StandardCharsets.UTF_8);
        } else {
            throw new RuntimeException();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mt);
        HttpEntity<String> entity = new HttpEntity<>(data, headers);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(publishUrl, entity, String.class);
        System.out.println(response);
    }

    static final String nameSpaceURI = "http://service.pers/";
    static final String publishUrl = "http://localhost:8080/services/ws/api";

    public static void main(String[] args) {
//        queryUser();
        changeUser();
    }

    private static void changeUser() {
        String data = buildSoapData("1.1", nameSpaceURI, "changeUser", "<user><id>55</id><name>Luffer</name><cat><name>huahua</name></cat></user><arg1>hi </arg1>");
        System.out.println(data);
        doRequest("1.1", data);
    }

    private static void queryUser() {
        String data = buildSoapData("1.1", nameSpaceURI, "queryUser", "<xml>222</xml>");
        System.out.println(data);
        doRequest("1.1", data);
    }

}
