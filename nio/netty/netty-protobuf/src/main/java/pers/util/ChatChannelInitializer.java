package pers.util;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import pers.handler.ChatWebSocketServerHandler;

import java.util.List;

public class ChatChannelInitializer extends ChannelInitializer<SocketChannel> {
    
    private ChatWebSocketServerHandler handler;
    
    public ChatChannelInitializer(ChatWebSocketServerHandler handler) {
        this.handler = handler;
    }
    
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        
        ch.pipeline().addLast(
                new HttpServerCodec(),
                new HttpObjectAggregator(64 * 1024),
                new WebSocketServerCompressionHandler(),
                new WebSocketServerProtocolHandler("/chat", null, true),
                new ProtobufVarint32FrameDecoder(),
                new MessageToMessageDecoder<WebSocketFrame>() {  // 解码 WebSocketFrame --> BinaryWebSocketFrame
                    @Override
                    protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg,
                            List<Object> out) throws Exception {
                        if (msg instanceof BinaryWebSocketFrame) {
                            ByteBuf buf = ((BinaryWebSocketFrame) msg).content();
                            out.add(buf);
                            buf.retain();
                        }
                    }
                },
                new CustomProtobufDecoder(),
                new MessageToMessageEncoder<MessageLiteOrBuilder>() { // 编码 MessageLiteOrBuilder --> BinaryWebSocketFrame
                    @Override
                    protected void encode(ChannelHandlerContext ctx, MessageLiteOrBuilder msg,
                            List<Object> out) throws Exception {
                        if (msg instanceof MessageLite) {
                            ByteBuf bf = Unpooled.wrappedBuffer(((MessageLite) msg).toByteArray());
                            out.add(new BinaryWebSocketFrame(bf));
                            return;
                        }
                        // 这个是允许业务代码中直接把Protobuf的Builder对象返回
                        if (msg instanceof MessageLite.Builder) {
                            ByteBuf bf = Unpooled.wrappedBuffer(
                                    ((MessageLite.Builder) msg).build().toByteArray());
                            out.add(new BinaryWebSocketFrame(bf));
                        }
                    }
                },
                new ProtobufVarint32LengthFieldPrepender(),
                handler);
    }

}
