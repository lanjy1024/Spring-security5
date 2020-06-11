package com.example.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

/**
 * @author：lanjy
 * @date：2020/6/10
 * @description：
 */
@Slf4j
public class HelloJwt {
    private static final String KEY_STR = "WcO6kn/Ux3WP/FfnwuyuQvUcpM1Em6Tofzwl+h/ehXc=";

    public static void main(String[] args) {

       /* SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String encodeToString = Base64.getEncoder().encodeToString(key.getEncoded());*/
        //WcO6kn/Ux3WP/FfnwuyuQvUcpM1Em6Tofzwl+h/ehXc=

        //自己生成key
        SecretKey key = Keys.hmacShaKeyFor(KEY_STR.getBytes());
        //使用jwt工具类生成token:创建主题sub=lan,以key作为签名秘钥,并进行压缩
        String token = Jwts.builder()
                .setSubject("lan")
                .setIssuedAt(new Date())//发行时间
                .setIssuer("蓝景优")//发行人
                //.setClaims()//可以是对象,也可以是map,自定义参数
                .signWith(key).compact();
        log.info("token:{}",token);
        log.info("token校验===================");

        Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token);
        String subject = jws.getBody().getSubject();
        System.out.println("token校验===================:"+"lan".equals(subject));


        String subject1 = Jwts.parserBuilder().setSigningKey(KEY_STR.getBytes()).build()
                .parseClaimsJws(token).getBody().getSubject();
        System.out.println("token校验===================:"+"lan".equals(subject1));

        String signature = jws.getSignature();
        System.out.println(signature);
    }
}
