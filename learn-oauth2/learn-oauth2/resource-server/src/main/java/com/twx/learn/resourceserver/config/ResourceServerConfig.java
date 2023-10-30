package com.twx.learn.resourceserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final TokenStore tokenStore;

    public ResourceServerConfig(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    /*@Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String secret;

    @Value("${security.oauth2.authorization.check-token-access}")
    private String checkTokenEndpointUrl;



    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    @Bean
    public TokenStore redisTokenStore (){
        return new RedisTokenStore(redisConnectionFactory);
    }*/

    /**
     * 配置了 access_token 的校验地址、client_id、client_secret 这三个信息，
     * 当用户来资源服务器请求资源时，会携带上一个 access_token，通过这里的配置，就能够校验出 token 是否正确等
     * @return
     */
    /*@Bean
    RemoteTokenServices tokenServices() {
        RemoteTokenServices services = new RemoteTokenServices();
        services.setCheckTokenEndpointUrl(checkTokenEndpointUrl);
        services.setClientId(clientId);
        services.setClientSecret(secret);
        return services;
    }*/
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.resourceId("res1").tokenServices(tokenServices());
        resources.resourceId("res1").tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("admin")
                .anyRequest().authenticated();
    }
}
