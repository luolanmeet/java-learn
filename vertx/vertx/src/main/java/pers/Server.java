package pers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class Server extends AbstractVerticle {
    public static void main(String[] args) {
        // http://127.0.0.1:8080/
        Vertx.vertx().deployVerticle(new Server());
    }

    public void start() {
        vertx.createHttpServer().requestHandler(req -> {
            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello from Vert.x!");
        }).listen(8080);
    }

}