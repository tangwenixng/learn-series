package com.twx.learn.rocketmq.boot;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BootProducer implements InitializingBean {

    @Autowired
    private RocketMQTemplate rocketMq;

    public void sendMsg() {
        rocketMq.convertAndSend("topic1", LocalDateTime.now().toString());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        sendMsg();
        System.out.println("Boot Send Successfully.");
    }
}
