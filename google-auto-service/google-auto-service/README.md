# google auto-service使用示例

最近在看Bladex-tool的auto模块发现它引用了[google-auto-service](https://github.com/google/auto/tree/main/service)这个项目。

一头雾水,所以搜索了一番，找到这篇文章[Google AutoService](https://www.baeldung.com/google-autoservice) 本模块照着这篇文章实战领悟auto-service的魅力。

## 回顾java spi

可以参考本项目的[learn-java-spi](https://github.com/tangwenixng/learn-series/tree/master/learn-java-spi)示例与当前模块进行比较。

1. 在resources目录下新建`META-INF/services`目录
2. 在`META-INF/services`目录新建一个以接口全限定名为名字的文件,例如本示例的`com.twx.learn.spi.CutAction`
3. `com.twx.learn.spi.CutAction`文件里的内容填入接口实现类的全限定名;本例是`com.twx.learn.spi.SoyuanCutAction`
4. 获取接口实现类的代码如下所示:

```java
ServiceLoader<CutAction> serviceLoader = ServiceLoader.load(CutAction.class);
Iterator<CutAction> iterator = serviceLoader.iterator();
while (iterator.hasNext()) {
    CutAction action = iterator.next();
    String res = action.exec("Prd_stream/gy1");
    System.out.println("res=>"+res);
}
```

调用`iterator.next();`时进行 接口实现类的实例化。即通过反射调用无参构造方法new实现类。

## google-auto-service

1. 首先我们声明了接口`TranslationService`
2. 然后实现了接口 `BingTranslationServiceProvider`，并且在接口上添加了注解 `@AutoService(TranslationService.class)`
3. **注意: 我们并没有在`META-INF/services`目录下手动编写配置**,但是编译运行后在target目录自动生成了 `com.twx.learn.TranslationService`

![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20240304175652.png)