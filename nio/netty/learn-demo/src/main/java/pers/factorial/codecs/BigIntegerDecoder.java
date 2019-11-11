package pers.factorial.codecs;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.math.BigInteger;
import java.util.List;

/**
 * 解码器 自定义协议：'F' + 数据长度 + 数据
 */
public class BigIntegerDecoder extends ByteToMessageDecoder {
    
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        
        // 等待接受到足够的数据，'F'+数据长度（int）等于5个字节
        // 因此至少需要读取到5个字节
        if (in.readableBytes() < 5) {
            return ;
        }
    
        // 要开始读数据，设置读索引
        in.markReaderIndex();
        
        int magicNumber = in.readUnsignedByte();
        // 看是否符合协议规范
        if (magicNumber != 'F') {
            // 重置读索引
            in.resetReaderIndex();
            throw new CorruptedFrameException("Invalid magic number: " + magicNumber);
        }
    
        // 读取要接收的数据的长度
        int dataLength = in.readInt();
        if (dataLength > in.readableBytes()) {
            // 解码器内部的累积缓冲区未接收到足够的数据
            // 重置读索引
            in.resetReaderIndex();
            return ;
        }
    
        // 全部数据接收完毕，读取数据
        byte[] decoded = new byte[dataLength];
        in.readBytes(decoded);
    
        // 向out添加一个对象，表示成功解码一条消息（累积缓冲区中读取过的部分会丢弃）
        out.add(new BigInteger(decoded));
    }
    
}
