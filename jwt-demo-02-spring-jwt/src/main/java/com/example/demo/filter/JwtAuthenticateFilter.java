package com.example.demo.filter;

import com.example.demo.constants.SecurityConstants;
import com.example.demo.vo.LoginData;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author：lanjy
 * @date：2020/6/10
 * @description：JWT认证的过滤器
 */
@Slf4j
public class JwtAuthenticateFilter extends UsernamePasswordAuthenticationFilter{

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticateFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/token");
    }

    /**
     * 登录认证的代码
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        /*String username = request.getParameter("username");
        String password = request.getParameter("password");*/
        //获取请求body中的信息
        log.info("JwtAuthenticateFilter=====JWT认证的过滤器======attemptAuthentication");
        LoginData loginData = pareData(request);
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(loginData.getUsername(), loginData.getPassword());
        return  authenticationManager.authenticate(upToken);
    }

    /**
     * 认证成功之后,把token返回给客户
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("JwtAuthenticateFilter=====认证成功之后,把token返回给客户======successfulAuthentication");
        User user = (User) authResult.getPrincipal();
        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());


        //自己生成key
        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.KEY_STR.getBytes());
        //使用jwt工具类生成token:创建主题sub=lan,以key作为签名秘钥,并进行压缩
        String token = Jwts.builder()
                .setHeaderParam(SecurityConstants.TYP,SecurityConstants.TYPE_JWT)//TYP/JWT这些要放到常量类里面
                .setSubject(user.getUsername())
                .claim(SecurityConstants.ROLES,roles)//角色
                .setIssuedAt(new Date())//发行时间
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*5))//失效时间
                .setIssuer("system")//发行人
                //.setClaims()//可以是对象,也可以是map,自定义参数
                .signWith(key).compact();
                //Authorization要放到常量类里面
        response.setHeader(SecurityConstants.Authorization,SecurityConstants.TOKEN_PREFIX+token);
        log.info("JwtAuthenticateFilter=====认证成功之后,把token返回给客户===成功===successfulAuthentication");
    }

    private LoginData pareData(HttpServletRequest request) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return  objectMapper.readValue(request.getInputStream(),LoginData.class);
    }
}
