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