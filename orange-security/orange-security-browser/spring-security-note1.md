# 慢慢来就很容易的 spring security 的学习（一）
`时间不会给你答案`

#### 简单直白的快速使用
0. 引入 spring security 依赖
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
</dependencies>
```
1. 在 spring boot 里面配置 security
```java
@Configuration
@EnableWebSecurity
public class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 该方法所返回的对象的方法来配置请求级别的安全细节
        http.authorizeRequests()
                .antMatchers("/login", "/", "index").permitAll() // 这几个请求不拦截
                .anyRequest().authenticated()// 拦截其他所有请求
                .and().formLogin()// 配置登录页面
                .loginPage("/login")// 登录页面的访问路径
                .loginProcessingUrl("/login")// 登录请求的路径
                .and()
                .csrf().disable();// 跨站请求防护
        }
}
```
2. 获取用户权限信息 -- 处理用户信息获取逻辑
```java
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * 这个是模仿从数据库里面取用户权限信息
     */
    @Autowired
    private SecurityService securityService;

    /**
     * 在其他拦截器里面会调用这个方法, 得到一个有用户权限信息的对象
     * user 里面有 4 个条件, 分别是 : 可用, 过期, 密码没过期, 没有被锁定
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SecurityUser byUsername = securityService.findByUsername(s);
        if (byUsername == null) {
            throw new UsernameNotFoundException("The username of the user not found...");
        }

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(byUsername.getRoles());
        return new User(byUsername.getUsername(), byUsername.getPassword(), authorities);
    }
}

// 其中获取的 securityUser 是我自己定义的, 只有 name, password, String[] roles
@Service
public class SecurityService {
    static Map<String, SecurityUser> userMap = new HashMap<String, SecurityUser>(6) {{
        this.put("123456", new SecurityUser("123456", "123456", new String[]{"ROLE_user", "ROLE_admin"}));
        this.put("123", new SecurityUser("123", "123", new String[]{"ROLE_user"}));
        this.put("456", new SecurityUser("456", "456", new String[]{"ROLE_admin"}));
    }};

    public SecurityUser findByUsername(String username) {
        return userMap.get(username);
    }
}
```
3. 以及一个自己的写的控制器
```java
@Controller
public class SecurityController {

    /**
     * 首页 所有人都可以访问
     */
    @GetMapping({"/", "index", "index.html"})
    public String index() {
        return "index";
    }

    /**
     * 登录页 所有人可都可以访问
     * 只是做了一个登录页跳转，但是具体登录的验证逻辑却没有，这是因为Spring Security要求使用者将此块的功能必须委托给它来处理
     * 也就是 spring security 会拦截 /login + post 请求
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
```
4. 这样一个简单的权限校验就起来了, 然后一个简单能满足大多数需求的校验权限的方法
```java
/**
 * 这配置类上加上注解 @EnableGlobalMethodSecurity                         
 * 就可以开启' 方法级别 '的安全性权限校验, 4个注解可用                                     
 * @PreAuthorize 在方法调用之前, 基于表达式的计算结果来限制对方法的访问
 * @PostAuthorize 允许方法调用, 但是如果表达式计算结果为false, 将抛出一个安全性异常
 * @PostFilter 允许方法调用, 但必须按照表达式来过滤方法的结果
 * @PreFilter 允许方法调用, 但必须在进入方法之前过滤输入值
 * 简单举例如下 :                                                     
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter {
}

@Controller
public class SecurityController {
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addUser(User user){
        // 如果具有权限 ROLE_ADMIN 访问该方法
    }
    
    // returnObject可以获取返回对象user，判断user属性username是否和访问该方法的用户对象的用户名一样。不一样则抛出异常。
    @PostAuthorize("returnObject.user.username == principal.username")
    public User getUser(int userId){
        // 允许进入
        return user;    
    }
} 
```
如果方法没有写注解, 那就是默认的白名单. 一般也只用来注解到 controller 层上的方法(大概)

5. 还有很多扩展
- [security 自定义决策管理器(动态权限码)](https://www.ktanx.com/blog/p/4929)
- RBAC
- [密码加密](https://www.ktanx.com/blog/p/4917)
- 自定义成功/失败处理
- 使用集群的 session 管理

> 参考的资料资料 
[spring security开发安全的rest服务](https://www.bilibili.com/video/av50683258/?p=19)
[Spring Security系列一 权限控制基本功能实现](https://www.ktanx.com/blog/p/4600)
