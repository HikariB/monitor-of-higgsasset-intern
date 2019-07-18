package com.hb.websocketclientdemo.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class MyUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //这里可以可以通过username（登录时输入的用户名）然后到数据库中找到对应的用户信息，并构建成我们自己的UserInfo来返回


        //为了快捷测试 直接内部写入
        if(username.equals("admin")){
            UserInfo userInfo = new UserInfo("admin","123456","ROLE_ADMIN", true,true,true, true);
            return  userInfo;
        }
        return null;
    }
}
