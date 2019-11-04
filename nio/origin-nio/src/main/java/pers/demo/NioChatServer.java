package pers.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
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
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel client = server.accept();
            // 设置为非阻塞模式
            client.configureBlocking(false);

            // 注册选择器，并设置为读取模式，收到一个连接请求
            // 然后起一个SocketChannel，并注册到selector上，之后这个连接的数据
            // 就由这个SocketChannel处理
            client.register(selector, SelectionKey.OP_READ);

            // 将此对应的channel设置为准备接受其他客户端请求
            key.interestOps(SelectionKey.OP_ACCEPT);
        }

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

    private void broadCast(SocketChannel client, String content) throws IOException {
        // 广播数据到所有的SocketChannel中
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();
            if (targetChannel instanceof  SocketChannel && targetChannel != client) {
                SocketChannel target = (SocketChannel) targetChannel;
                target.write(charset.encode(content));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new NioChatServer().listen();
    }

}
