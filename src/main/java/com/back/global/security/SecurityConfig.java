package com.back.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAuthenticationFilter customAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/favicon.ico").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/**").permitAll()
                                .anyRequest().authenticated()//나머지는 인증된 애들만 접근가능
                )
                .headers(
                        headers -> headers
                                .frameOptions(//h2가 iframe이라는걸 쓰기때문에 풀어줌
                                        HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                )
                ).csrf(//csrf
                        AbstractHttpConfigurer::disable
                )
                // 필터를 넣는 위치가 중요하다. 여기서는 UsernamePasswordAuthenticationFilter 전에 커스텀필터를 넣었다.
                .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}