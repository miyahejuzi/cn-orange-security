package cn.orange.browser;

import cn.orange.browser.security.CustomAuthenticationFailureHandler;
import cn.orange.browser.security.CustomAuthenticationSuccessHandler;
import cn.orange.browser.security.CustomUserDetailsServiceImpl;
import cn.orange.browser.security.ValidateCodeFilter;
import cn.orange.browser.session.DemoExpiredSessionStrategy;
import cn.orange.core.SmsCodeAuthenticationSecurityConfig;
import cn.orange.core.properties.CustomSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author : kz
 * @date : 2019/7/30
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityBrowserConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomSecurityProperties securityProperties;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private ValidateCodeFilter validateCodeFilter;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Bean
    @Override
    public CustomUserDetailsServiceImpl userDetailsService() {
        return new CustomUserDetailsServiceImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .apply(smsCodeAuthenticationSecurityConfig)
                .and()
            .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
            // 配置登录页面
            .formLogin()
                // 登录页面的访问路径 --> 自定义的 controller 方法来判断怎么跳转
                .loginPage("/authentication/login")
                .loginProcessingUrl("/login/authentication/image")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userDetailsService)
                // 也是可以配置的
                .tokenValiditySeconds(60 * 60)
                .and()
            .sessionManagement()
                .invalidSessionUrl("/session/invalid")
                .maximumSessions(1)
                .expiredSessionStrategy(new DemoExpiredSessionStrategy())
                .maxSessionsPreventsLogin(true)
                .and()
                .and()
            .authorizeRequests()
                // 这几个请求不拦截
                .antMatchers(getPermitAllUrl()).permitAll()
                // 拦截其他请求
                .anyRequest().authenticated()
                .and()
            .csrf()
                .disable();
    }

    private String[] getPermitAllUrl() {
        return new String[]{
                "/authentication/*", "/login", "index", "/error",
                "/code/*", "/session/invalid",
                securityProperties.getBrowser().getLoginPage()
        };
    }

}
