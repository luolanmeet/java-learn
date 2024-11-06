package pers.demo2;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * @auther ken.ck
 * @date 2024/10/25 23:28
 */
public class Server extends AbstractVerticle {

    public static void main(String[] args) {
        // http://127.0.0.1:8080/
        Vertx.vertx().deployVerticle(new Server());
    }

    public void start() {

        HttpServer httpServer = vertx.createHttpServer();

        // route 的顺序是有关系的，请求被接管之后，不会再进入其他 route
        Router router = Router.router(vertx);

        // http://127.0.0.1:8080/hello?name=cck
        router.route("/hello").handler(ctx -> {
            String name = ctx.request().getParam("name");
            HttpServerResponse response = ctx.response();
            response.putHeader("content-type", "text/plain")
                    .end("hello " + name);
        });

        // 静态页面
        // http://127.0.0.1:8080/
        router.route().handler(StaticHandler.create("demo2"));

        // 默认路由
        router.route().handler(ctx -> {
            HttpServerResponse response = ctx.response();
            response.putHeader("content-type", "text/plain")
                    .end("Hello from Vert.x!");
        });

        httpServer.requestHandler(router).listen(8080);
    }

}
