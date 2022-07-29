# 集成QueryDSL

1. 引入依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<!--QueryDSL支持-->
<dependency>
    <groupId>com.querydsl</groupId>
    <artifactId>querydsl-apt</artifactId>
    <scope>provided</scope>
</dependency>

<!--QueryDSL支持-->
<dependency>
    <groupId>com.querydsl</groupId>
    <artifactId>querydsl-jpa</artifactId>
</dependency>

<plugin>
<groupId>com.mysema.maven</groupId>
<artifactId>apt-maven-plugin</artifactId>
<version>1.1.3</version>
<executions>
    <execution>
        <phase>generate-sources</phase>
        <goals>
            <goal>process</goal>
        </goals>
        <configuration>
            <!--							<outputDirectory>target/generated-sources/java</outputDirectory>-->
            <outputDirectory>target/generated-sources/java</outputDirectory>
            <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
        </configuration>
    </execution>
</executions>
</plugin>
```

2. 建立Entity，比如WarnType
```java
@Entity
@Table(name = "warn_type")
public class WarnType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 预警类型名称
     */
    private String name;

    /**
     * 音频id
     */
    private Integer warnVoiceId;

    /**
     * 报警间隔 单位分钟
     */
    private Integer warnInterval;

    /**
     * 行为类型id
     */
    private Integer behaviorId;

    /**
     * 绑定的预警类型code
     */
    private String typeCode;

    /**
     * 行为类型json
     */
    private String behaviorJson;

    /**
     * 是否系统内置 0系统 1用户
     */
    private Integer isSystem;

    /**
     * 状态 0使用中 1未启用
     */
    @Column(columnDefinition = "TINYINT")
    private Integer enable;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
```

3. 通过`mvn clean compile`命令编译,会在`target/generated-sources`目录下生成QWarnDic类
4. 在idea中将`target/generated-sources/java`标记为`Sources`,如下图所示：
![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20220729104159.png)

5. 创建一个`JPAQueryFactory` Bean
```java
@Bean
public JPAQueryFactory jpaQueryFactory(EntityManager em) {
    return new JPAQueryFactory(em);
}
```
注意：引入spring-data-jpa自动创建了EntityManager，所以直接注入即可！

6. 创建Dao接口,扩展了`QuerydslPredicateExecutor<WarnType>`接口
```java
@Repository
public interface WarnTypeDao extends JpaRepository<WarnType,Integer>, QuerydslPredicateExecutor<WarnType> {
}
```

---

7. 测试一下
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class WarnTypeDaoTest {
    @Autowired
    WarnTypeDao warnTypeDao;

    @Autowired
    JPAQueryFactory queryFactory;

    /**
     * 联表查询
     */
    @Test
    public void multiTable() {
        QWarnType warnType = QWarnType.warnType;
        QWarnDic warnDic = QWarnDic.warnDic;
        List<Tuple> list = queryFactory.select(warnType.name, warnType.typeCode, warnDic.name)
                .from(warnType)
                .innerJoin(warnDic)
                .on(warnType.behaviorId.eq(warnDic.id))
                .fetch();
        assertEquals(17, list.size());
    }

    /**
     * 测试两个表有相同名字的字段
     */
    @Test
    public void multiTableProjectBean() {
        QWarnType warnType = QWarnType.warnType;
        QWarnDic warnDic = QWarnDic.warnDic;
        List<WarnTypeDto> list = queryFactory.select(Projections.bean(
                        WarnTypeDto.class, warnType.name, warnType.typeCode, warnDic.name.as("dicName")
                ))
                .from(warnType)
                .innerJoin(warnDic)
                .on(warnType.behaviorId.eq(warnDic.id))
                .fetch();
        assertEquals(17, list.size());
    }
}
```




