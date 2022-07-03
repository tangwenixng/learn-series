package com.twx.learn.learnspringbootkafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class KafkaReBalanceDemo {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "demo");
        props.put("enable.auto.commit", "false");
//        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Collections.singletonList("Kafka-ReBalance-Demo"), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> collection) {
                System.out.println("Start onPartitionsRevoked...");
                for (TopicPartition tp : collection) {
                    System.out.println("topci: "+tp.topic()+" partition: "+tp.partition());
                }
                System.out.println("End onPartitionsRevoked...");
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> collection) {
                System.out.println("Start onPartitionsAssigned...");
                for (TopicPartition tp : collection) {
                    System.out.println("topci: "+tp.topic()+" partition: "+tp.partition());
                }
                System.out.println("End onPartitionsAssigned...");
            }
        });

        try {
            while (true) {
                // 拉取消息
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("topic:"+record.topic()+" partition:"+record.partition()+" offset:"+record.offset()+" value:"+record.value());
                }
                consumer.commitAsync((map, e) -> {
                    if (e != null) {
                        e.printStackTrace();
                    }
                });
            }
        } finally {
            consumer.close();
        }
    }
}
