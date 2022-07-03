# java spi demo

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

---

大家可以看一下这篇文章: https://pdai.tech/md/java/advanced/java-advanced-spi.html#spi%E6%9C%BA%E5%88%B6%E7%9A%84%E7%AE%80%E5%8D%95%E7%A4%BA%E4%BE%8B