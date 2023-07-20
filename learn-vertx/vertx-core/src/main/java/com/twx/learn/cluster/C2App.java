package com.twx.learn.cluster;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

public class C2App {
    public static void main(String[] args) {
        ClusterManager mgr = new ZookeeperClusterManager();
        VertxOptions options = new VertxOptions();
        options.setClusterManager(mgr);
        //集群模式的Vert.x对象
        Vertx.clusteredVertx(options, result -> {
            if (result.succeeded()) {
                System.out.println("Clustered 2 Vertx started!");
                Vertx vertx = result.result();
                vertx.deployVerticle(new Cluster2Verticle());
            } else {
                System.out.println("Clustered 2 Vertx failed!");
            }
        });
    }
}
