package com.example.demo.controller;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author：lanjy
 * @date：2020/6/8
 * @description：
 */
@RestController
public class HomeController {

    /**
     * 返回请求源的ip地址
     * @param request
     * @return
     */
    @GetMapping("/api")
    public String getApi(HttpServletRequest request){
        return request.getRemoteAddr();
    }

}


