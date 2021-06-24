package pers.node;

import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import pers.flow.annotation.JavaMethod;
import pers.flow.annotation.JavaNode;
import pers.flow.content.FlowContent;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @auther ken.ck
 * @date 2021/6/23 23:54
 */
@JavaNode("778887ae2e784c3c9599b55135eff87f")
public class JavaRestTempleNode {

    RestTemplate restTemplate;

    {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(15000);

        restTemplate = new RestTemplate(requestFactory);
        // 设置编码
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        for (int i = 0; i < messageConverters.size(); i++) {
            HttpMessageConverter<?> httpMessageConverter = messageConverters.get(i);
            if (httpMessageConverter.getClass().equals(StringHttpMessageConverter.class)) {
                messageConverters.set(i, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            }
        }
    }

    // 配置

    public final static String URL_KEY = "url";
    public final static String PARAM_KEY = "param";

    @JavaMethod("14d8388c799946e681286b9269643b32")
    public String doPost(FlowContent content) {
        HttpEntity requestEntity = getHttpEntity(content.getParam(PARAM_KEY));
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                content.getParam(URL_KEY), HttpMethod.POST, requestEntity, String.class);
        return responseEntity.toString();
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
    }

    private HttpEntity<String> getHttpEntity(String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
        return new HttpEntity<>(param, headers);
    }

}
