package com.hb.websocketclientdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    private static final Logger logger = LoggerFactory.getLogger(ViewController.class);

    @RequestMapping("/")
    public String hello() {
        return "login-sign";
    }

    @RequestMapping("/index")
    public String index() {
        return "login-sign";
    }

    @RequestMapping("/login")
    public String login() {
        return "login-sign";
    }

    @RequestMapping("/login-error")
    public String loginError() {
        return "login-error";
    }

    @RequestMapping("/monitor-summary")
    public String summaryPage() {
        return "monitor-summary";
    }

    @RequestMapping("/monitor-detail")
    public String monitor() {
        return "monitor-detail";
    }

}
