package com.twx.learn.example;

import java.util.concurrent.TimeUnit;

public class Bootstrap {
    public static void main(String[] args) throws InterruptedException {
            init();
            startElect();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Trigger shutdown hook...");
                ZkUtils.close();
            }));

            TimeUnit.MINUTES.sleep(30);

    }

    private static void init() {
        ZkUtils.create(ZkPaths.WORKERS);
        ZkUtils.create(ZkPaths.TASKS);
        ZkUtils.create(ZkPaths.ASSIGN);
    }

    private static void startElect() {
        new Master().createMaster();
    }
}
