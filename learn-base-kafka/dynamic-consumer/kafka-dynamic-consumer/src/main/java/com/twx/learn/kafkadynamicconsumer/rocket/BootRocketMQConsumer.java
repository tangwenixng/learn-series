package com.twx.learn.kafkadynamicconsumer.rocket;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "topic1", consumerGroup = "consumer_group")
public class BootRocketMQConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        // 处理接收到的消息
        System.out.println("Boot Received message: " + message);
    }
}
