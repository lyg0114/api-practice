package com.apipractice.global.security;

import com.apipractice.global.security.filter.CustomAuthenticationFilter;
import com.apipractice.global.security.filter.CustomAuthorizationFilter;
import com.apipractice.global.security.filter.LoginMethodTypeCheckFilter;
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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

  /**
   * @param http
   * @return
   * @throws Exception
   *  - 필터 호출 순서
   *     - 1. LoginMethodTypeCheckFilter.class
   *     - 2. CustomAuthorizationFilter.calss
   *     - 3. CustomAuthenticationFilter.class
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .addFilter(getAuthenticationFilter())
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(getAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(getLoginMethodTypeCheckFilter(), CustomAuthorizationFilter.class)
        .authorizeHttpRequests(authorize -> authorize
            .anyRequest()
            .permitAll()
        )
    ;
    return http.build();
  }

  /**
   * @return CustomAuthenticationFilter
   *  - 권한 체크 필터
   */
  private CustomAuthorizationFilter getAuthorizationFilter() {
    return new CustomAuthorizationFilter();
  }

  /**
   * @return CustomAuthenticationFilter
   *  - 인증 필터
   */
  private CustomAuthenticationFilter getAuthenticationFilter() {
    return new CustomAuthenticationFilter(authManagerBuilder, successHandler, failureHandler);
  }

  /**
   * @return CustomAuthenticationFilter
   *  - 로그인 method, url 체크 필터
   */
  private LoginMethodTypeCheckFilter getLoginMethodTypeCheckFilter() {
    return new LoginMethodTypeCheckFilter();
  }
}
