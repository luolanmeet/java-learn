package pers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Service
public class DemoService {

    @Autowired
    RestTemplate restTemplate;

    public void exchange() {
        String url = "https://www.baidu.com";
        HttpEntity requestEntity = getHttpEntity("");
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        print(responseEntity);
    }

    @Retryable(
            maxAttempts = 3, // 最多重试3次
            backoff = @Backoff(delay = 2000L, multiplier = 1.5), // 重试时等待2s 延迟倍数1.5 【2 2*1.5 2*1.5*1.5】
            include = {Exception.class},
            listeners = {"demoRetryListener"})
    public void errorMethod() {
        String url = "https://locaohost:8080";
        HttpEntity requestEntity = getHttpEntity("");
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        print(responseEntity);
    }

    public void postForEntity() {
        String url = "https://fanyi.baidu.com/sug";
        HttpEntity requestEntity = getHttpEntity("");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        print(responseEntity);
    }

    public void getForEntity() {
        String url = "https://www.baidu.com";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        print(responseEntity);
    }

    private HttpEntity<String> getHttpEntity(String param) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));

        return new HttpEntity<>(param, headers);
    }

    private void print(ResponseEntity<String> responseEntity) {
        HttpHeaders headers = responseEntity.getHeaders();
        System.out.println(headers);
        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getStatusCodeValue());
        System.out.println(responseEntity.getBody());
    }

}
