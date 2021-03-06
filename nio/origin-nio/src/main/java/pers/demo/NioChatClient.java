package pers.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * NIO 聊天室客户端
 */
public class NioChatClient {

    private final SocketAddress serverAddress
            = new InetSocketAddress("localhost", 8080);

    private Charset charset = Charset.forName("UTF-8");

    private Selector selector;
    private SocketChannel client;

    public NioChatClient() throws IOException {
        this.selector = Selector.open();
        this.client = SocketChannel.open(serverAddress);
        client.configureBlocking(false); // 设置为非阻塞
        client.register(selector, SelectionKey.OP_READ);
    }

    public void session() {
        
        System.out.println("客户端已启动");
        
        // channel是全双工的，因此可以同时读写
        // 从服务器读数据
        new ChatReader().start();
        // 向服务器写数据
        new CharWriter().start();
    }

    private class ChatReader extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    int readyChannels = selector.select();
                    if (readyChannels == 0) continue;
                    // 获取可用通道的集合
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        process(key);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void process(SelectionKey key) throws IOException {

            if (key.isReadable()) {

                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                StringBuffer content = new StringBuffer();
                while (sc.read(buffer) > 0) {
                    buffer.flip();
                    content.append(charset.decode(buffer));
                }
                System.out.println(content.toString());
                key.interestOps(SelectionKey.OP_READ);
            }
        }
    }

    private class CharWriter extends Thread {

        @Override
        public void run() {
            try {
                // 从键盘读取数据输入，然后发送到服务器
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if ("".equals(line)) continue;
                    client.write(charset.encode(line));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new NioChatClient().session();
    }

}
