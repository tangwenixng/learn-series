# 演示网关的使用

1、引入依赖
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

2、设置自动发现服务
```yaml
spring:
  cloud:
    gateway:
      discovery:
        locator:
          #enables DiscoveryClient gateway integration
          enabled: true
          #lower case serviceId in predicates and filters, defaults to false
          lowerCaseServiceId: true
```
关键是`enabled=true`