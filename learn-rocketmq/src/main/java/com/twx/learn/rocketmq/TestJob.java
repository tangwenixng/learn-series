package com.twx.learn.rocketmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twx.learn.rocketmq.entity.NodeEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

//@Component
@Slf4j
public class TestJob implements InitializingBean, DisposableBean {

    private static Integer counter = 0;

    private final String topic = "XIY-SIMP-TURNAROUNDTUNING";

    private ObjectMapper objectMapper;

    private DefaultMQProducer producer;

    public void init() {
        String nameServerAddress = "10.226.0.174:8200";
        // 设置RocketMQ的生产者组名
        String producerGroup = "hyzh";
        // 设置RocketMQ的Topic名称

        objectMapper = new ObjectMapper();

        String accessKey = "SIMP";
        String secretKey = "h837I@9h/r.dh07y9YC+PD2j@4.1oY9!-%cFW@TjuE-8/Va#tfK=6L5JaKyIbo80";

        AclClientRPCHook rpcHook = new AclClientRPCHook(new SessionCredentials(accessKey, secretKey));
        // 创建生产者实例
        producer = new DefaultMQProducer(producerGroup, rpcHook);
        producer.setNamesrvAddr(nameServerAddress);
        producer.setUseTLS(true);

        try {
            //启动
            producer.start();
            log.info("init rocketmq producer success....");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    @Override
    public void destroy() {
        if (producer != null) {
            log.info("shutdown rocketmq producer success....");
            producer.shutdown();
        }
    }

    @Scheduled(fixedDelay = 1, initialDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void run() {
        String messageContent = null;
        try {
            messageContent = objectMapper.writeValueAsString(new NodeEntity(String.valueOf(counter)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (StringUtils.isEmpty(messageContent)) {
            throw new RuntimeException("error occur");
        }

        log.info("Send=>" + messageContent);

        for (int i = 0; i < 1; i++) {
            //创建消息：参数1-topic名称 参数2-tag名称 参数3-消息内容(byte[])
            Message message = new Message(topic, messageContent.getBytes());
            try {
                //发送
                SendResult send = producer.send(message);
                System.out.println("Message sent successfully." + send.getMsgId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        counter++;
        if (counter >= 31) {
            counter = 0;
        }
    }


}
