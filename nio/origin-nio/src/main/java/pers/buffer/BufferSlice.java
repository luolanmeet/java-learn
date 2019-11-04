package pers.buffer;

import java.nio.ByteBuffer;

/**
 * 缓冲区分片
 */
public class BufferSlice {
    
    public static void main(String[] args) {
    
        ByteBuffer buffer = ByteBuffer.allocate(10);
        
        for (int i = 0; i < 10; i++) {
            buffer.put((byte)i);
        }
    
        // 缓冲区分片，分片后的缓冲区和原缓冲区数据共享
        buffer.position(3);
        buffer.limit(7);
        ByteBuffer sliceBuffer = buffer.slice();
        
        for (int i = 0; i < sliceBuffer.capacity(); i++) {
            sliceBuffer.put(i, (byte)(sliceBuffer.get(i) * 10));
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());
        while (buffer.remaining() > 0) {
            System.out.print(buffer.get() + " ");
        }
    }

}
