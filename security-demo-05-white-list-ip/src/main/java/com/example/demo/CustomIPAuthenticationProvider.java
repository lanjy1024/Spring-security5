package com.example.demo;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：lanjy
 * @date：2020/6/8
 * @description：客户端IP鉴定:处理IP白名单
 */
@Slf4j
@Component
public class CustomIPAuthenticationProvider  implements AuthenticationProvider {

    /**
     * IP白名单list
     */
    private List<String> whiteIPs = new ArrayList<>();
    //可以从数据库加载
    public CustomIPAuthenticationProvider(){
        whiteIPs.add("192.168.56.1");
//        whiteIPs.add("127.0.0.1");

    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        String remoteAddress = details.getRemoteAddress();
        log.info("客户端ip:{}",remoteAddress);
        if (!whiteIPs.contains(remoteAddress)) {
            //如果不是IP白名单,则抛出异常
            throw new BadCredentialsException("不是IP白名单,则抛出异常");
        }
        return authentication;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
