package pers.factorial.codecs;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.math.BigInteger;

public class NumberEncoder extends MessageToByteEncoder<Number> {
    
    @Override
    protected void encode(ChannelHandlerContext ctx, Number msg, ByteBuf out) throws Exception {
    
        // 按照协议 'F' + 数据长度 + 数据  发送数据
    
        BigInteger v;
        if (msg instanceof BigInteger) {
            v = (BigInteger) msg;
        } else {
            v = new BigInteger(String.valueOf(msg));
        }
    
        byte[] data = v.toByteArray();
        
        out.writeByte((byte)'F');
        out.writeInt(data.length);
        out.writeBytes(data);
    }
    
}
