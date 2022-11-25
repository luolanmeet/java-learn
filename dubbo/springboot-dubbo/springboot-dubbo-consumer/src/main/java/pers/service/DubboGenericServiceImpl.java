package pers.service;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.SimpleReferenceCache;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @auther ken.ck
 * @date 2022/11/25 17:49
 */
@Service
public class DubboGenericServiceImpl {

    /**
     * dubbo 2.7 的泛化调用
     */

/*
    @Autowired
    private RegistryConfig registry;
    @Autowired
    private ApplicationConfig applicationConfig;

    public Object genericInvoke(String interfaceClass, String version, String methodName, String[] invokeParamTyeps, Object[] invokeParamss, Integer timeout) {
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setApplication(applicationConfig);
        reference.setRegistry(registry);
        // 接口名
        reference.setInterface(interfaceClass);
        // 声明为泛化接口
        reference.setGeneric("true");
        reference.setVersion(version);
        reference.setTimeout(timeout);
        // 失败重试次数
        RpcContext.getContext().setAttachment(CommonConstants.RETRIES_KEY, 0);

        //新建ReferenceConfig实例资源消耗很大，里面封装了与注册中心的连接以及与提供者的连接，
        //需要缓存，否则重复生成ReferenceConfig可能造成性能问题并且会有内存和连接泄漏。
        //使用dubbo内置的简单缓存工具类进行缓存
        GenericService genericService = null;
        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        genericService = cache.get(reference);

        return genericService.$invoke(methodName, invokeParamTyeps, invokeParamss);
    }
*/

    /**
     * dubbo 3.x 的泛化调用
     */
    @Value("${dubbo.application.name}")
    private String appName;
    @Value("${dubbo.registry.address}")
    private String registryAddress;

    public Object genericInvoke(String interfaceClass, String version, String methodName, String[] invokeParamTyeps, Object[] invokeParamss, Integer timeout) {
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        ApplicationConfig applicationConfig = new ApplicationConfig(appName);
        RegistryConfig registry = new RegistryConfig(registryAddress);
        reference.setApplication(applicationConfig);
        reference.setRegistry(registry);
        // 接口名
        reference.setInterface(interfaceClass);
        // 声明为泛化接口
        reference.setGeneric("true");
        reference.setVersion(version);
        reference.setTimeout(timeout);
        // 失败重试次数
        RpcContext.getContext().setAttachment(CommonConstants.RETRIES_KEY, 0);

        //新建ReferenceConfig实例资源消耗很大，里面封装了与注册中心的连接以及与提供者的连接，
        //需要缓存，否则重复生成ReferenceConfig可能造成性能问题并且会有内存和连接泄漏。
        //使用dubbo内置的简单缓存工具类进行缓存
        GenericService genericService = null;
        SimpleReferenceCache cache = SimpleReferenceCache.getCache();
        genericService = cache.get(reference);

        return genericService.$invoke(methodName, invokeParamTyeps, invokeParamss);
    }

}
