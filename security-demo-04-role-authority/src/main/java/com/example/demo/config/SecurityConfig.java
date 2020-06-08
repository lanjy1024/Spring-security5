package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


/**
 * @author：lanjy
 * @date：2020/6/6
 * @description：
 */
@Configuration
public class SecurityConfig/* extends WebSecurityConfigurerAdapter*/ {

    //创建用户名密码方式1
   /* @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(encoder().encode("123456"))
                .roles("USER");
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(encoder().encode("123456"))
                .roles("ADMIN");
    }*/
    //创建用户名密码方式2
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.withUsername("user")
                        .password(encoder().encode("123456"))
                        .roles("USER")
                        .build()
        );
        manager.createUser(
                User.withUsername("admin")
                        .password(encoder().encode("123456"))
                        .roles("ADMIN")
                        .build()
        );
        return manager;
    }
    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Configuration
    @Order(1)
    public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter{
        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.antMatcher("/admin/**")//匹配
                .authorizeRequests()//授权请求
                .antMatchers("/css/**")
                .permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")//添加角色权限
                .anyRequest()//所有请求
                .authenticated()//已认证

                .and()
                .formLogin()
                .loginPage("/admin/login")
                .defaultSuccessUrl("/admin/home")
                .permitAll()

                .and()
                .logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login?logout")
                .permitAll();
        }
    }
    @Configuration
    @Order(2)
    public static class UserSecurityConfig extends WebSecurityConfigurerAdapter{
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/user/**")//匹配
                .authorizeRequests()//授权请求
                .antMatchers("/css/**")
                .permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest()//所有请求
                .authenticated()//已认证

                .and()
                .formLogin()
                .loginPage("/user/login")
                .defaultSuccessUrl("/user/home")
                .permitAll()

                .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/user/login?logout")
                .permitAll();
        }
    }


}
