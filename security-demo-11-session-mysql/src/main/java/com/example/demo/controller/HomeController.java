package com.example.demo.controller;

import com.example.demo.dao.EmployeeRepository;
import com.example.demo.po.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author：lanjy
 * @date：2020/6/6
 * @description：
 */
@RestController
@Slf4j
public class HomeController {
    private static final String USER_SESSION = "USER_SESSION";
    @Autowired
    private EmployeeRepository employeeRepository;


    /**
     1.浏览器第一次请求的时候，服务端创建一个session，并且将名为SESSION的属性作为响应cookie返回浏览器

     2. 浏览器保存名称为SESSION的cookie作为唯一标识，下次访问服务端时带上此cookie

     3.服务端session中的名为SESSION的属性和浏览器保存的cookie是不一致的，为保密原因，进行了base64加密。

     4.spring-session使用了简单的配置，可以实现session持久化到数据库。

     */
    @GetMapping("/test/{name}")
    public String adminLogin(@PathVariable String name,HttpSession session){
        Optional<Employee> employeeOptional = employeeRepository.findByName(name);
        if(employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            log.info("成功");
            session.setAttribute(USER_SESSION,employee);
            return "成功"+employee.toString();
        }
        log.info("shibai");
        return "失败";
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
