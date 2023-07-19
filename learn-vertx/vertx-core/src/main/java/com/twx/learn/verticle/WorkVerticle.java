package com.twx.learn.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;

public class WorkVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        //输出: vert.x-worker-thread-0 异步调用WorkVerticle start...
        //可知分配给了一个worker线程执行
        System.out.println(Thread.currentThread().getName() + " 异步调用WorkVerticle start...");
        JsonObject config = config();
        System.out.println("部署时传入的配置: " + config);
    }

    @Override
    public void stop() throws Exception {
        System.out.println(Thread.currentThread().getName() + " 同步调用WorkVerticle stop...");
    }
}
