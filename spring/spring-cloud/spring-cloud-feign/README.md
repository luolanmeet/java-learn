服务注册用的是`spring-cloud-eureka-server`，需要启动

接口使用`@FeignClient(name = "hello-service")`注解，
声明应用的名称，接口中的抽象方法使用`@XxxMapping`指定处理的`url`路径。

服务提供者的`application.properties`中声明应用名称为
`hello-service`，对应到某个接口。
`spring.application.name=hello-service`
服务提供者的具体方法也使用`@XxxMapping`指定处理的`url`路径，对应到接口的方法上。

服务消费者使用`@EnableFeignClients(clients = IHelloService.class)`，之后注入接口实例，
然后可以直接使用。


