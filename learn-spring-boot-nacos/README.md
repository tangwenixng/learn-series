# spring-boot集成nacos

## 配置中心
1. 引入依赖
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.3.12.RELEASE</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>

<dependency>
    <groupId>com.alibaba.boot</groupId>
    <artifactId>nacos-config-spring-boot-starter</artifactId>
    <version>0.2.10</version>
</dependency>
```

注意：截止2022-06-10,`nacos-config-spring-boot-starter`只适配SpringBoot 2.3.x版本,2.4.x以上是不支持的(虽然spring-boot最新版本已经到2.7了)

否则会报`org.springframework.boot.context.properties.ConfigurationBeanFactoryMetadata not found`错误！

关于兼容性的说明: 
> Nacos2.0的服务端完全兼容1.X客户端。Nacos2.0客户端由于使用了gRPC，无法兼容Nacos1.X服务端，请勿使用2.0以上版本客户端连接Nacos1.X服务端。

所以在引入`nacos-config-spring-boot-starter`版本的时候注意下版本。

2. 增加配置application.yml
```yaml
nacos:
  config:
    server-addr: 127.0.0.1:8848
    username: nacos
    password: nacos
```

3. 在启动类上加上注解
```java
@NacosPropertySource(dataId = "learn-nacos", autoRefreshed = true, type = ConfigType.YAML)
```
注意: `type = ConfigType.YAML` (根据配置文件类型设置)

4. 测试代码
```java
@RequestMapping("/config")
@RestController
public class ConfigController {

    @NacosValue(value = "${app.token:12343}", autoRefreshed = true)
    private String token;

    @GetMapping("/")
    public String config() {
        return token;
    }
}
```

5. 访问`http://localhost:8787/config/`
6. 在nacos配置界面修改配置后，再次访问 `http://localhost:8787/config/` 观察返回值是否变化

---
## 服务注册
1. 引入依赖
```xml
<dependency>
    <groupId>com.alibaba.boot</groupId>
    <artifactId>nacos-discovery-spring-boot-starter</artifactId>
    <version>0.2.10</version>
</dependency>
```
2. 修改配置
```yaml
nacos:
  discovery:
    server-addr: 127.0.0.1:8848
    username: nacos
    password: nacos

spring:
  application:
    name: learn-spring-boot-nacos
```
注意: 增加`spring.application.name`作为服务名

3. 增加注册代码
```java
@NacosInjected
private NamingService namingService;

@Value("${spring.application.name}")
private String applicationName;

@Value("${server.port}")
private Integer port;

@Override
public void run(String... args) throws Exception {
    namingService.registerInstance(applicationName, "127.0.0.1", port);
}
```

4. 查看nacos管理界面
   ![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20220610103614.png)

---
感悟：
1. 使用spring-boot集成注册中心，需要手动编写服务注册代码。这一块体验不是很好。感觉还是使用spring-cloud-starter比较智能！

源码地址:  https://github.com/tangwenixng/learn-series/learn-spring-boot-nacos