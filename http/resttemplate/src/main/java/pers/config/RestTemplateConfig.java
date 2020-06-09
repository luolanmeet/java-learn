package pers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Bean
    public SimpleClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(15000);
        return requestFactory;
    }

    @Bean
    public RestTemplate restTemplate(SimpleClientHttpRequestFactory simpleClientHttpRequestFactory) {

        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);

        // 设置编码
        setEncode(restTemplate);

        // 设置错误处理
        restTemplate.setErrorHandler(new HttpRequestErrorHandler());

        return restTemplate;
    }

    /**
     * 设置编码
     * @param restTemplate
     */
    private void setEncode(RestTemplate restTemplate) {

        // 可解决中文乱码问题
        // restTemplate 第二个 messageConverters 就是StringHttpMessageConverter，
        // StringHttpMessageConverter默认编码是 StandardCharsets.ISO_8859_1
//        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        // 保险写法
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        for (int i = 0; i < messageConverters.size(); i++) {
            HttpMessageConverter<?> httpMessageConverter = messageConverters.get(i);
            if (httpMessageConverter.getClass().equals(StringHttpMessageConverter.class)) {
                messageConverters.set(i, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            }
        }

        // 可解决unicode编码问题
//        restTemplate.getMessageConverters().add(0, new FastJsonHttpMessageConverter());
    }

}
