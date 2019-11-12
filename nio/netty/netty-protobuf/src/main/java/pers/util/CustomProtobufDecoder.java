package pers.util;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iplus.protobuf.LoginReqProto;
import com.iplus.protobuf.MsgProto;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 解码器
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

        log.debug("dateType: {}", dataType);
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

        if(dataType == 0x01) {
            out.add(LoginReqProto.LoginReq.getDefaultInstance().getParserForType().parseFrom(array, offset, length));
        } else if(dataType == 0x03) {
            out.add(MsgProto.Msg.getDefaultInstance().getParserForType().parseFrom(array, offset, length));
        }
    }

}
