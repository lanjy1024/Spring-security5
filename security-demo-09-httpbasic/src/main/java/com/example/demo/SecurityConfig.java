package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * @author：lanjy
 * @date：2020/6/6
 * @description：
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 添加ip限制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()//授权请求
            //实际项目中,ip客户端可能会很多,可能胡存放在数据库里的,自定义CustomIPAuthenticationProvider该类控制处理
            //.antMatchers("/api**").hasIpAddress("192.168.56.1")
            .anyRequest()//所有请求
            .authenticated()//已认证
            .and()
            .formLogin()
            .and()
            .httpBasic();
    }
}
