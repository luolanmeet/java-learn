package pers.buffer;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 内存映射缓冲区
 * 也使用非堆内存
 */
public class MappedBuffer {
    
    public static void main(String[] args) throws Exception {
    
        String path = System.getProperty("user.dir") + "/nio/origin-nio/src/main/resources/textCopy.txt";
        RandomAccessFile raf = new RandomAccessFile(path, "rw");
    
        FileChannel fc = raf.getChannel();
    
        // 把缓冲区和文件系统进行映射
        // 操作缓冲区的内容，文件内容也会一共改变
        // MapMode.PRIVATE 专用模式，可读可写，不过修改的内容不会写入文件
        MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
    
        buffer.put("write by MappedBuffer".getBytes());
    
        fc.close();
        raf.close();
    }

}
