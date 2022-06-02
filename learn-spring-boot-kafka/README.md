# 集成步骤
1. 查看https://spring.io/projects/spring-kafka版本对应情况
   ![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20220602153327.png)
根据kafka-server(client)版本选择对应的spring-kafka版本

假如我的kafka server: 3.1.0,那么在pom中引入:
```xml
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
    <version>2.8.6</version>
</dependency>
```

2. 在application.yml中添加如下参数
```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      auto-offset-reset: latest
```
可能需要你手动调整默认配置

3. 消费topic

- 普通消费
```java
@KafkaListener(topics = "topic2",groupId = "mac-twx-3")
public void listen(ConsumerRecord<?,?> record) {
    System.out.println("topic:"+record.topic()+" partition:"+record.partition()+" offset:"+record.offset()+"value:"+record.value());
}
```
- 指定分区和偏移量消费
```java
@KafkaListener(groupId = "mac-twx",topicPartitions = {
        @TopicPartition(topic = "topic2",
                partitionOffsets = {@PartitionOffset(partition = "0",initialOffset = "0")})
})
public void listen(ConsumerRecord<?,?> record) {
    System.out.println("topic:"+record.topic()+" partition:"+record.partition()+" offset:"+record.offset()+"value:"+record.value());
}
```

- 手动提交
  - 在application.yml中设置参数spring.kafka.consumer.enable-auto-commit=false、spring.kafka.listener.ack-mode=manual
```java
@KafkaListener(topics = "topic2",groupId = "mac-twx")
 public void receive(ConsumerRecord<?, ?> record, Acknowledgment acknowledgment) {
     System.out.println("topic:"+record.topic()+" partition:"+record.partition()+" offset:"+record.offset()+" value:"+record.value());
     acknowledgment.acknowledge();
 }
```
   然后调用方法`acknowledgment.acknowledge();`进行手动提交
   
- 批量消费
  - 在application.yml中设置参数spring.kafka.listener.type=batch（默认是single）和spring.kafka.consumer.max-poll-records=3（每批最大条数）
```java
@KafkaListener(topics = "topic2",groupId = "mac-twx")
 public void receive(List<ConsumerRecord<?, ?>> records, Acknowledgment acknowledgment) {
     System.out.println("receive...");
     for (ConsumerRecord<?, ?> record : records) {
         System.out.println("topic:"+record.topic()+" partition:"+record.partition()+" offset:"+record.offset()+" value:"+record.value());
     }
     acknowledgment.acknowledge();
 }
```