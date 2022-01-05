# 搭建注册中心

引入依赖：
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

配置：
```yaml
eureka:
  instance:
    #The hostname if it can be determined at configuration time (otherwise it will be guessed from OS primitives).
    hostname: localhost
  client:
    #Indicates whether or not this instance should register its information with eureka server for discovery by others.
    register-with-eureka: false
    #indicates whether this client should fetch eureka registry information from eureka server
    fetch-registry: false
```
注意一下，`register-with-eureka` 和 `fetch-registry` 属性就好了