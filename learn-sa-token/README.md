# spring-boot集成sa-token

1. 引入依赖
```xml
<dependency>
    <groupId>cn.dev33</groupId>
    <artifactId>sa-token-spring-boot-starter</artifactId>
    <version>1.28.0</version>
</dependency>

<dependency>
    <groupId>cn.dev33</groupId>
    <artifactId>sa-token-dao-redis-jackson</artifactId>
    <version>1.28.0</version>
</dependency>
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
    <version>2.8.1</version>
</dependency>
```
引入了redis用于存储token

redis存储结构见下图：
![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20220608172130.png)
value类型是cn.dev33.satoken.session.SaSession


![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20220608172203.png)

2. 配置如下：（具体参数含义参见官方文档）
```yaml
sa-token:
  #比如上图中的redis key=satoken:login:session:10001
  #第一列即为token-name
  token-name: satoken
  #token的长久有效期(单位:秒) 默认30天, -1代表永久
  timeout: 2592000
  #token临时有效期
  activity-timeout: -1
  #是否允许同一账号并发登录
  is-concurrent: true
  #在多人登录同一账号时，是否共用一个token
  is-share: false
  #token风格(默认可取值：uuid、simple-uuid、random-32、random-64、random-128、tik)
  token-style: uuid
  #是否打印操作日志
  is-log: false

spring:
  redis:
    database: 1
    host: 47.96.98.119
    port: 6379
    password: ****
    timeout: 15s
    connect-timeout: 10s
    lettuce:
      pool:
        max-active: 30
        max-wait: -1
        max-idle: 10
        min-idle: 0
```

3. 配置拦截器(可选)
```java
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaAnnotationInterceptor())
                .addPathPatterns("/**");
    }
}
```
配置了拦截器后，才可以在Controller层使用注解，比如`@SaCheckLogin、@SaCheckRole("/super-admin1")`

注意：SaAnnotationInterceptor拦截器只能在Controller层使用。如果想在任意层(Service)使用，请参考官方文档https://sa-token.dev33.cn/doc/index.html#/plugin/aop-at

**实际开发中最好还是使用[路由拦截](https://sa-token.dev33.cn/doc/index.html#/use/route-check) 或者 [全局过滤器](https://sa-token.dev33.cn/doc/index.html#/up/global-filter)**

因为SaAnnotationInterceptor源码中是对Controller的每一个方法都检查是否存在注解`@SaCheckLogin、@SaCheckRole("")`来判断是否要进行拦截的。


4. 实现接口StpInterface（重要）
```java
@Component
public class StpInterfaceImpl implements StpInterface {
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> list = new ArrayList<>();
        list.add("101");
        list.add("user-add");
        list.add("user-delete");
        list.add("user-update");
        list.add("user-get");
        list.add("article-get");
        return list;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> list = new ArrayList<>();
        list.add("admin");
        list.add("super-admin");
        return list;
    }
}
```
**实际项目中，应该使用orm框架从数据库获取角色和权限**,而且可以考虑使用缓存存储角色和权限。