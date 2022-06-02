package com.twx.learn.learnspringbootkafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;

import java.util.List;

@SpringBootApplication
public class LearnSpringBootKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnSpringBootKafkaApplication.class, args);
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("topic1")
                .partitions(10)
                .replicas(1)
                .build();
    }

//    @KafkaListener(groupId = "mac-twx",topicPartitions = {
//            @TopicPartition(topic = "topic2",
//                    partitionOffsets = {@PartitionOffset(partition = "0",initialOffset = "0")})
//    })
//    @KafkaListener(topics = "topic2",groupId = "mac-twx-3")
//    public void listen(ConsumerRecord<?,?> record) {
//        System.out.println("topic:"+record.topic()+" partition:"+record.partition()+" offset:"+record.offset()+"value:"+record.value());
//    }

    @KafkaListener(topics = "topic2")
    public void receive(List<ConsumerRecord<?, ?>> records, Acknowledgment acknowledgment) {
        System.out.println("receive...");
//        System.out.println("topic:"+record.topic()+" partition:"+record.partition()+" offset:"+record.offset()+" value:"+record.value());
        for (ConsumerRecord<?, ?> record : records) {
            System.out.println("topic:"+record.topic()+" partition:"+record.partition()+" offset:"+record.offset()+" value:"+record.value());
        }
        acknowledgment.acknowledge();
    }


    /*@Bean
    public ApplicationRunner runner(KafkaTemplate<String, String> template) {
        return args -> {
            template.send("topic1", "test");
        };
    }*/
}
