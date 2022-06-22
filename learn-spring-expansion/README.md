# spring扩展

## ImportBeanDefinitionRegistrar(动态注入bean)

大家在使用spring-boot的时候应该遇到过很多`@Enable*`的注解吧,比如`@EnableScheduling`。通过使用`@Enable*`注解，就能自动启用很多功能。

举一个更为熟知的例子,比如`@SpringBootApplication`,看一下代码:

```java
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {}


@AutoConfigurationPackage
@Import(AutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(AutoConfigurationPackages.Registrar.class)
public @interface AutoConfigurationPackage {}

static class Registrar implements ImportBeanDefinitionRegistrar, DeterminableImports {}
```



`@SpringBootApplication->@EnableAutoConfiguration->@AutoConfigurationPackage->AutoConfigurationPackages.Registrar`

---



我们关注一下`@AutoConfigurationPackage`注解和`AutoConfigurationPackages.Registrar.class`,同时和我们自定义的`@EnablePerson`注解(后面会详细介绍)比对一下：

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(AutoConfigurationPackages.Registrar.class)
public @interface AutoConfigurationPackage {}

static class Registrar implements ImportBeanDefinitionRegistrar, DeterminableImports {}
```

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(MyImportBeanDefinitionRegistrar.class)
public @interface EnablePerson {
}

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar{}
```

通过比对发现，自定义的`@EnablePerson`注解格式和`@AutoConfigurationPackage`是一样的，都有`@Import`注解。而且`@Import`里的classs都实现了`ImportBeanDefinitionRegistrar`接口。



通过实现`ImportBeanDefinitionRegistrar`接口,我们可以做到动态注入`Bean`.下面给出`@EnablePerson`的具体实现。

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(MyImportBeanDefinitionRegistrar.class)
public @interface EnablePerson {
}

public class Person {
    private String name;
    private int age;
}

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        boolean person = registry.containsBeanDefinition("person");
        if (!person) {
            System.out.println("Create Person Bean...");
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(Person.class)
                            .addPropertyValue("name", "twx")
                            .addPropertyValue("age", 22);
            registry.registerBeanDefinition("person", builder.getBeanDefinition());
//            GenericBeanDefinition bd = new GenericBeanDefinition();
//            bd.setBeanClass(Person.class);
//            bd.getPropertyValues().add("name", "twx");
//            bd.getPropertyValues().add("age", 22);
//            registry.registerBeanDefinition("person",bd);
        }
    }
}
```

**Bean的两种构建方式参考上述代码!**

使用方式就很简单了,如下代码所示:

```java
@SpringBootApplication
@EnablePerson
public class LearnSpringExpansionApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LearnSpringExpansionApplication.class, args);
        Person person = (Person) context.getBean("person");
        System.out.println("person=>"+person);
    }

}
```

源码地址: https://github.com/tangwenixng/learn-series/blob/master/learn-spring-expansion/README.md

## EnvironmentPostProcessor

通过扩展实现`EnvironmentPostProcessor`接口，我们就可以加载自定义的`xx.properties`或者xx.yml文件(也许是来自网络url、或者本地磁盘)，而不用将所有的配置按照约定写入application.yml文件了。

我是在浏览nacos-spring-boot-starter的时候发现他们有扩展实现这个接口！所以在此写个小demo记录一下！

---

1. 创建一个`MyEnvironmentPostProcessor`类，实现`EnvironmentPostProcessor`接口

```java
public class MyEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Properties properties = new Properties();
        try {
            String path = System.getProperty("user.dir") + File.separator + "env" + File.separator + "soyuan.properties";
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PropertiesPropertySource soyuan = new PropertiesPropertySource("soyuan", properties);
        environment.getPropertySources().addLast(soyuan);
    }
}
```

上述代码将`${user.dir}/env/soyuan.properteis`配置文件加载到`spring`中。

![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20220622105258.png)

2. 需要通过SPI方式加载`MyEnvironmentPostProcessor`。在`resources`目录下创建META-INF/spring.factories文件,内容如下：

```properties
org.springframework.boot.env.EnvironmentPostProcessor=com.twx.learn.learnspringexpansion.environment.MyEnvironmentPostProcessor
```

3. 使用方式如下

```java
@SpringBootApplication
public class LearnSpringExpansionApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LearnSpringExpansionApplication.class, args);
        String token = context.getEnvironment().getProperty("app.token");//app.token定义在soyuan.properties中
        System.out.println("token=>"+token);
    }
}
```

---

如果要加载外部yaml文件，稍微复杂一点。步骤如下:

1. 创建`YmlResourceFactory`类，继承`DefaultPropertySourceFactory`

```java
public class YmlResourceFactory extends DefaultPropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource.getResource());
        factory.afterPropertiesSet();

        Properties ymlProperties = factory.getObject();
        String propertyName = name != null ? name : resource.getResource().getFilename();
        return new PropertiesPropertySource(propertyName, ymlProperties);
    }
}
```

2. 修改`MyEnvironmentPostProcessor`类

```java
public class MyEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        //加载自定义yaml
        YmlResourceFactory factory = new YmlResourceFactory();
        String yamlPath = System.getProperty("user.dir") + File.separator + "env" + File.separator + "soyuan.yml";
        try {
            PropertySource soyuan = factory.createPropertySource("soyuan", new EncodedResource(new FileSystemResource(yamlPath)));
            environment.getPropertySources().addLast(soyuan);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

源码地址: https://github.com/tangwenixng/learn-series/blob/master/learn-spring-expansion/README.md