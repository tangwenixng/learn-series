package com.twx.learn.kafkadynamicconsumer.rocket;

import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
public class BootDynamicRocketConsumer implements RocketMQListener<String>{

    @Override
    public void onMessage(String s) {
        System.out.println("Dynamic Rocket Message: "+ s);
    }
}
