> 尝试了下用 spring cloud 做分布式配置
> 使用了 spring cloud 提供的 git 的实现方案

### 实现步骤
1. 创建配置文件，用保存在git仓库中（和.git在同一级），这里建了三个配置文件对应不同环境，`app`是应用名称
```
app.properties
app-dev.properties
app-prod.properties
```

2. 创建一个服务，加入依赖`spring-cloud-config-server`，配置类标记`@EnableConfigServer`注解。

3. 在`application.properties`配置文件中配置 配置文件的地址，这里用的是本地
```
### 定义本地仓库 GIT URL 配置
spring.cloud.config.server.git.uri = file:///C:/Users/Ryan/Desktop/config
```

4. 启动服务，访问 `http://localhost:9090/config-dev.properties`
如果访问一个不存在的配置文件，则会访问到默认的配置文件 ，比如访问`config-noexist.properties`，将会访问到`config.properties`
