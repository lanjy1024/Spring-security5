package com.example.demo.service;

import com.example.demo.dao.UserRepository;
import com.example.demo.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author：lanjy
 * @date：2020/6/11
 * @description：
 */
@Service
@Slf4j
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 通过用户名查找数据库,获取用户姓名密码角色
     * 封装成UserDetails
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("MyUserDetailService=======通过用户名查找数据库,获取用户姓名密码角色=====loadUserByUsername");
        User user = userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("user not exit"));
        List<SimpleGrantedAuthority> authorities =  user.getRoles()
                .stream()
                .map(
                        role -> new SimpleGrantedAuthority(role.getName())
                )
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(),authorities);
    }
}