# 思路
构建三个服务[ServiceA、ServiceB、ServiceB],
调用链路: this is serviceA-->this is serviceB-->this is serviceC

期望: 在skywalking UI端能展示serviceA的调用链路

# 服务运行配置
在VM Option中增加`-javaagent:/Users/twx/Downloads/skywalking-agent/skywalking-agent.jar`
(这个java包可去[官网](https://www.apache.org/dyn/closer.cgi/skywalking/java-agent/8.10.0/apache-skywalking-java-agent-8.10.0.tgz)
下载，解压缩可得)

在Env variable增加`SW_AGENT_NAME=serviceA`

# 结果

![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20220601111817.png)

从上图可以看出serviceA接口`/sa/`的完整路线

# 日志上报
1. 引入依赖
```xml
<dependency>
  <groupId>org.apache.skywalking</groupId>
  <artifactId>apm-toolkit-logback-1.x</artifactId>
  <version>8.10.0</version>
</dependency>
```
2. 添加logback配置(详情参见服务logback.xml)
```xml
<appender name="console" class="ch.qos.logback.core.ConsoleAppender">
    <!--<encoder>表示对日志进行编码-->
    <encoder class="ch.qos.logback.core.encoder.LayoutWrappingEncoder">
        <layout class="org.apache.skywalking.apm.toolkit.log.logback.v1.x.TraceIdPatternLogbackLayout">
            <Pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%tid] [%thread] %-5level %logger{36} -%msg%n</Pattern>
        </layout>
    </encoder>
</appender>

<appender name="ASYNC" class="ch.qos.logback.classic.AsyncAppender">
    <discardingThreshold>0</discardingThreshold>
    <queueSize>1024</queueSize>
    <neverBlock>true</neverBlock>
    <appender-ref ref="console"/>
</appender>

<appender name="grpc-log" class="org.apache.skywalking.apm.toolkit.log.logback.v1.x.log.GRPCLogClientAppender">
    <encoder class="ch.qos.logback.core.encoder.LayoutWrappingEncoder">
        <layout class="org.apache.skywalking.apm.toolkit.log.logback.v1.x.mdc.TraceIdMDCPatternLogbackLayout">
            <Pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%tid] [%thread] %-5level %logger{36} -%msg%n</Pattern>
        </layout>
    </encoder>
</appender>
```
3. 通过View logs即可查看日志
   ![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20220601151156.png)