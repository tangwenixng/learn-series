package com.twx.learn.rocketmq.base;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

        //模拟外部API修改订阅topic
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(60);
                System.out.println("开始变更订阅...");
                consumer.unsubscribe("topic1");
                consumer.subscribe("topic2", "*");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (MQClientException e) {
                throw new RuntimeException(e);
            }
        }).start();

        // 拉取消息
        while (true) {
            List<MessageExt> messages = consumer.poll(1000);
            for (MessageExt msg : messages) {
                // 处理接收到的消息
                System.out.println(Thread.currentThread().getName()+ " BasePull topic:" + msg.getTopic() + ", tag: " + msg.getTags() +
                        " ,message: " + new String(msg.getBody()));
            }
        }
    }
}
