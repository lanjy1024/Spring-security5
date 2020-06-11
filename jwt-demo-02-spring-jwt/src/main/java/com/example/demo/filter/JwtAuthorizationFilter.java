package com.example.demo.filter;

import com.example.demo.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.thymeleaf.util.StringUtils;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author：lanjy
 * @date：2020/6/10
 * @description：jwt授权的过滤器
 */
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {



    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 进行token校验
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("JwtAuthorizationFilter=====jwt授权的过滤器======doFilterInternal");
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);
        if (authenticationToken == null) {
            chain.doFilter(request,response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request,response);
    }

    /**
     * 客户端拿到token之后,在请求头中携带该token便可访问其他的资源
     * 从请求头中获取token
     * @param request
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        try{
            log.info("从请求头中获取token");
            String token = request.getHeader(SecurityConstants.Authorization);
            if(!StringUtils.isEmpty(token) && token.startsWith(SecurityConstants.TOKEN_PREFIX)){
                //获取token,如果签名正确的话,获取token是没有问题的
                Jws<Claims> jws = Jwts.parserBuilder()
                        .setSigningKey(SecurityConstants.KEY_STR.getBytes())
                        .build()
                        .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX,""));
                //获取用户名
                String username = jws.getBody().getSubject();
                //获取角色
                List<?> list = (List<?>) jws.getBody().get(SecurityConstants.ROLES);
                List<SimpleGrantedAuthority> authorities = list.stream().map(
                        authority -> new SimpleGrantedAuthority((String) authority)
                ).collect(Collectors.toList());
                if (!StringUtils.isEmpty(username)) {
                    log.info("从请求头中获取token==校验成功");
                    return new UsernamePasswordAuthenticationToken(username,null,authorities);
                }
            }
        }catch (Exception e){
            log.warn("进行token校验异常:{}",e);
        }
        return null;
    }
}
