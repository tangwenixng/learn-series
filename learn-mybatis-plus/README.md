## Mybatis-plus配置

重点： 解决save时乱码、解决mybatis映射时 日期格式问题

为什么不用mybatis-plus-boot-starter?
答： 因为后面要配置多数据源，用boot-starter不会配（O(∩_∩)O哈哈~）。。。
配置多数据源时，要让spring-boot不自动注入data-source和sqlSessionFactory。

## FAQ

1、 出现了如下BUG

```
org.mybatis.spring.MyBatisSystemException: 
nested exception is org.apache.ibatis.reflection.ReflectionException:
 There is no getter for property named 'statement' in 'class org.apache.tomcat.jdbc.pool.StatementFacade$StatementProxy'
 
at org.apache.ibatis.reflection.Reflector.getGetInvoker(Reflector.java:419)
at org.apache.ibatis.reflection.MetaClass.getGetInvoker(MetaClass.java:164)
at org.apache.ibatis.reflection.wrapper.BeanWrapper.getBeanProperty(BeanWrapper.java:162)
at org.apache.ibatis.reflection.wrapper.BeanWrapper.get(BeanWrapper.java:49)
at org.apache.ibatis.reflection.MetaObject.getValue(MetaObject.java:122)
at org.apache.ibatis.reflection.MetaObject.getValue(MetaObject.java:119)
at com.baomidou.mybatisplus.plugins.PerformanceInterceptor.intercept(PerformanceInterceptor.java:81)
 	
```

注意我贴出来的异常栈的最后一行 `PerformanceInterceptor`这个拦截器,我在mybatis 配置文件中引入了它
```java
sqlSessionFactoryBean.setPlugins(new Interceptor[]{
    new PaginationInterceptor(),
    new PerformanceInterceptor(),
    new OptimisticLockerInterceptor()
});
```

为什么我要引入它呢？因为官方例子就引入了啊（呃呃呃...)，我不知道它干什么用的，所以先删掉，同时
`OptimisticLockerInterceptor` 也删掉，也不知道干什么用的

所以现在就只引入一个分页拦截器

```java
sqlSessionFactoryBean.setPlugins(new Interceptor[]{
    new PaginationInterceptor()
});
```

再运行就不会报错了!


2、 update对象时，持久化中文乱码

> 配置文件的url 忘记加 characterEncoding=utf-8

正确的URL应该如下：

> url: jdbc:mysql://localhost:3306/pos?useSSL=false&useUnicode=true&characterEncoding=utf-8


3、mybatis 日期转换问题

mysql中的时间格式是DATETIME,Dog中定义的是java.util.Date

```java
@Data
public class Dog {
private Integer id;
private String name;
private Date time;
}
```

```xml
<select id="findDogs" resultType="Dog">
    SELECT id,username as name,created_time as time
    FROM t_manager
</select>
```

```java
List<Dog> dogs = testMapper.findDogs();
System.out.println(dogs.get(0).getTime());
```
mybatis转换得到的Dog对象的time字段，打印出来的格式是 `Tue Dec 12 09:46:12 CST 2017`

序列化后传回前端的格式是一段字符串 `1513043172000`(长这样)

但是前端希望得到的是 yyyy-MM-dd HH:mm:ss 这样的格式...所以

序列化对象时格式化该对象，**在time上加上 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")** (这是jackson的注解)

```java
@Data
public class Dog {
    private Integer id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
}
```

虽然打印出来的时间格式还是 `Tue Dec 12 09:46:12 CST 2017` 这样，但是传回前端的格式已经变成了 '2017-12-12 01:46:12'

---

我们在测试用例中用Jackson序列化Dog对象，并打印出来看看结果：
```java
List<Dog> dogs = testMapper.findDogs();
System.out.println(dogs.get(0).getTime());


ObjectMapper mapper = new ObjectMapper();
String jsonStr = null;
try {
    jsonStr = mapper.writeValueAsString(dogs.get(0));
    System.out.println(jsonStr);
} catch (JsonProcessingException e) {
    e.printStackTrace();
}

//输出结果
//Tue Dec 12 09:46:12 CST 2017
//{"id":1,"name":"admin","time":"2017-12-12 01:46:12"}
```

总结： 在Date属性上加上**@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")**就可以了！！！

