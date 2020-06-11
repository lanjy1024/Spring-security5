package com.example.demo.controller;

import com.example.demo.dao.UserRepository;
import com.example.demo.po.Role;
import com.example.demo.po.User;
import com.example.demo.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author：lanjy
 * @date：2020/6/10
 * @description：
 */
@RestController
@Slf4j
public class JwtConyroller {

    @Autowired
    private UserRepository  userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/api/guest")
    public String guest(){
        return "this is guest";
    }

    @GetMapping("/api/admin")
    public String admin(){
        return "this is admin";
    }

    @PostMapping("/api/register")
    public boolean register(@RequestBody UserVo userVo){
        User user = new User();
        user.setName(userVo.getUsername());
        user.setPassword(passwordEncoder.encode(userVo.getPassword()));

//        user.setPassword(userVo.getPassword());
        Role role = new Role();
        role.setName(userVo.getRolename());
        role.setUsers(Arrays.asList(user));
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
        return true;
    }
}
