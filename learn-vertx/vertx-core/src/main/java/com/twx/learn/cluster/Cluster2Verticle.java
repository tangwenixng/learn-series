package com.twx.learn.cluster;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class Cluster2Verticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        vertx.setTimer(10000, id -> {
            //发送消息并接收应答;request等同于send，但是它可以接收应答
            vertx.eventBus().request("news.uk.sport", "Yay! Someone kicked a ball", reply -> {
                if (reply.succeeded()) {
                    System.out.println("Received reply: " + reply.result().body());
                }
            });
        });
        startPromise.complete();
    }
}
