# 演示配置中心的使用

1、引入依赖：
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>
```

2、使用注解`@EnableConfigServer`

3、在bootstrap.yml中配置`spring.cloud.config`
```yaml
spring:
  cloud:
    config:
      server:
        # because github create default branch name from master to main
        # please checkout a new branch named master manually
        git:
          uri: https://github.com/tangwenixng/learn-sc-config-server
          skipSslValidation: true
          username: twx843571091@gmail.com
          password: 474913884ynh
          # Time (in seconds) between refresh of the git repository
          refresh-rate: 30
```

注意：github 默认创建分支名称是main(以前是master),所以需要手动创建master分支

浏览器中访问：http://localhost:9000/application/master
或者 http://localhost:9000/client/dev/master
可获取配置文件内容

访问配置信息的URL与配置文件的映射关系如下：

- /{application}/{profile}[/{label}]
- /{application}-{profile}.yml
- /{label}/{application}-{profile}.yml
- /{application}-{profile}.properties
- /{label}/{application}-{profile}.properties

