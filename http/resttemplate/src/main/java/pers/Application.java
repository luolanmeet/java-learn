package pers;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@ComponentScan("pers")
public class Application {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        RestTemplate restTemplate = context.getBean(RestTemplate.class);

        getForEntity(restTemplate);
        postForEntity(restTemplate);
        exchange(restTemplate);
    }

    private static void exchange(RestTemplate restTemplate) {
        String url = "https://www.baidu.com";
        HttpEntity requestEntity = getHttpEntity("");
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        print(responseEntity);
    }

    private static void postForEntity(RestTemplate restTemplate) {
        String url = "https://fanyi.baidu.com/sug";
        HttpEntity requestEntity = getHttpEntity("");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        print(responseEntity);
    }

    private static void getForEntity(RestTemplate restTemplate) {
        String url = "https://www.baidu.com";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        print(responseEntity);
    }

    private static HttpEntity<String> getHttpEntity(String param) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));

        return new HttpEntity<>(param, headers);
    }

    private static void print(ResponseEntity<String> responseEntity) {
        HttpHeaders headers = responseEntity.getHeaders();
        System.out.println(headers);
        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getStatusCodeValue());
        System.out.println(responseEntity.getBody());
    }

}
