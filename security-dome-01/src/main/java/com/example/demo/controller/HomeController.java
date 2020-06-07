package com.example.demo.controller;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：lanjy
 * @date：2020/6/6
 * @description：
 */
@RestController
@Slf4j
public class HomeController {
    @GetMapping(value = {"/","/home"})
    public String login(){
        log.info("登录成功");
        return "登录成功";
    }

    @GetMapping("/api")
    public String getApi(){
        log.info("springboot security api");
        return "springboot security api";
    }
}
