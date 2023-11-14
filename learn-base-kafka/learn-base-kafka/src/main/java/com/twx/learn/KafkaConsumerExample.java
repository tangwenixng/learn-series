package com.twx.learn;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerExample {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerExample.class);
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            ClassLoader classLoader = KafkaConsumerExample.class.getClassLoader();
            InputStream resourceAsStream = classLoader.getResourceAsStream("kafka-ssl.properties");
            properties.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        String bootstrapServers = "dev.soyuan.com.cn:9092"; // Kafka 服务器地址
//        String bootstrapServers = "172.26.10.100:9092"; // Kafka 服务器地址
        String topic = "T_TEST_005"; // Kafka 主题名称

        // Kafka 消费者配置
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", StringDeserializer.class.getName());

        AdminClient adminClient = AdminClient.create(properties);
        NewTopic newTopic = new NewTopic(topic, 1, (short) 1);
        CreateTopicsResult result = adminClient.createTopics(Collections.singletonList(newTopic));

        result.values().forEach((key,future) -> {
            log.info("create topic {}: ", key);
            future.whenComplete((r, e) -> {
                if (e != null) {
                    log.error(e.getMessage(), e);
                }
            });
        });
        adminClient.close();

        // 创建 Kafka 消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // 订阅 Kafka 主题
        consumer.subscribe(Collections.singletonList(topic));

        // 消费消息
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                log.info("Received message: key={}, value={}", record.key(), record.value());
            }
        }
    }
}
