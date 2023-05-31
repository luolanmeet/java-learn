package pers.service;

import org.apache.dubbo.common.BaseServiceMetadata;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.SimpleReferenceCache;
import org.apache.dubbo.registry.client.InstanceAddressURL;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    // ------------------------------------------------

    /**
     * dubbo 3.x 的泛化调用
     */
    @Value("${dubbo.application.name}")
    private String appName;
    @Value("${dubbo.registry.address}")
    private String registryAddress;

    public Object genericInvoke(String interfaceClass, String version, String methodName, String[] invokeParamTyeps, Object[] invokeParamss, Integer timeout) {
        GenericService genericService = getService(interfaceClass, version, timeout);
        return genericService.$invoke(methodName, invokeParamTyeps, invokeParamss);
    }

    public RpcProviderMsgDTO getProviderMsg(String interfaceClass, String version, Integer timeout) {

        try {

            GenericService genericService = getService(interfaceClass, version, timeout);
            // InvokerInvocationHandler
            Object handlerObj = ReflectUtils.getFieldValue(genericService, "handler");
            // MigrationInvoker
            Object invokerObj = ReflectUtils.getFieldValue(handlerObj, "invoker");
            // MockClusterInvoker
            Object currentAvailableInvokerObj = ReflectUtils.getFieldValue(invokerObj, "currentAvailableInvoker");
            // ServiceDiscoveryRegistryDirectory
            Object directoryObj = ReflectUtils.getFieldValue(currentAvailableInvokerObj, "directory");
            List<InstanceAddressURL> instances = ReflectUtils.getFieldValue(directoryObj, "originalUrls");
            List<RpcProviderMsgDTO.RpcProviderMsg> providerList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(instances)) {
                for (InstanceAddressURL instance : instances) {
                    RpcProviderMsgDTO.RpcProviderMsg msgDTO = new RpcProviderMsgDTO.RpcProviderMsg();
                    msgDTO.setAddress(instance.getInstance().getAddress());
                    providerList.add(msgDTO);
                }
            }

            RpcProviderMsgDTO msgDTO = new RpcProviderMsgDTO();
            msgDTO.setProvider(providerList);

            return msgDTO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取 dubbo 服务
     * @param interfaceClass
     * @param version
     * @param timeout
     * @return
     */
    private GenericService getService(String interfaceClass, String version, Integer timeout) {

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

        // 自定义 keyGenerator
        SimpleReferenceCache cache = SimpleReferenceCache.getCache(
                "DUBBO_CACHE", referenceConfig -> {
                    String iName = referenceConfig.getInterface();
                    if (StringUtils.isBlank(iName)) {
                        Class<?> clazz = referenceConfig.getInterfaceClass();
                        iName = clazz.getName();
                    }
                    if (StringUtils.isBlank(iName)) {
                        throw new IllegalArgumentException("No interface info in ReferenceConfig" + referenceConfig);
                    }
                    String key = BaseServiceMetadata.buildServiceKey(iName, referenceConfig.getGroup(), referenceConfig.getVersion());
                    // 感知超时配置变化
                    Integer timeoutTmp = referenceConfig.getTimeout();
                    if (timeoutTmp != null) {
                        key = key + BaseServiceMetadata.COLON_SEPARATOR + timeoutTmp;
                    }
                    return key;
                });

        //新建ReferenceConfig实例资源消耗很大，里面封装了与注册中心的连接以及与提供者的连接，
        //需要缓存，否则重复生成ReferenceConfig可能造成性能问题并且会有内存和连接泄漏。
        //使用dubbo内置的简单缓存工具类进行缓存
        return cache.get(reference);
    }

}
