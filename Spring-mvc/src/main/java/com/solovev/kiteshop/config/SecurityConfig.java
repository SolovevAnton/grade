package com.solovev.kiteshop.config;

import com.solovev.kiteshop.common.APINamings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(a -> a
                        .requestMatchers(APINamings.ORDER + "/**").authenticated()
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.
                        ignoringRequestMatchers(APINamings.LOGOUT, APINamings.REGISTRATION, APINamings.LOGIN))
                .formLogin(form -> form
                        .loginPage(APINamings.LOGIN)
                        .defaultSuccessUrl(APINamings.CATALOG))
                .logout(Customizer.withDefaults())
                .build();
    }
}
