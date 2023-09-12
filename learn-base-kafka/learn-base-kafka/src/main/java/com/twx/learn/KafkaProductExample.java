package com.twx.learn;

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

public class KafkaProductExample {
    private static final Logger log = LoggerFactory.getLogger(KafkaProductExample.class);
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            ClassLoader classLoader = KafkaProductExample.class.getClassLoader();
            InputStream resourceAsStream = classLoader.getResourceAsStream("kafka-ssl.properties");
            properties.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String bootstrapServers = "172.26.10.100:9092"; // Kafka 服务器地址
        String topic = "twx003"; // Kafka 主题名称

        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());

        // 创建Kafka生产者
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, LocalDateTime.now().toString());
            // 发送消息
            RecordMetadata metadata = producer.send(record).get();
            System.out.printf("Message sent to topic=%s, partition=%s, offset=%s%n",
                    metadata.topic(), metadata.partition(), metadata.offset());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
