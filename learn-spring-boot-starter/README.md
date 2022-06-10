我们在使用一些官方starter的时候，通过依赖树，总能看到`xxx-spring-boot-autoconfiguration`这样的依赖。以`nacos-config-spring-boot-starter`为例，见下图：

![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20220610145500.png)

实际上真正起自动配置作用的就是这个`xxx-spring-boot-autoconfiguration`依赖。

`xxx-config-spring-boot-starter`只不过是起到组合依赖的作用。比如上图中`nacos-config-spring-boot-starter`引入了`nacos-client`、`nacos-spring-context`等。为了佐证我刚刚的说法，我们看看`nacos-config-spring-boot-starter`有什么内容（见下图）：

![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20220610145801.png)

可见一个class文件都没有！所以它只是起到一个组合依赖的作用！

---

我们自定义starter也遵从这个套路。首先看一下项目结构：

![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20220610150232.png)

- `as-api`: **该模块只有一个接口`IAsCamera`**, 用于模拟外部依赖。主要是为了演示说明`@ConditionalOnClass`和`@ConditionalOnMissingBean`的作用。
- `as-application`:  这是一个普通的web项目，用于**模拟使用自定义的as-spring-boot-starter**。
- **as-spring-boot-autoconnfigure: 这是自定义starter的核心。待会重点将。**
- `as-spring-boot-starter`:  组合as-spring-boot-autoconnfigure和as-api依赖

---

我们先来看`as-spring-boot-autoconnfigure`模块。（重点）

1. 引入依赖

```xml
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>2.4.2</version>
  <relativePath/> <!-- lookup parent from repository -->
</parent>

<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-autoconfigure</artifactId>
  <optional>true</optional>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-logging</artifactId>
  <optional>true</optional>
</dependency>
<!--将被@ConfigurationProperties注解的类的属性注入到元数据-->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-configuration-processor</artifactId>
  <optional>true</optional>
  <scope>provided</scope>
</dependency>

<dependency>
  <groupId>com.twx.learn</groupId>
  <artifactId>as-api</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <optional>true</optional>
</dependency>
```

说明：

- **所有的依赖都是`<optional>true</optional>`**，即当真正的应用比如as-application引用`As-spring-boot-autoconnfigure`时，这些依赖不会自动传递。
- `as-api`这个依赖也是`<optional>true</optional>`，引入这个依赖主要是为了演示`@ConditionalOnClass`的作用。

2. 创建`AsProperties.java`。我们在`idea->application.yml`中输入属性时，会自动跳出属性提示（演示这个功能）。

```java
@ConfigurationProperties(value = AsProperties.AS_PREFIX)
public class AsProperties {
    public static final String AS_PREFIX = "soyuan.as";
    private boolean enable;
    private String env;
    private boolean devMode;
    private String proxyUrl;
    private int maxWeight;
  ..
}
```

3. 创建**AsAutoConfiguration.java**(重点)

```java
@Configuration
@ConditionalOnClass(IAsCamara.class)
@ConditionalOnProperty(prefix = AsProperties.AS_PREFIX, value = "enable", havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(AsProperties.class)
public class AsAutoConfiguration {
    private Logger log = LoggerFactory.getLogger(AsAutoConfiguration.class);

    private AsProperties asProperties;

    public AsAutoConfiguration(AsProperties asProperties) {
        this.asProperties = asProperties;
    }

    @Bean
    @ConditionalOnMissingBean(IAsCamara.class)
    public IAsCamara asCamara() {
        log.info("执行As自动配置逻辑...");
        log.info("devMode: {}",asProperties.isDevMode());
        return new BjAsCamera();
    }
}
```

- `@ConditionalOnClass(IAsCamara.class)`: 当项目中有IAsCamera类的时候此配置才起作用。
- `@ConditionalOnProperty(prefix = AsProperties.AS_PREFIX, value = "enable", havingValue = "true", matchIfMissing = false)`：当application.yml中有`soyuan.as.enabel=true`时此配置才起作用
- `@EnableConfigurationProperties(AsProperties.class)`：加了此注解才能将属性配置注入
- `@ConditionalOnMissingBean(IAsCamara.class)`：当项目中没有`IAsCamara bean`的时候才会生成`new BjAsCamera()`。待会举个例子。
- **`log.info("执行As自动配置逻辑...");`后续我们通过观察这句日志来判断自动配置有没有被执行！**



4. **(关键)**在resources目录下创建META-INF/spring.factories文件,内容如下：

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.twx.learn.asspringbootautoconfigure.AsAutoConfiguration
```

5. `mvn clean install`- maven打包安装即可

---

接下来简单过一下`as-spring-boot-starter`：

1. 只有一个pom.xml文件，内容如下：

```xml
<properties>
  <maven.compiler.source>8</maven.compiler.source>
  <maven.compiler.target>8</maven.compiler.target>
</properties>

<dependencies>
  <dependency>
    <groupId>com.twx.learn</groupId>
    <artifactId>as-spring-boot-autoconfigure</artifactId>
    <version>0.0.1-SNAPSHOT</version>
  </dependency>

  <dependency>
    <groupId>com.twx.learn</groupId>
    <artifactId>as-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
  </dependency>
</dependencies>
```

2. 然后`mvn clean install`- maven打包安装即可

---

最后，我们从使用者角度（即as-application）来看看。首先来个完整的演示：

1. 引入依赖

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
  <groupId>com.twx.learn</groupId>
  <artifactId>as-spring-boot-starter</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

2. 修改配置

```yaml
soyuan:
  as:
    enable: true
    dev-mode: true
    proxy-url: http://localhost:8787/test
```

注意: 此时的**soyuan.as.enable=true**

3. 启动入口

```java
@SpringBootApplication
public class AsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsApplication.class, args);
    }
}
```

4. 执行结果

![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20220610153455.png)



附注：看到**执行As自动配置逻辑...**这行日志说明`BjAsCamera`已经被注入到beanFactory中了。这时我们就可以使用`@Autowired private IAsCamera asCamera`



---

接下来我们验证`@ConditionalOnProperty(prefix = AsProperties.AS_PREFIX, value = "enable", havingValue = "true", matchIfMissing = false)`

1. 修改application.yml

```yaml
soyuan:
  as:
    enable: false
```

2. 运行入口程序，结果如下图

![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20220610154056.png)

**没有找到我们的目标日志。说明自动配置未起作用。**

---

接下来验证`@ConditionalOnMissingBean(IAsCamara.class)`

1. 首先复原application.yml

```yaml
soyuan:
  as:
    enable: true
```

2. 新增SzAsCamera.java

```java
package com.twx.learn.asapplication;

import com.twx.learn.IAsCamara;
import org.springframework.stereotype.Component;

@Component
public class SzAsCamera implements IAsCamara {
    @Override
    public String name() {
        return "SzAsCamera";
    }
}
```

3. 运行入口程序，结果如下图

![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20220610154056.png)

**没有找到我们的目标日志。说明自动配置未起作用。**

解释一下：由于我们自定义了`IAsCamara`的实现类，即spring容器中已经有类型为IAsCamara的bean了，所以不满足条件`@ConditionalOnMissingBean(IAsCamara.class)`。自然就不会运行配置了。

---

最后再验证`@ConditionalOnClass(IAsCamara.class)`

1. 首先删除`SzAsCamera.java`
2. 修改pom.xml，排除`as-api`依赖

```xml
<dependency>
  <groupId>com.twx.learn</groupId>
  <artifactId>as-spring-boot-starter</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <exclusions>
    <exclusion>
      <groupId>com.twx.learn</groupId>
      <artifactId>as-api</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```

3. 运行入口程序，结果如下图

![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20220610154056.png)

**没有找到我们的目标日志。说明自动配置未起作用。**

---

示例源码：https://github.com/tangwenixng/learn-series/learn-spring-boot-starter/README.md