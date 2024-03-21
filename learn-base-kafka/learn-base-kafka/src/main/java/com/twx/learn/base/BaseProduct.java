package com.twx.learn.base;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseProduct {
    private static final Logger log = LoggerFactory.getLogger(BaseProduct.class);
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            ClassLoader classLoader = BaseProduct.class.getClassLoader();
            InputStream resourceAsStream = classLoader.getResourceAsStream("kafka-ssl-base.properties");
            properties.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String topic = "NodeMsg"; // Kafka 主题名称

        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());

        // 创建Kafka生产者
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
            for (int i = 0; i < 10; i++) {
                ProducerRecord<String, String> record = new ProducerRecord<>(topic, LocalDateTime.now().toString());
                // 发送消息
                RecordMetadata metadata = producer.send(record).get();
                System.out.printf("Message sent to topic=%s, partition=%s, offset=%s%n",
                        metadata.topic(), metadata.partition(), metadata.offset());
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
