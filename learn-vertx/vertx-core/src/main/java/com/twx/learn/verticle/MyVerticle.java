package com.twx.learn.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;

public class MyVerticle extends AbstractVerticle {

    private HttpServer server;

    @Override
    public void start() throws Exception {
        super.start();
        System.out.println(Thread.currentThread().getName() + " 同步调用MyVerticle start...");
        //在verticle中触发定时器
        vertx.setTimer(10000, id -> System.out.println("MyVerticle: "+Thread.currentThread().getName() + " timer fired!"));
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        //可以稍微做一些耗时的操作
        //但是不能阻塞等待其他的Verticle部署完成
        //输出: vert.x-eventloop-thread-1 异步调用start...(可知分配给了一个eventloop线程执行)
        System.out.println(Thread.currentThread().getName()+" 异步调用MyVerticle start...");
        server = vertx.createHttpServer().requestHandler(request -> request.response()
                .putHeader("content-type", "text/plain")
                .end("Hello from Vert.x!"));

        // Now bind the server:
        server.listen(8080, res -> {
            if (res.succeeded()) {
                startPromise.complete();
            } else {
                startPromise.fail(res.cause());
            }
        });
        start();
    }

    @Override
    public void stop() throws Exception {
        System.out.println(Thread.currentThread().getName()+" 同步调用MyVerticle stop...");
    }
}
