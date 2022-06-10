package com.twx.learn.asspringbootautoconfigure;

import com.twx.learn.IAsCamara;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
