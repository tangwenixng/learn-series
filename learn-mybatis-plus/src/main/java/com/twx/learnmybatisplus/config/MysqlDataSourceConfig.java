package com.twx.learnmybatisplus.config;

import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author soyuan
 */
@Configuration
@MapperScan(basePackages = "com.twx.learnmybatisplus.db.mysql",sqlSessionFactoryRef = "mysqlSessionFactory")
public class MysqlDataSourceConfig {

    @Bean(name = "mysqlDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource mysql(){
        return DataSourceBuilder.create().build();
    }


    /**
     * mybatis-plus 配置官方文档地址
     * http://mp.baomidou.com/#/api?id=mybatissqlsessionfactorybean
     * @return
     */
    @Bean
    public GlobalConfiguration globalConfiguration() {
        GlobalConfiguration conf = new GlobalConfiguration(new LogicSqlInjector());
        //逻辑删除 定义下面3个参数
//        conf.setLogicDeleteValue("-1");
//        conf.setLogicNotDeleteValue("1");
//        conf.setIdType(2);
        return conf;
    }

    @Bean(name = "mysqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("mysqlDataSource")  DataSource dataSource) throws Exception{
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
        .getResources("classpath*:mapper/mysql/*.xml"));

        sqlSessionFactoryBean.setTypeAliasesPackage("com.twx.learnmybatisplus.entity.mysql");

        MybatisConfiguration configuration = new MybatisConfiguration();
        //部分数据库不识别默认的NULL类型（比如oracle，需要配置该属性
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        //开启驼峰命名
        configuration.setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(configuration);

        sqlSessionFactoryBean.setPlugins(new Interceptor[]{
                new PaginationInterceptor()
        });
        sqlSessionFactoryBean.setGlobalConfig(globalConfiguration());
        return sqlSessionFactoryBean.getObject();
    }


}
