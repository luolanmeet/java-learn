package com.pers;

import com.pers.client.CnaplarkServiceClient;
import com.pers.client.HelloClient;
import com.pers.client.UnifiedEntranceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * @auther ken.ck
 * @date 2022/4/19 14:03
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients // 用openFeign才需要打开
public class ClientApplication {

	@Autowired(required = false)
	private HelloClient client;
	@Autowired(required = false)
	private CnaplarkServiceClient cnapClient;

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private LoadBalancerClient loadBalancerClient;

	@RequestMapping("/")
	public String hello() {
		return client.hello();
	}

	@RequestMapping("/cnap")
	public String cnap() {
		UnifiedEntranceDTO requestDto = new UnifiedEntranceDTO();
		requestDto.setBody("body");
		Object o = cnapClient.unifiedEntry(requestDto);
		return o.toString();
	}

	@RequestMapping("/cnap2")
	public String cnap2() {

		String serverId = "cnap-lark";
		String path = "out_side/unified_entry";

		ServiceInstance serviceInstance = loadBalancerClient.choose(serverId);
		String url = String.format("http://%s:%s/%s/",
				serviceInstance.getHost(), serviceInstance.getPort(), path);
		System.out.println("request url:" + url);

		String requestBody = "{\"body\":\"value数值\"}";
		HttpHeaders headers = new HttpHeaders();
		MediaType mt = new MediaType("application", "json", StandardCharsets.UTF_8);
		headers.setContentType(mt);
		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

		Object o = restTemplate.postForObject(url, entity, Object.class);

		assert o != null;
		return o.toString();
	}

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}
}
