package com.travel.toy3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/api/members/admin"
                        ).authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin().disable() // form Login 비활성화
                .logout(logout -> logout
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true) // 로그아웃시인증정보클리어 ( SecurityContext )
                        .invalidateHttpSession(true) // 세션 무효화
                )
                .httpBasic().disable() // HTTP Basic 인증 비활성화
        ;
        return http.build();
    }
}
