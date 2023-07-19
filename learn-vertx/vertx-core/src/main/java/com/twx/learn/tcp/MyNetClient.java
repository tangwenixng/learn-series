package com.twx.learn.tcp;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;

public class MyNetClient {
    private Vertx vertx;

    public MyNetClient(Vertx vertx) {
        this.vertx = vertx;
    }

    public void connect() {
        NetClientOptions options = new NetClientOptions().setConnectTimeout(10000);
        NetClient client = vertx.createNetClient(options);
        client.connect(4321, "localhost", res -> {
            if (res.succeeded()) {
                System.out.println("MyNetClient Connected!");
                client.close();
            } else {
                System.out.println("Failed to connect: " + res.cause().getMessage());
            }
        });

    }
}
