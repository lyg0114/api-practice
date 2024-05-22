package com.apipractice.global.security;

import com.apipractice.global.security.filter.CustomAuthenticationFilter;
import com.apipractice.global.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.global.security
 * @since : 19.05.24
 */
@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

  private final AuthenticationProvider authenticationProvider;
  private final AuthenticationManagerBuilder authManagerBuilder;
  private final AuthenticationSuccessHandler successHandler;
  private final AuthenticationFailureHandler failureHandler;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .addFilterBefore(getAuthorizationFilter(), AuthorizationFilter.class) // 접근 인가 필터, AuthorizationFilter 는 order가 마지막 바로 앞
        .addFilter(getAuthenticationFilter()) // 로그인 필터
        .authenticationProvider(authenticationProvider)
        .authorizeHttpRequests(authorize -> authorize
            .anyRequest()
            .permitAll()
        )
    ;
    return http.build();
  }

  private CustomAuthorizationFilter getAuthorizationFilter() {
    return new CustomAuthorizationFilter();
  }

  private CustomAuthenticationFilter getAuthenticationFilter() {
    return new CustomAuthenticationFilter(authManagerBuilder, successHandler, failureHandler);
  }
}
