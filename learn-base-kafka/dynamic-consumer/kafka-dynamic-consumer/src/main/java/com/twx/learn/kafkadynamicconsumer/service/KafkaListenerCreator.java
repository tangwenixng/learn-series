package com.twx.learn.kafkadynamicconsumer.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class KafkaListenerCreator {
    private static final String kafkaGroupId = "gp-twx";
    private static final String kafkaListenerId = "kafkaListenerId-";
    private static final AtomicLong endpointIdIndex = new AtomicLong(1);
    private static final Map<String, String> topicIds = new ConcurrentHashMap<>();

    private final KafkaListenerEndpointRegistry registry;
    private final KafkaListenerContainerFactory factory;

    public KafkaListenerCreator(KafkaListenerEndpointRegistry registry, KafkaListenerContainerFactory factory) {
        this.registry = registry;
        this.factory = factory;
    }

    public void createAndRegisterListener(String topic) {
        KafkaListenerEndpoint listener = createKafkaListenerEndpoint(topic);
        registry.registerListenerContainer(listener, factory, true);
        topicIds.put(topic, listener.getId());
    }

    public void stopListener(String topic) {
        String id = topicIds.remove(topic);
        if (StringUtils.hasText(id)) {
            MessageListenerContainer mlc = registry.unregisterListenerContainer(id);
            if (mlc != null) {
                mlc.stop();
            }
        }
    }

    private KafkaListenerEndpoint createKafkaListenerEndpoint(String topic) {
        MethodKafkaListenerEndpoint<String, String> kafkaListenerEndpoint =
                createDefaultMethodKafkaListenerEndpoint(topic);
        kafkaListenerEndpoint.setBean(new KafkaTemplateListener());
        try {
            kafkaListenerEndpoint.setMethod(KafkaTemplateListener.class.getMethod("onMessage", ConsumerRecord.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Attempt to call a non-existent method " + e);
        }
        return kafkaListenerEndpoint;
    }

    private MethodKafkaListenerEndpoint<String, String> createDefaultMethodKafkaListenerEndpoint(String topic) {
        MethodKafkaListenerEndpoint<String, String> kafkaListenerEndpoint = new MethodKafkaListenerEndpoint<>();
        kafkaListenerEndpoint.setId(generateListenerId());
        kafkaListenerEndpoint.setGroupId(kafkaGroupId);
        kafkaListenerEndpoint.setAutoStartup(true);
        kafkaListenerEndpoint.setTopics(topic);
        kafkaListenerEndpoint.setMessageHandlerMethodFactory(new DefaultMessageHandlerMethodFactory());
        return kafkaListenerEndpoint;
    }
    private String generateListenerId() {
        return kafkaListenerId + endpointIdIndex.getAndIncrement();

    }
}
