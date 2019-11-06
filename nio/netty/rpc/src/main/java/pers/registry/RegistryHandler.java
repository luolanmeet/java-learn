package pers.registry;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import pers.protocol.InvokerProtocol;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RegistryHandler extends ChannelInboundHandlerAdapter {

    //用保存所有可用的服务
    public static ConcurrentHashMap<String, Object> registryMap = new ConcurrentHashMap<>();

    //保存所有相关的服务类
    private List<String> classNames = new ArrayList<>();

    public RegistryHandler() {
        scannerClass("pers.provider");
        doRegister();
    }

    private void scannerClass(String packageName) {

        URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            //如果是一个文件夹，继续递归
            if(file.isDirectory()){
                scannerClass(packageName + "." + file.getName());
            }else{
                classNames.add(packageName + "." + file.getName().replace(".class", "").trim());
            }
        }
    }

    private void doRegister() {
        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className);
                Class<?> inter = clazz.getInterfaces()[0];
                registryMap.put(inter.getName(), clazz.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        InvokerProtocol request = (InvokerProtocol) msg;
        Object result = null;
        Object obj = registryMap.get(request.getClassName());
        if (obj != null) {
            Method method = obj.getClass().getMethod(request.getMethodName(), request.getParames());
            result = method.invoke(obj, request.getValuse());
        }

        ctx.write(result);
        ctx.flush();
        ctx.close();
    }

}
