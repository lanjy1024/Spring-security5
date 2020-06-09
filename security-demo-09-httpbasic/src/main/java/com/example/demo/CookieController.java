package com.example.demo;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author：lanjy
 * @date：2020/6/8
 * @description：
 */
@RestController
public class CookieController {

    @GetMapping("read")
    public String read(@CookieValue String username){
        return username;
    }


    @GetMapping("write")
    public String write(HttpServletResponse response){
        Cookie cookie = new Cookie("write_cookie", "11111");
        //https访问才能读取到这个cookie
        cookie.setSecure(true);
        response.addCookie(cookie);
        return "OK";
    }
}
