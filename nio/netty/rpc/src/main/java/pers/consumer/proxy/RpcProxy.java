package pers.consumer.proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import pers.protocol.InvokerProtocol;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 返回目标服务接口的代理对象
 * 在代理方法中利用netty发起远程通信，
 * 通信时使用约定的协议
 */
public class RpcProxy {

    public static<T> T create(Class<?> clazz) {

        Class<?>[] interfaces = clazz.isInterface() ? new Class[]{clazz} : clazz.getInterfaces();
        MethodProxy methodProxy = new MethodProxy(clazz);

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), interfaces, methodProxy);
    }

    private static class MethodProxy implements InvocationHandler {

        private Class clazz;

        public MethodProxy(Class clazz) {
            this.clazz = clazz;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            // 如果是一个具体的类，则直接调用方法
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(args);
            }

            return rpcInvoke(proxy, method, args);
        }

        private Object rpcInvoke(Object proxy, Method method, Object[] args) {

            // 封装请求
            InvokerProtocol msg = InvokerProtocol.builder()
                    .className(this.clazz.getName())
                    .methodName(method.getName())
                    .parames(method.getParameterTypes())
                    .valuse(args)
                    .build();

            final RpcProxyHandler consumerHandler = new RpcProxyHandler();

            // 这里只需要一个线程组
            NioEventLoopGroup group = new NioEventLoopGroup();

            Bootstrap bootstrap = new Bootstrap();

            try {
                bootstrap.group(group)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .handler(new ChannelInitializer<SocketChannel>() {

                            protected void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline()
                                        // 自定义协议解析器
                                        /**
                                         *  maxFrameLength：数据帧的最大长度
                                         *  lengthFieldOffset：长度字段的偏移量，就是长度字段在数据中的位置
                                         *  lengthFieldLength：长度字段的长度，长度字段是Int，那就是4，Long就是8
                                         *  lengthAdjustment：添加到长度字段的补偿值
                                         *  initialBytesToStrip：解码后跳过的字节数
                                         */
                                        .addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4))
                                        // 自定义协议编码器
                                        .addLast("frameEncoder", new LengthFieldPrepender(4))
                                        // 对象参数类型解码器
                                        .addLast("encoder", new ObjectEncoder())
                                        // 对象参数类型编码器
                                        .addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)))
                                        .addLast("handler", consumerHandler);
                            }
                        });

                ChannelFuture future = bootstrap.connect("localhost", 8080).sync();
                future.channel().writeAndFlush(msg).sync();

                // 在这里阻塞，直到连接关闭
                future.channel().closeFuture().sync();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                group.shutdownGracefully();
            }

            return consumerHandler.getResponse();
        }
    }

}
