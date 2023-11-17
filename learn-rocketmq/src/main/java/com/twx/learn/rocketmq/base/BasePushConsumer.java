package com.twx.learn.rocketmq.base;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

public class BasePushConsumer {
    public static void main(String[] args) throws Exception {
        // 创建一个消费者实例,需要指定消费者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_group");
        // 设置Name Server地址
        consumer.setNamesrvAddr("localhost:9876");
        // 订阅Topic和Tag
        consumer.subscribe("topic1", "*");
        consumer.setConsumeThreadMin(10);
        consumer.setConsumeThreadMax(20);
        // 注册消息监听器
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Thread [" + threadName + "] start consumer...");
            for (MessageExt msg : msgs) {
                // 处理接收到的消息
                System.out.println("["+threadName + "]  Received tag: "+msg.getTags()+" ,message: " + new String(msg.getBody()));
            }
            System.out.println("Thread [" + threadName + "] end consumer...");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        // 启动消费者
        consumer.start();

        System.out.println("Consumer started.");
    }
}
