package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.DocFlavor;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author：lanjy
 * @date：2020/6/8
 * @description：
 * curl -H "Authorization:Basic bGFuOjEyMzQ1Ng==" http://localhost:8080/hello
 */
@RestController
@Slf4j
public class SessionController {

    private static final String USER_SESSION = "USER_SESSION";

    @GetMapping("/")
    public String index(HttpSession session, Authentication authentication){
        String name = authentication.getName();
        log.info("登录成功:{}",name);
        session.setAttribute(USER_SESSION,name);
        return "hello session:"+name;
    }
    @GetMapping("/session")
    public String session(HttpSession session){
        return session.getAttribute(USER_SESSION).toString();
    }

    /**
     * 登录成功后,会返回客户端一个cookie
     * JSESSIONID:70B14CF740F074F58224B077DFB39D08
     * 70B14CF740F074F58224B077DFB39D08是服务端保存的session的id
     * 如果这个cookie被窃取,就可以不用登录,直接访问到这个后台页面
     * @param request
     * @return
     */
    @GetMapping("/cookies")
    public String cookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .map(cookie -> cookie.getName()+":"+cookie.getValue())
                    .collect(Collectors.joining(","));
        }

        return "null";
    }

}
