package com.apipractice.global.security;

import com.apipractice.global.security.filter.CustomAuthenticationFilter;
import com.apipractice.global.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
  private final CustomAuthorizationFilter authorizationFilter;
  private final AuthenticationManagerBuilder authManagerBuilder;
  private final AuthenticationSuccessHandler successHandler;
  private final AuthenticationFailureHandler failureHandler;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(authManagerBuilder.getOrBuild());
    authenticationFilter.setFilterProcessesUrl("/api/v1/members/login");
    authenticationFilter.setAuthenticationSuccessHandler(successHandler);
    authenticationFilter.setAuthenticationFailureHandler(failureHandler);

    http
        .csrf(AbstractHttpConfigurer::disable)
        .addFilterBefore(authorizationFilter, AuthorizationFilter.class) // 접근 인가 필터, AuthorizationFilter 는 order가 마지막 바로 앞
        .addFilter(authenticationFilter) // 로그인 필터
        .authenticationProvider(authenticationProvider)
        .authorizeHttpRequests(authorize -> authorize
            .anyRequest()
            .permitAll()
        )
    ;
    return http.build();
  }

  @Bean
  public FilterRegistrationBean<CustomAuthorizationFilter> customAuthorizationFilterRegistration(CustomAuthorizationFilter filter) {
    FilterRegistrationBean<CustomAuthorizationFilter> registration = new FilterRegistrationBean<>(filter);
    registration.setEnabled(false);
    return registration;
  }

  // TODO : CustomAuthenticationFilter 을 Bean 으로 등록할 수 있는지 체크 필요
  // @Bean
  public FilterRegistrationBean<CustomAuthenticationFilter> customAuthenticationFilterRegistration(CustomAuthenticationFilter filter) {
    FilterRegistrationBean<CustomAuthenticationFilter> registration = new FilterRegistrationBean<>(filter);
    registration.setEnabled(false);
    return registration;
  }
}
