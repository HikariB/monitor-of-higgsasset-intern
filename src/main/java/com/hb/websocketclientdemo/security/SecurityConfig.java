package com.hb.websocketclientdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 核心配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationProvider provider;

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

//    @Autowired
//    private AuthenticationFailureHandler myAuthenticationFailureHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //loginPage 拦截后尚未拥有权限转入的url登入页面请求，需要配置相应的视图解析Controller，返回指定的html
                //loginProcessingUrl 登入页面表单提交的地址
                //failureUrl 密码错误跳转的url请求，同样需要进行视图解析配置，详见TestController
                .formLogin().loginPage("/login").loginProcessingUrl("//login/form").failureUrl("/login-error")
                //自定义的登入认证成功或者失败拦截器
                .successHandler(myAuthenticationSuccessHandler)
//                .failureHandler(myAuthenticationFailureHandler)
                .permitAll()
                .and()
                .logout().logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .and()
                .authorizeRequests()
                //对于特定资源不需要权限
                .antMatchers("/index", "/css/**", "/js/**", "/images/**").permitAll()
                //需要指定 权限要求的 url资源/http方法
                .antMatchers("/monitor-summary").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET).hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }
    // 允许对于网站静态资源的无授权访问
//                        .antMatchers(
//                            HttpMethod.GET,
//                            "/",
//                            "/*.html",
//                            "/**/*.html",
//                            "/**/*.css",
//                            "/**/*.js"
//                         ).permitAll()



    //不能没有 passwordEncoder()
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("user").password(new BCryptPasswordEncoder().encode("password")).roles("USER")
//                .and()
//                .withUser("admin").password(new BCryptPasswordEncoder().encode("123456")).roles("ADMIN");

        //自定义验证的组件
        auth.authenticationProvider(provider);

    }
}
