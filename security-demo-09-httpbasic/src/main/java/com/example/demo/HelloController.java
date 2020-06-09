package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：lanjy
 * @date：2020/6/8
 * @description：
 * curl -H "Authorization:Basic bGFuOjEyMzQ1Ng==" http://localhost:8080/hello
 */
@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello(){
        return "hello http-basic";
    }
}
