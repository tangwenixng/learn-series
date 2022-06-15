

1. 引入依赖

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
  <groupId>com.xuxueli</groupId>
  <artifactId>xxl-job-core</artifactId>
  <version>2.2.0</version>
</dependency>
```

2. 修改配置

```yaml
xxl:
  job:
    accessToken:
    admin:
      addresses: http://172.26.1.80:8080/xxl-job-admin
    executor:
      appname: learn-xxl-job
      ### xxl-job executor registry-address: default use address to registry ,
      ### otherwise use ip:port if address is null
      address:
      ip:
      #默认是9999
      port: 9999
      logpath: ./log/xxl-job/jobhandler
      logretentiondays: 30
server:
  port: 1101

```

3. 创建配置类

```java
@Configuration
public class XxlJobConfig {
    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;

    @Value("${xxl.job.accessToken}")
    private String accessToken;

    @Value("${xxl.job.executor.appname}")
    private String appname;

    @Value("${xxl.job.executor.address}")
    private String address;

    @Value("${xxl.job.executor.ip}")
    private String ip;

    @Value("${xxl.job.executor.port}")
    private int port;

    @Value("${xxl.job.executor.logpath}")
    private String logPath;

    @Value("${xxl.job.executor.logretentiondays}")
    private int logRetentionDays;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        System.out.println(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
        xxlJobSpringExecutor.setAppname(appname);
        xxlJobSpringExecutor.setAddress(address);
        xxlJobSpringExecutor.setIp(ip);
        xxlJobSpringExecutor.setPort(port);
        xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setLogPath(logPath);
        xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);
        return xxlJobSpringExecutor;
    }
}
```

4. 来个demo

```java
@Component
public class DemoJob extends IJobHandler {
    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("demoJobHandler")
    public ReturnT<String> execute(String s) throws Exception {
        XxlJobLogger.log("This is a demo job.args: {}", s);
        Thread.sleep(5 * 1000L);
        return SUCCESS;
    }
}
```

---

1. 访问xxl-job管理界面，【**执行器管理->新增执行器**】

![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20220615110820.png)

如上图所示，

- **AppName**：填入后台`xxl.job.executor.appname`配置的参数(本demo是**learn-xxl-job**)，

- **名称**：随意
- **注册方式**：自动注册

2. 新增任务，见下图

![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20220615111319.png)

- **执行器**: 选择刚刚添加的
- **cron表达式**：自定义时间
- **JobHandler**: 填入后端代码`@XxlJob("demoJobHandler")`里的这个值
- **任务参数**：随意

3. 开始任务

![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20220615111530.png)

---

项目源码：https://github.com/tangwenixng/learn-series/blob/master/learn-xxl-job/README.md