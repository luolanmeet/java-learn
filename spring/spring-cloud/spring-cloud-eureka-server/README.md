### 高可用注册中心集群 
启动多个eureka-server即可，然后Eureka客户端和服务消费端配置多个Eureka服务器，
即Eureka客户端配置`eureka.client.service-url.defaultZone`填入多个地址，用','分割。
一般可以用域名，然后nginx解析到多个地址上，这样配置就只需填一个域名加一个端口。
源码见`EurekaClientConfigBean`

如果Eureka客户端配置多个Eureka服务器，那么默认只有第一台可用Eureka服务器存在注册信息。

如果第一台Eureka服务器宕机，那么Eureka客户端选择下一台可用的Eureka服务器注册。