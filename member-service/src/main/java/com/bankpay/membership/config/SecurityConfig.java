//package com.bankpay.membership.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable() // H2 콘솔을 사용할 때 CSRF를 비활성화
//                .authorizeRequests()
//                .antMatchers("/h2-console/**").permitAll() // H2 콘솔에 대한 접근 허용
//                .anyRequest().authenticated()
//                .and()
//                .headers().frameOptions().disable(); // H2 콘솔의 frame 사용 허용
//    }
//}
