### fallback 配置
> HSF、Dubbo 的默认 fallback 是抛出异常，Web 的默认 fallback 是 302。

以下 fallback 全局注册逻辑 配置方式。只要在合适的地方注册一次（比如@PostConstruct），就能改变全局的 fallback 行为，每种回调里头的参数均包含了当前调用的必要信息，在代码中查看回调接口了解详情。

应用类型 | 旧 SDK (2.x) | 新 SDK (3.x)
--- | --- |---
HSF | BlockException.registerHsfProviderBlock(HsfProviderBlock) | HsfFallbackRegistry.setProviderFallback(HsfFallback) 同时兼容旧的方式
Dubbo | 不支持 | 服务方：DubboFallbackRegistry.setProviderFallback(DubboFallback) 消费方：DubboFallbackRegistry.setConsumerFallback(DubboFallback)
Web | SentinelUtil.registerUrlBlock(UrlBlock) | WebCallbackManager.setUrlBlockHandler(UrlBlockHandler) 同时兼容旧的方式
其他类型 | SentinelUtil.isBlockException(Throwable) 判断是否被被限流 或者直接捕获 BlockException | BlockException.isBlockException(Throwable) 判断是否被 Sentinel 限流，然后自定义异常处理逻辑 同时兼容旧的方式


