package com.twx.learn.rocketmq.base;

import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.time.LocalDateTime;

public class BaseAclProducer {
    public static void main(String[] args) {

        String accessKey = "rocketmq2";
        String secretKey = "12345678";

        AclClientRPCHook rpcHook = new AclClientRPCHook(new SessionCredentials(accessKey, secretKey));

        DefaultMQProducer producer = new DefaultMQProducer("TEST",  rpcHook);
//        DefaultMQProducer producer = new DefaultMQProducer("XIY-IMF-FLIGHT");
        //设置发送超时时间
        producer.setSendMsgTimeout(8000);
        //设置NameServer地址
        producer.setNamesrvAddr("localhost:9876");

        try {
            //启动
            producer.start();

            for (int i = 0; i < 1; i++) {
                //创建消息：参数1-topic名称 参数2-tag名称 参数3-消息内容(byte[])
                Message message = new Message("XIY-IMF-FLIGHT", "tag2", LocalDateTime.now().toString().getBytes());
                try {
                    //发送
                    producer.send(message);
//                    producer.sendOneway(message);
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
