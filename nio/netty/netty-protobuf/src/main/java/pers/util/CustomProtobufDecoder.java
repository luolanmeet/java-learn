package pers.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import pers.protobuf.LoginReqProto;
import pers.protobuf.MsgProto;

import java.util.List;

/**
 * 解码器
 * 规定了所有传输的对象的第一个属性都是一个 int32 type = x;
 * 用于标识对象类型
 */
@Sharable
public class CustomProtobufDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {

        // 标记读索引
        msg.markReaderIndex();

        //第一个字节为数据类型，这里只需要下一个字节的值
        msg.readByte();
        byte dataType = msg.readByte();

        // 重置读索引
        msg.resetReaderIndex();

        final byte[] array;
        final int offset;
        final int length = msg.readableBytes();
        if (msg.hasArray()) {
            array = msg.array();
            offset = msg.arrayOffset() + msg.readerIndex();
        } else {
            array = new byte[length];
            msg.getBytes(msg.readerIndex(), array, 0, length);
            offset = 0;
        }

        // 根据数据类型，解码成对应的protobuf对象
        if(dataType == 0x01) {
            out.add(LoginReqProto.LoginReq.getDefaultInstance().getParserForType().parseFrom(array, offset, length));
        } else if(dataType == 0x03) {
            out.add(MsgProto.Msg.getDefaultInstance().getParserForType().parseFrom(array, offset, length));
        }
    }

}
