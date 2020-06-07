package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author：lanjy
 * @date：2020/6/6
 * @description：
 */
@Controller
@Slf4j
public class HomeController {

    @GetMapping(value = {"/","/home"})
    public String home(){
        log.info("登录成功");
        return "index_page";
    }
    /*@PostMapping("/login")
    public String login(){
        log.info("登录成功");
        return "index_page";
    }*/

    @GetMapping("/demo-login")
    public String loginPage(){
        log.info("进入登录页面");
        return "login_page";
    }
    @GetMapping("/demo-logout")
    public String logout(){
        log.info("进入退出登录页面");
        return "logout_page";
    }
}
