package pers.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * Reactor模式
 *
 * 多路复用器Selector负责轮询注册在自己身上的那些channel，
 * 调用selectedKeys时返回有事件响应的channel。
 * channel可以是ServerSocketChannel（接受连接的channel）
 * 也可以是SocketChannel（类似与一个客户端句柄）
 *
 * NIO 聊天室服务端
 */
public class NioChatServer {

    private int port = 8080;
    private Charset charset = Charset.forName("UTF-8");

    private Selector selector;

    public NioChatServer() throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(this.port));
        server.configureBlocking(false);
        this.selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器已启动，监听端口：" + this.port);
    }

    public void listen() throws IOException {

        while (true) {
            int wait = selector.select();
            if (wait == 0) continue;
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                process(key);
            }
        }
    }

    private void process(SelectionKey key) throws IOException {

        if (key.isAcceptable()) {
            
            // OP_ACCEPT只能是ServerSocketChannel
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            // 接受客户端连接，并产生一个SocketChannel
            SocketChannel client = server.accept();
            // 设置为非阻塞模式
            client.configureBlocking(false);

            // 注册到selector上，之后客户端和服务端的通信由这个SocketChannel处理，
            // 注册是会创建SelectionKey对象
            client.register(selector, SelectionKey.OP_READ);

            // 用于接收连接的channel继续准备接受其他客户端连接请求
            key.interestOps(SelectionKey.OP_ACCEPT);
        }

        // OP_READ是客户端的操作
        if (key.isReadable()) {

            // 返回该SelectionKey对应的 Channel，其中有数据需要读取
            SocketChannel client = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            StringBuffer content = new StringBuffer();
            try {
                while (client.read(buffer) > 0) {
                    buffer.flip();
                    content.append(charset.decode(buffer));
                }
                // 将对应的channel设置为准备下一次接收数据
                key.interestOps(SelectionKey.OP_READ);
            } catch (Exception e) {
                key.cancel();
                if (key.channel() != null) {
                    key.channel().close();
                }
                e.printStackTrace();
            }

            broadCast(client, content.toString());
        }
    }

    // 广播数据到所有的SocketChannel中
    private void broadCast(SocketChannel client, String content) throws IOException {
        for (SelectionKey key : selector.keys()) {
            Channel channel = key.channel();
            // ServerSocketChannel也注册在selector上，所有要过滤掉
            // channel != client 不广播给发送消息的客户端
            if (channel instanceof  SocketChannel && channel != client) {
                SocketChannel target = (SocketChannel) channel;
                target.write(charset.encode(content));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new NioChatServer().listen();
    }

}
