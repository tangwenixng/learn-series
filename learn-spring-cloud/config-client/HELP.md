# 演示ConfigClient使用

1、引入依赖
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```
2、bootstrap中配置config server地址
```yaml
spring:
#  cloud:
#    config:
#        env: default
#        label: master
#        uri: http://localhost:9000
  cloud:
    config:
      discovery:
        enabled: true
        service-id: config-server
      profile: default
      label: master
```
注释掉的部分使用的是 url 地址，激活的部分使用的是discovery方式


# actuator使用
在`application.yml`中配置`management exposure`
```yaml
management:
  endpoints:
    web:
      exposure:
        include: env,beans,health,info,refresh
```

POST http://localhost:9006/actuator/refresh 刷新配置
同时在controller上添加注解@RefreshScope