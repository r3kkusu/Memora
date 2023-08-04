package com.memora.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    public static final String[] ENDPOINTS_WHITELIST = {
            "/v1/account/register",
            "/v1/account/login"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(request ->
                        request.requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                                .anyRequest().authenticated())
                        .formLogin(login -> login
                                .loginPage("/v1/account/login")
                                .permitAll()
                        )
                .rememberMe(Customizer.withDefaults());
        return http.build();
    }
}