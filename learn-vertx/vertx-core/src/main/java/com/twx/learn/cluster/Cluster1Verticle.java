package com.twx.learn.cluster;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class Cluster1Verticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        vertx.eventBus().consumer("news.uk.sport",
                message -> {
                    System.out.println("Consumer1===> I have received a message: " + message.body());
                    //应答消息
                    message.reply("From Consumer1===> how interesting!");
                });
        startPromise.complete();
    }
}
