package com.twx.learn.rocketmq.base;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class BasePullConsumer {
    public static void main(String[] args) throws Exception {
        // 创建一个消费者实例,需要指定消费者组
        DefaultLitePullConsumer consumer = new DefaultLitePullConsumer("consumer_group");
        // 设置Name Server地址
        consumer.setNamesrvAddr("localhost:9876");

        // 订阅主题和标签
        consumer.subscribe("topic1", "*");

        // 启动消费者
        consumer.start();

        // 拉取消息
        while (true) {
            List<MessageExt> messages = consumer.poll(1000);
            for (MessageExt message : messages) {
                // 处理接收到的消息
                System.out.println("BasePull message: " + new String(message.getBody()));
            }
        }
    }
}
