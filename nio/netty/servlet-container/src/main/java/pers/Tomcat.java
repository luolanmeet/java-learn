package pers;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import pers.http.Request;
import pers.http.Response;
import pers.http.Servlet;

import java.io.FileInputStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Tomcat {

    private int port = 8080;

    private Map<String, Servlet> servletMapping =  new HashMap<>();

    private Properties properties = new Properties();

    private void init() {

        try {

            // tomcat是用web.xml文件，这里用properties
            // 扫描所有配置的Servlet，然后利用反射创建实例
            String WEB_INF = this.getClass().getResource("/").getPath();
            FileInputStream fis = new FileInputStream(WEB_INF + "web.properties");
            properties.load(fis);

            for (Object k : properties.keySet()) {

                String key = k.toString();
                if (key.endsWith(".url")) {

                    String servletName = key.replaceAll("\\.url$", "");
                    String url = properties.getProperty(key);
                    String className = properties.getProperty(servletName + ".className");
                    Servlet obj = (Servlet) Class.forName(className).newInstance();
                    servletMapping.put(url, obj);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {

        init();
        // boss线程
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // worker线程
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup)
                    // 指定之后创建的channel类型
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel client) throws Exception {
                            client.pipeline()
                                    // HttpResponse编码器
                                    .addLast(new HttpResponseEncoder())
                                    // HttpRequest解码器
                                    .addLast(new HttpRequestDecoder())
                                    // 业务逻辑处理
                                    .addLast(new MyTomcatHandler());
                        }
                    })
                    // 针对主线程的配置，分配线程数最大数量128
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 启动服务器
            ChannelFuture f = server.bind(port).sync();
            System.out.println("servlet 容器已启动，端口：" + this.port);
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public class MyTomcatHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

            // 添加了HttpRequestDecoder，会自动将消息解析成HttpRequest对象
            if (msg instanceof HttpRequest) {

                HttpRequest req = (HttpRequest) msg;

                Request request = new Request(ctx, req);
                Response response = new Response(ctx, req);

                String url = request.getUrl();

                if (servletMapping.containsKey(url)) {
                    servletMapping.get(url).service(request, response);;
                } else {
                    response.write("404");
                }
            }
        }
    }

    public static void main(String[] args) {
        new Tomcat().start();
    }

}
