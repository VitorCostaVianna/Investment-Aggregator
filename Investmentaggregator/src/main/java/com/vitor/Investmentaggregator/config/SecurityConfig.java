package com.vitor.Investmentaggregator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST, "/v1/users/*").permitAll()
                .requestMatchers(HttpMethod.GET, "v1/users/*").permitAll()
                .requestMatchers(HttpMethod.PUT, "v1/users/*").permitAll()
                .anyRequest().permitAll() // allow all requests
            )
            .csrf(csrf -> csrf.disable()); //
    
        return http.build();
    }
    
}
