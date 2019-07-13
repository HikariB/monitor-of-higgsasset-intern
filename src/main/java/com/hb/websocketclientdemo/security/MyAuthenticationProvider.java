package com.hb.websocketclientdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 原理为拦截器
 *
 * 内部验证的流程：前端POST请求发送至指定地址-->AuthenticationManager -->
 * -->AuthenticationProvider 即自定义Class
 * --> 调用 UserDetailService从内存或者数据库调用 UserDetails对象（UserInfo）
 * 验证逻辑，主要是密码
 */

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MyUserDetailsService myUserDetailsService;


    //authentication 中带有表单提交的username password
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserInfo userInfo = (UserInfo) myUserDetailsService.loadUserByUsername(userName);
        if (userInfo == null) {
            throw new BadCredentialsException("User Not Existed");
        }

//        加入加密器
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String encodePwd = bCryptPasswordEncoder.encode(password);
//        if (!userInfo.getPassword().equals(encodePwd)){
//            throw new BadCredentialsException("Illegal Password");
//        }

        if (!userInfo.getPassword().equals(password)) {
            throw new BadCredentialsException("Illegal Password");
        }

        Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();

        // 构建返回的用户登录成功的token
        return new UsernamePasswordAuthenticationToken(userInfo,password,authorities);



//        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {

        // 这里直接改成retrun true;表示是支持这个执行

        return true;
    }
}
