package pers.buffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 直接缓冲区（实现零拷贝）
 *
 * 分配的是非堆内存，在堆外进行内存分配，相对于堆内存，它的分配和回收速度会慢一些，
 * 但是写入或是从Channel中读取时，由于少了一次内存复制（内核空间到用户空间），
 * 速度会比堆内存快。
 *
 * 使用场景：
 *   在I/O通信线程读写缓冲区使用DirectByteBuffer,后端业务消息的解编码模块使用HeapByteBuffer
 */
public class DirectBuffer {
    
    /**
     * 读取文件，拷贝文件
     */
    public static void main(String[] args) throws Exception {
        
        String path = System.getProperty("user.dir") + "/nio/origin-nio/src/main/resources/text.txt";
        FileInputStream inFis = new FileInputStream(path);
        FileChannel inFc = inFis.getChannel();
    
        path = System.getProperty("user.dir") + "/nio/origin-nio/src/main/resources/textCopy.txt";
        FileOutputStream outFis = new FileOutputStream(path);
        FileChannel outFc = outFis.getChannel();
    
        // 创建直接缓冲区，使用allocateDirect，其他操作和普通缓冲区相同
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        // 是否是直接缓冲区
        System.out.println(buffer.isDirect());
        
        while (inFc.read(buffer) != -1) {
            buffer.flip();
            outFc.write(buffer);
            buffer.clear();
        }
    
        inFc.close();
        inFis.close();
        outFc.close();
        outFis.close();
    }

}
