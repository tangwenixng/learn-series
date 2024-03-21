package com.twx.learn.kafkadynamicconsumer.controller;

import com.twx.learn.kafkadynamicconsumer.service.KafkaListenerCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
    @Autowired
    KafkaListenerCreator kafkaListenerCreator;

    @GetMapping(path = "/create")
    public String create(@RequestParam("topic") String topic) {
        kafkaListenerCreator.createAndRegisterListener(topic);
        return "OK";
    }

    @GetMapping(path = "/stop")
    public String stop(@RequestParam("topic") String topic) {
        kafkaListenerCreator.stopListener(topic);
        return "OK";
    }
}
