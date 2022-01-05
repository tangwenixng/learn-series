# 演示FeignClient使用

注意引入这个依赖：
```xml
 <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

使用注解`@EnableFeignClients` :Scans for interfaces that declare they are feign clients (via FeignClient @FeignClient).