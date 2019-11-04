package pers.buffer;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Buffer 的 capacity position limit
 */
public class BufferAttributeDemo {
    
    public static void main(String[] args) throws Exception {
    
        String path = System.getProperty("user.dir") + "/nio/origin-nio/src/main/resources/text.txt";
        
        // 创建文件IO
        FileInputStream fis = new FileInputStream(path);
        
        // 获取文件的操作通道
        FileChannel fc = fis.getChannel();
        
        // 分配一个10byte的缓冲区，也就是 new byte[10]
        ByteBuffer buffer = ByteBuffer.allocate(10);
        output("初始化", buffer);
    
        // 数据读入缓冲区，写进缓冲区时，position增加
        fc.read(buffer);
        output("数据写入缓冲区", buffer);
        
        // 准备操作之前，确定操作范围，limit = position，position = 0
        buffer.flip();
        output("flip()", buffer);
        
        // 从缓冲区读数据，读position到limit之间的数据 remaining = limit - position
        while (buffer.remaining() > 0) {
            byte b = buffer.get();
            System.out.print((char) b);
        }
        System.out.println();
        output("get()", buffer);
        
        // 重置状态
        buffer.clear();
        output("clear()", buffer);
    
        fc.close();
        fis.close();
    }
    
    /**
     * 打印缓冲区的状态
     */
    private static void output(String step, ByteBuffer buffer) {
    
        System.out.println(step + ":");
        // 容量
        System.out.print("  capacity:" + buffer.capacity());
        // 当前操作数据的位置，游标
        System.out.print("  position:" + buffer.position());
        // 锁定值,flip，数据操作范围只能在position~limit之间
        System.out.print("  limit:" + buffer.limit());
        System.out.println();
        System.out.println();
    }
    
}
