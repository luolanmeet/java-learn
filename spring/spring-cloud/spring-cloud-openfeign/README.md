服务注册用的是`spring-cloud-eureka-server`，需要启动

（和`spring-cloud-feign`那个项目基本一致）

`spring-cloud-openfeign-provider2` 是用于实验自身暴露接口，又使用其他接口的情况。
> 真实开发中遇到过依赖多个jar包的接口，然后这些jar中依赖的openfeign版本都不相同
> 导致项目无法启动的问题
> >会报java.lang.NoClassDefFoundError: feign/optionals/OptionalDecoder
> >移除掉高版本的openfeign即可

> springboot 2.0.0 和 openfeign 2.1.0不兼容，
> 选择升级springboot的话，需要升级到2.1.x及以上
