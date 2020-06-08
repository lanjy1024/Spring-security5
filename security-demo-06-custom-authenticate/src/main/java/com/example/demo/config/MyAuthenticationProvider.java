package com.example.demo.config;

import com.example.demo.dao.EmployeeRepository;
import com.example.demo.po.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author：lanjy
 * @date：2020/6/7
 * @description：自定义认证方式
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String pw = authentication.getCredentials().toString();

        Employee employee = employeeRepository.findByName(name).orElseThrow(()-> new BadCredentialsException("用户名或密码不正确"));

        String username = employee.getName();
        String password = employee.getPassword();
        //https://bcrypt-generator.com/
        //String password = "$2y$12$O7GQBRUZdHepSvsfNw2Wdu1rrOIWA4Ocsy97E/ewXpJxs1bltDuGu";

        boolean isPassword = BCrypt.checkpw(pw,password);
        if(isPassword && username.equals(name)){
            return new UsernamePasswordAuthenticationToken(name,pw,new ArrayList());
        }else {
            throw new BadCredentialsException("用户名或密码不正确");
        }


    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
