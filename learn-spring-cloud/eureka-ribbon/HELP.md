# 演示Ribbon使用

需要单独引入ribbon：
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
</dependency>
```

然后在`restTemplate`上是使用注解：
```java
@Bean
@LoadBalanced
public RestTemplate restTemplate() {
    return new RestTemplate();
}
```
此时使用`restTemplate`访问服务就能自动做到负载均衡了

如下所示：
```java
String url = "http://eureka-client/dc";
return "consumer_"+restTemplate.getForObject(url,String.class);
```