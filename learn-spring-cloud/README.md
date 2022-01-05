# 2020-11-19
参考http://blog.didispace.com/spring-cloud-learning/ Dalston版进行学习构建

因为上面教程过于陈旧，有些依赖以及注解已经过时，比如引入eureka-client。

教程中使用的是
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka</artifactId>
</dependency>
```
现在新版本使用的是
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```
---

教程中使用的网关zuul,现在spring cloud使用gateway代替：
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```
---

注意：
1. 父pom.xml引入的是`Hoxton.SR9`，关于版本之间的区别，查看链接 https://spring.io/projects/spring-cloud 

2. `Hoxton.SR9` 对应的`spring.boot`版本是`2.3.1.RELEASE`,可以查看`Hoxton.SR9`中的pom文件获取

![](https://soyuan-yuque.oss-cn-shanghai.aliyuncs.com/picgo/20201119094310.png)

---

# 阅读顺序
- eureka-server
- eureka-client
- eureka-consumer
- eureka-ribbon
- eureka-feign
- config-server
- config-client
- api-gateway