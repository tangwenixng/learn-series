package com.twx.learn.kafkadynamicconsumer.controller;

import com.twx.learn.kafkadynamicconsumer.service.KafkaListenerCreator;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rocket")
public class RocketController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping(path = "/create")
    public String create(@RequestParam("topic") String topic) {
        DefaultLitePullConsumer consumer = new DefaultLitePullConsumer();
        rocketMQTemplate.setConsumer(new DefaultLitePullConsumer());
        return "OK";
    }


}
