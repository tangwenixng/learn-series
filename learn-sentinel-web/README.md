

# 使用Redis限制Http接口访问总次数

## 配置

- 引入redis依赖

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
spring2.x需要引入这个，否则会报下图的错误
<dependency>
  <groupId>org.apache.commons</groupId>
  <artifactId>commons-pool2</artifactId>
  <version>2.6.2</version>
</dependency>
```

![](https://soyuan-yuque.oss-cn-shanghai.aliyuncs.com/picgo/20200617140910.png)

- 引入SpringBoot一些基本依赖

  ```xml
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
  
  <!-- 热加载  按Ctrl+F9 重新编译即可 -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
  </dependency>
  
  <!-- 阿里巴巴sentinel,不需要的话可以不引入 -->
  <dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
    <version>2.2.1.RELEASE</version>
  </dependency>
  ```

  ### application.yml 

  ```yaml
  server:
    port: 9000
    servlet:
      context-path: /
  
  spring:
    application:
      name: skeleton
    profiles:
      active: dev
    mvc:
      static-path-pattern: /**
    resources:
      static-locations: classpath:/META-INF/resources/, classpath:/resources/,classpath:/static/, classpath:/public/,file:${soyuan.thumbnailDir}
  #    不需要sentinel的话也可以不配
    cloud:
      sentinel:
        transport:
          port: 8719
          dashboard: localhost:8080
  #  配置redis      
    redis:
      host: 127.0.0.1
      port: 6379
      database: 0
      lettuce:
        shutdown-timeout: 200ms
        pool:
          max-active: 7
          max-idle: 7
          min-idle: 2
          max-wait: -1ms
  
  ```

  配一下redis地址即可。

  sentinel不需要的话，就删掉吧。

## service

```java
@Service
public class IProjectMetaServiceImpl implements IProjectMetaService {

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void register(String key, Integer count) {
        ValueOperations<String, String> valOps = redisTemplate.opsForValue();
        valOps.set(key, String.valueOf(count));
    }

    @SentinelResource(value = "projectMetaTest",blockHandler = "testBlockHandler")
    @Override
    public String test(String key) {
        ValueOperations<String, String> valOps = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(key)) {
            Long decrement = valOps.decrement(key);
            if (decrement <= 0) {
                throw new AccessLimitException("调用次数已用完...");
            }
            return key+"您的业务逻辑处理完毕";
        }
        throw new AccessLimitException("未注册用户，不能访问...");
    }

    public String testBlockHandler(String name, BlockException be) {
        System.out.println("testBlockHandler");
        return "error";
    }
}

```

`register(String key, Integer count)` 模拟后台管控系统给xxx设定访问次数。

**`String test(String key)` 每访问一次，将次数减1，当次数小于0时，提示调用次数已用完。如果不存在key,则表示当前用户未注册**。

方法上的注解`@SentinelResource(value = "projectMetaTest",blockHandler = "testBlockHandler")` 是阿里sentinel的入门用法，不必关心。

## 控制接口层

```java
@RestController
@RequestMapping("/project")
public class IndexController {

    @Autowired
    private IProjectMetaService service;

    @GetMapping("/test")
    public String test(@RequestParam("key")String key) {
        return service.test(key);
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRqo rqo) {
        service.register(rqo.getKey(), rqo.getCount());
        return "注册成功";
    }

    public static class RegisterRqo {
        String key;
        Integer count;

        public RegisterRqo() {
        }

        public String getKey() {
            return key;
        }

        public Integer getCount() {
            return count;
        }
    }
}

```

很简单，就不说了。



##  测试



![](https://soyuan-yuque.oss-cn-shanghai.aliyuncs.com/picgo/20200617162055.png)

![](https://soyuan-yuque.oss-cn-shanghai.aliyuncs.com/picgo/20200617162125.png)



使用ab测试，结果

![](https://soyuan-yuque.oss-cn-shanghai.aliyuncs.com/picgo/20200617161941.png)

注意，失败8次。（均是因为超过调用次数导致）



## 仓库地址

https://github.com/tangwenixng/learn-sentinel-web


