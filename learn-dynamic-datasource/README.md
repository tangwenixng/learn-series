# 多数据源集成

1. 引入依赖
```xml
<dependency>
  <groupId>com.baomidou</groupId>
  <artifactId>mybatis-plus-boot-starter</artifactId>
  <version>3.5.1</version>
</dependency>
<dependency>
  <groupId>com.baomidou</groupId>
  <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
  <version>3.5.1</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.21</version>
</dependency>
```

2. 修改配置文件
```yaml
spring:
  datasource:
    dynamic:
      primary: master
      strict: false
      datasource:
        master:
          driver-class-name: com.mysql.cj.jdbc.Driver
          username: root
          password: *****
          url: jdbc:mysql://172.26.1.22:3306/acdm?serverTimezone=Asia/Shanghai&useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=utf8
        slave_1:
          driver-class-name: com.mysql.cj.jdbc.Driver
          username: root
          password: *****
          url: jdbc:mysql://172.26.1.22:3306/test?serverTimezone=Asia/Shanghai&useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=utf8
```
详情参见：https://baomidou.com/pages/a61e1b/

3. 在service实现类或方法上添加@DS注解
```java
@Service
@DS("slave_1")
public class ICompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements ICompanyService {

}
```

4. 测试代码
```java
@Autowired
    private ICompanyService cs;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void component() {
        System.out.println(("----- query master ------"));
        List<User> userList = userMapper.selectList(null);
        userList.stream().forEach(System.out::println);
        System.out.println("----- query slave_1 ------");
        cs.list().stream().forEach(System.out::println);
    }
```