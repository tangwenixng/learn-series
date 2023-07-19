package com.twx.learn.tcp;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;

public class MyNetServer {
    private Vertx vertx;

    public MyNetServer(Vertx vertx) {
        this.vertx = vertx;
    }

    public void start() {
        NetServerOptions options = new NetServerOptions().setPort(4321);
        NetServer server = vertx.createNetServer(options);
        server.exceptionHandler(e -> {
            System.out.println("Error: " + e.getMessage());
        });
        server.connectHandler(socket -> {
            System.out.println("Incoming connection!");
            socket.handler(buffer -> {
                System.out.println("I received some bytes: " + buffer.length());
                // Write a string in UTF-8 encoding
                socket.write("Hello world");
            });
            socket.closeHandler(v -> {
                System.out.println("The socket has been closed");
            });
        });
        server.listen(res -> {
            if (res.succeeded()) {
                System.out.println("Server is now listening! Actual Port: " + res.result().actualPort());
            } else {
                System.out.println("Failed to bind!");
            }
        });
    }
}
