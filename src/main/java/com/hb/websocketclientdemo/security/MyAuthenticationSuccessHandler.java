package com.hb.websocketclientdemo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登入成功后拦截器
 * 直接返回的是Json格式的数据
 */

@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
//        super.onAuthenticationSuccess(request, response, authentication);
        Map<String, String> map = new HashMap<>();

        map.put("code", "200");
        map.put("msg", "Login Success!");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(map));
        if (authentication.getName().equals("dev"))
            new DefaultRedirectStrategy().sendRedirect(request, response, "/index");
        else
            new DefaultRedirectStrategy().sendRedirect(request, response, "/monitor-summary");


    }

}
