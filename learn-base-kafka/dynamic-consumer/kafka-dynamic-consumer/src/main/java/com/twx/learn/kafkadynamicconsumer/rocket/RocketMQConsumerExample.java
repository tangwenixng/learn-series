package com.twx.learn.kafkadynamicconsumer.rocket;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

public class RocketMQConsumerExample {
    public static void main(String[] args) throws Exception {
        // 创建一个消费者实例
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_group");
        // 设置Name Server地址
        consumer.setNamesrvAddr("localhost:9876");
        // 订阅Topic和Tag
        consumer.subscribe("topic1", "*");
        // 注册消息监听器
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                // 处理接收到的消息
                System.out.println("Received message: " + new String(msg.getBody()));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        // 启动消费者
        consumer.start();

        System.out.println("Consumer started.");
    }
}
