package pers.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.io.UnsupportedEncodingException;

public class Response {

    // Nio中SocketChannel的封装
    private ChannelHandlerContext ctx;

    private HttpRequest req;

    public Response(ChannelHandlerContext ctx, HttpRequest req) {
        this.ctx = ctx;
        this.req = req;
    }

    public void write(String out) throws UnsupportedEncodingException {

        if (out == null || out.length() == 0) {
            return ;
        }

        // 设置 http 协议以及请求头信息
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(out.getBytes("UTF-8")));

        response.headers().set("Content-Type", "text/html");
        ctx.write(response);
        ctx.flush();
        ctx.close();
    }

}
