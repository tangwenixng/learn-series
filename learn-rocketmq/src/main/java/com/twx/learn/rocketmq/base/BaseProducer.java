package com.twx.learn.rocketmq.base;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.time.LocalDateTime;

public class BaseProducer {
    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("test-group");
        //设置发送超时时间
        producer.setSendMsgTimeout(8000);
        //设置NameServer地址
        producer.setNamesrvAddr("localhost:9876");
        try {
            //启动
            producer.start();

            for (int i = 0; i < 1; i++) {
                //创建消息：参数1-topic名称 参数2-tag名称 参数3-消息内容(byte[])
                Message message = new Message("topic1", "tag2", LocalDateTime.now().toString().getBytes());
                try {
                    //发送
//                    producer.send(message);
                    producer.sendOneway(message);
                    System.out.println("Message sent successfully.");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }
}
