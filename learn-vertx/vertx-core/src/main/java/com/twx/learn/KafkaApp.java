package com.twx.learn;

import com.twx.learn.cluster.Cluster1Verticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

import java.util.HashMap;
import java.util.Map;

public class KafkaApp {
    public static void main(String[] args) {
        ClusterManager mgr = new ZookeeperClusterManager();
        VertxOptions options = new VertxOptions();
        options.setClusterManager(mgr);
        //集群模式的Vert.x对象
        Vertx.clusteredVertx(options, result -> {
            if (result.succeeded()) {
                System.out.println(Thread.currentThread().getName()+" Kafka Vertx started!");
                init(result.result());
            } else {
                System.out.println(Thread.currentThread().getName()+" Kafka Vertx failed!");
            }
        });
    }

    private static void init(Vertx vertx) {
        Map<String, String> config = new HashMap<>();
        config.put("bootstrap.servers", "172.26.10.100:9094");
        config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("group.id", "vertx");
        config.put("auto.offset.reset", "earliest");
        config.put("enable.auto.commit", "false");

        // 使用消费者和 Apache Kafka 交互
        KafkaConsumer<String, String> consumer = KafkaConsumer.create(vertx, config);
        consumer.subscribe("a-single-topic")
                .onSuccess(v -> System.out.println(Thread.currentThread().getName() +" subscribed"))
                .onFailure(cause  -> System.out.println("Could not subscribe " + cause.getMessage()));
        consumer.handler(record -> {
            System.out.println(Thread.currentThread().getName() + " Processing key=" + record.key() + ",value=" + record.value() +
                    ",partition=" + record.partition() + ",offset=" + record.offset());
            consumer.commit()
                    .onSuccess(v -> System.out.println(Thread.currentThread().getName() +" Committed ok"))
                    .onFailure(cause -> System.out.println("Error while committing " + cause.getMessage()));
        });
    }
}
