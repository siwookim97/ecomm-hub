package com.likelion.ecommhub.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class AuthenticationConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .and()
                .formLogin()
                .loginPage("/usr/member/login")
                .loginProcessingUrl("/usr/member/login")
                .defaultSuccessUrl("/usr/member/myPage")
                .failureUrl("/usr/member/login")
                .and()
                .logout()
                .logoutUrl("/usr/member/logout")
                .logoutSuccessUrl("/usr/member/login")
                .deleteCookies("JSESSIONID", "remember-me")
                .and().build();
    }
}