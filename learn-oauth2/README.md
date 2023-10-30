什么是OAuth2?
- 概念性的东西就不讲了。我们常见的第三方登录，比如QQ、微信、Github等使用的就是OAuth2。

OAuth2有四种模式:
- `授权码模式`：**最重要，最通用** 。
- `简化模式`:  直接在浏览器中向授权服务器申请令牌（token）
- `密码模式`:  把用户名密码直接告诉客户端，客户端使用说这些信息向授权服务器申请令牌（token）
- `客户端模式`:  客户端使用自己的`clientId`和`clientSecret`直接申请`token`

## 简化、密码、客户端模式
1. **简化模式实例**
```txt
  GET /oauth/authorize?client_id=abcd&response_type=token&scope=all&redirect_uri=http://localhost:8081/index.html
  ```
1. client_id:  abcd
2. response_type: token
3. redirect_uri: http://localhost:8081/index.html

在认证中心页面上输入用户名/密码成功登录后，会回调至redirect_uri（携带token），比如：
`http://localhost:8081/index.html#access_token=8470caee-643c-4301-9613-ba3ef8c27636&token_type=bearer&expires_in=7199`

2. **密码模式示例**
```text
  POST /oauth/token?grant_type=password&client_id=u_pwd&client_secret=123&username=admin&password=123
  ```
- grant_type: password
- client_id: u_pwd
- client_secret: 123
- username: admin
- password: 123

接口直接返回token值:
```json
  {
  "access_token": "dcd14ceb-eb62-49f5-9d46-beb1d327a3a1",
  "token_type": "bearer",
  "refresh_token": "a402a8d8-7c2d-43f2-8ede-5d258cb2b065",
  "expires_in": 6866,
  "scope": "all"
  }
  ```

3. **客户端模式**
```text
  POST /oauth/token?grant_type=client_credentials&client_id=u_client&client_secret=123
  ```
- grant_type: client_credentials
- client_id: u_client
- client_secret: 123
- **(相比密码模式少了username和password参数)**

- 接口返回值示例:
```json
  {
  "access_token": "913f1a28-3118-4773-bb5e-33765a44b383",
  "token_type": "bearer",
  "expires_in": 7199,
  "scope": "all"
  }
  ```

## 认证&资源服务(分离)
### 认证服务

下面将演示如何搭建基于JWT token的认证服务:

1. **引入依赖**
   logseq.order-list-type:: number
```xml
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-oauth2</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
  </dependency>
  <dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
  </dependency>
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.8</version>
    <scope>compile</scope>
  </dependency>
  ```
> 注意: 引入jdbc是为了将用户和客户端数据存储至数据库持久化

2. **建立数据库并初始化客户端数据**
```sql
  DROP TABLE IF EXISTS `oauth_client_details`;
  CREATE TABLE `oauth_client_details` (
    `client_id` varchar(48) NOT NULL,
    `resource_ids` varchar(256) DEFAULT NULL,
    `client_secret` varchar(256) DEFAULT NULL,
    `scope` varchar(256) DEFAULT NULL,
    `authorized_grant_types` varchar(256) DEFAULT NULL,
    `web_server_redirect_uri` varchar(256) DEFAULT NULL,
    `authorities` varchar(256) DEFAULT NULL,
    `access_token_validity` int DEFAULT NULL,
    `refresh_token_validity` int DEFAULT NULL,
    `additional_information` varchar(4096) DEFAULT NULL,
    `autoapprove` varchar(256) DEFAULT NULL,
    PRIMARY KEY (`client_id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
  -- ----------------------------
  -- Records of oauth_client_details
  -- ----------------------------
  INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('implicit', 'res2', '$2a$10$BGRoWYPm9B1CJRcHJYV9BOyGbWVKOr9S.E5OFotqw9a9nsR0zeH8u', 'all', 'implicit,refresh_token', 'http://localhost:8081/index.html', NULL, 7200, 259200, NULL, NULL);
  INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('javaboy', 'res1', '$2a$10$BGRoWYPm9B1CJRcHJYV9BOyGbWVKOr9S.E5OFotqw9a9nsR0zeH8u', 'all', 'authorization_code,refresh_token', 'http://localhost:8081/index.html', NULL, 7200, 259200, NULL, NULL);
  INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('twx', 'res1', '$2a$10$BGRoWYPm9B1CJRcHJYV9BOyGbWVKOr9S.E5OFotqw9a9nsR0zeH8u', 'all', 'authorization_code,refresh_token', 'http://localhost:8081/index.html', NULL, 7200, 259200, NULL, NULL);
  INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('u_client', 'res1,res4', '$2a$10$BGRoWYPm9B1CJRcHJYV9BOyGbWVKOr9S.E5OFotqw9a9nsR0zeH8u', 'all', 'client_credentials,refresh_token', 'http://localhost:8081/index.html', NULL, 7200, 259200, NULL, NULL);
  INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('u_pwd', 'res3', '$2a$10$BGRoWYPm9B1CJRcHJYV9BOyGbWVKOr9S.E5OFotqw9a9nsR0zeH8u', 'all', 'password,refresh_token', 'http://localhost:8081/index.html', NULL, 7200, 259200, NULL, NULL);
  
  ```
数据库表字段解释如下:
![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20231030152926.png)

3. **修改配置（主要是数据库）**
```yaml
  spring:
    datasource:
      url: jdbc:mysql://172.26.10.100:3306/oauth?useUnicode=true&characterEncoding=UTF-8&serverTimeZone=Asia/Shanghai
      username: root
      password: Pass2017
    main:
      allow-bean-definition-overriding: true
  ```

4. **配置SpringSecurity**

通过继承`WebSecurityConfigurerAdapter`并重写一些关键方法
```java
  @Configuration
  @EnableWebSecurity
  public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  
      @Bean
      public PasswordEncoder passwordEncoder() {
          return new BCryptPasswordEncoder();
      }
  
      @Bean
      UserDetailsService syUserDetailsService() {
          return new SyUserDetailService(passwordEncoder());
      }
  
      /**
       * 密码模式需要用到 AuthenticationManager
       */
      @Override
      @Bean
      public AuthenticationManager authenticationManagerBean() throws Exception {
          return super.authenticationManagerBean();
      }
  
      @Override
      protected void configure(AuthenticationManagerBuilder auth) throws Exception {
          auth.userDetailsService(syUserDetailsService());
      }
  
      @Override
      protected void configure(HttpSecurity http) throws Exception {
          http.csrf().disable().formLogin();
      }
  
  }
  ```
- 通过注解`@EnableWebSecurity`启用安全防护
- `PasswordEncoder`: 注入加密算法
- `UserDetailsService`: 注入用户数据，这是spring security的标准用法（这里的用户数据可以理解成微信、QQ用户。）我们在第三方登录跳转后要输入的用户名密码。
- `AuthenticationManager`：认证管理器。这是spring security的核心组件之一。`AuthenticationManager`调用内部的`AuthenticationProvider`进行实际认证
  ![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20231030153801.png)
- `auth.userDetailsService(syUserDetailsService());` 配置使用`UserDetailsService`加载验证用户。
- `http.csrf().disable().formLogin();`禁用跨站攻击和启用表单登录。

4.1 **简单实现`UserDetailsService`**
```java
  public class SyUserDetailService implements UserDetailsService {
      private final PasswordEncoder passwordEncoder;
  
      public SyUserDetailService(PasswordEncoder passwordEncoder) {
          this.passwordEncoder = passwordEncoder;
      }
  
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          String role = "user";
          if (username.equals("sang")) {
              role = "admin";
          }
          List<SimpleGrantedAuthority> authorities = new ArrayList<>();
          authorities.add(new SimpleGrantedAuthority(role));
  
          return new User(username, passwordEncoder.encode("123"), authorities);
      }
  }
  ```
硬编码了用户密码角色，实际应该从数据库加载(看后续文章)

5. **配置OAuth2**

通过继承`AuthorizationServerConfigurerAdapter`并重写关键方法
```java
  @Configuration
  @EnableAuthorizationServer
  @RequiredArgsConstructor
  public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
      private final TokenStore tokenStore;
      private final DataSource dataSource;
      private final AuthenticationManager authenticationManager;
      private final JwtAccessTokenConverter jwtAccessTokenConverter;
      private final CustomAdditionalInformation customAdditionalInformation;
  
  
      @Bean
      public ClientDetailsService clientDetailsService() {
          return new JdbcClientDetailsService(dataSource);
      }
  
  
      /**
       * 用来配置令牌的存储，即 access_token 的存储位置
       */
      @Bean
      AuthorizationServerTokenServices tokenServices() {
          //配置 Token 的一些基本信息
          DefaultTokenServices services = new DefaultTokenServices();
          services.setClientDetailsService(clientDetailsService());
          services.setSupportRefreshToken(true);
          services.setTokenStore(tokenStore);
          TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
          tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter, customAdditionalInformation));
          services.setTokenEnhancer(tokenEnhancerChain);
          return services;
      }
  
      @Override
      public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
          //tokenServices 用来配置令牌的存储，即 access_token 的存储位置
          //配置认证管理器:密码模式需要
          endpoints.authenticationManager(authenticationManager)
                  .tokenServices(tokenServices());
      }
  
      @Override
      public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
          //配置客户端的详细信息
          clients.withClientDetails(clientDetailsService());
      }
  
      /**
       * 配置令牌端点的安全约束，也就是这个端点谁能访问，谁不能访问
       */
      @Override
      public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
          //配置令牌端点的安全约束
          security.checkTokenAccess("permitAll()")
                  .allowFormAuthenticationForClients();
  
      }
  }
  ```
- `@EnableAuthorizationServer`: 启用认证服务
- `clientDetailsService()`: 通过`ClientDetailsService`加载之前数据库中配置的client数据(回调地址、客户端凭据、token过期时间等)
- `tokenServices()`: `setClientDetailsService`设置从哪加载客户端数据。`setTokenStore`设置access_token存储在哪里，自动注入了`JwtTokenStore`实例(后面会贴源码)；`setTokenEnhancer`是对jwt token做增强配置（后面再讲）
- `configure(AuthorizationServerEndpointsConfigurer endpoints)`: 配置认证管理器和`tokenService`
- `configure(ClientDetailsServiceConfigurer clients)`: 配置客户端详情（从哪加载）
- `configure(AuthorizationServerSecurityConfigurer security)`：配置令牌端点的安全约束，也就是这个端点谁能访问，谁不能访问。checkTokenAccess 是指一个 Token 校验的端点，这个端点我们设置为可以直接访问。（后面的资源服务器会访问这个端点进行token校验）

5.1 **配置TokenStore实例**

我们配置从jwt生成token
```java
  @Configuration
  public class AccessTokenConfig {
      private String SIGNING_KEY = "javaboy";
  
      @Bean
      TokenStore tokenStore() {
          return new JwtTokenStore(jwtAccessTokenConverter());
      }
  
      /**
       * 将用户信息和 JWT 进行转换（将用户信息转为 jwt 字符串，或者从 jwt 字符串提取出用户信息）
       */
      @Bean
      JwtAccessTokenConverter jwtAccessTokenConverter() {
          JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
          converter.setSigningKey(SIGNING_KEY);
          return converter;
      }
  }
  ```
`JwtAccessTokenConverter`:  将默认的uuid token增强为jwt.
在`DefaultTokenServices#createAccessToken()`方法最后一行判断有没有`accessTokenEnhancer`。如果有就进行增强，即调用`TokenEnhancer#enhance()`。默认的`TokenEnhancer`实现是**TokenEnhancerChain**, 在它的`enhance()`方法里会遍历调用其他的`TokenEnhancer#enhance()`。由于`JwtAccessTokenConverter`实现了`TokenEnhancer`接口, 所以最终会进入到`JwtAccessTokenConverter`。（流程参见下图）

![](https://slimteaegg-blog.oss-cn-shanghai.aliyuncs.com/picgo20231030164418.png)


### 资源服务

**引入依赖**
```xml
  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-oauth2</artifactId>
    </dependency>
  </dependencies>
  ```
**2. 配置资源服务**
通过继承`ResourceServerConfigurerAdapter`并重写几个方法
```java
  @Configuration
  @EnableResourceServer
  public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
  
      private final TokenStore tokenStore;
  
      public ResourceServerConfig(TokenStore tokenStore) {
          this.tokenStore = tokenStore;
      }
  
  
      @Override
      public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
          resources.resourceId("res1").tokenStore(tokenStore);
      }
  
      @Override
      public void configure(HttpSecurity http) throws Exception {
          http.authorizeRequests()
                  .antMatchers("/admin/**").hasRole("admin")
                  .anyRequest().authenticated();
      }
  }
  ```
- `@EnableResourceServer`: 启用资源服务器
- `TokenStore tokenStore`: 注入`JwtTokenStore`, 同认证服务
- `configure(ResourceServerSecurityConfigurer resources)`: 配置resourceId，即该服务对应的资源ID;设置 tokenStore如何解析jwt
- `configure(HttpSecurity http)`: 配置哪些端点需要授权和认证

**3. 配置TokenStore**

将认证服务的`TokenStore`拷贝过来即可
```java
  @Configuration
  public class AccessTokenConfig {
      private String SIGNING_KEY = "javaboy";
  
      @Bean
      TokenStore tokenStore() {
          return new JwtTokenStore(jwtAccessTokenConverter());
      }
  
      /**
       * 将用户信息和 JWT 进行转换（将用户信息转为 jwt 字符串，或者从 jwt 字符串提取出用户信息）
       */
      @Bean
      JwtAccessTokenConverter jwtAccessTokenConverter() {
          JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
          converter.setSigningKey(SIGNING_KEY);
          return converter;
      }
  }
  ```
**4. 编写测试接口**

```java
  @RestController
  public class UserController {
      @GetMapping("/hello")
      public String hello() {
          return "hello";
      }
      @GetMapping("/admin/hello")
      public String admin() {
          return "admin";
      }
  }
  ```

**5. 访问接口`/hello`**
**5.1 无token**
`GET http://localhost:8081/hello`
返回值:
```json
  {
    "error": "unauthorized",
    "error_description": "Full authentication is required to access this resource"
  }
  ```

**5.2 有token**
```text
  GET http://localhost:8081/hello
  Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzNCIsInJlczEiXSwic2NvcGUiOlsiYWxsIl0sImV4cCI6MTY5ODY2NDAxNiwianRpIjoiOWY0MjFlMWEtMDE3OC00NWRmLThmYmQtMTgwMTIyODAyMzExIiwiY2xpZW50X2lkIjoidV9jbGllbnQifQ.okvrNCWYUzs2gYBHgzYR51j7vbAj8ZzsLmVg__VulBo
  
  ```
返回值:
```text
  hello
  ```
