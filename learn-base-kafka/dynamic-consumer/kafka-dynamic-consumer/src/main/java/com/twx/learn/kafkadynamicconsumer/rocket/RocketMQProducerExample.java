package com.twx.learn.kafkadynamicconsumer.rocket;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.time.LocalDateTime;

public class RocketMQProducerExample {
    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("test-group");
        producer.setSendMsgTimeout(8000);
        producer.setNamesrvAddr("localhost:9876");
        try {
            producer.start();

            Message message = new Message("topic1", "tag1", LocalDateTime.now().toString().getBytes());
            try {
                producer.send(message);
                System.out.println("Message sent successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                producer.shutdown();
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
}
