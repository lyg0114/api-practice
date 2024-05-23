package com.apipractice.global.security.filter;

import com.apipractice.domain.member.dto.MemberDto.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.global.security.filter
 * @since : 20.05.24
 *
 *  - id, pw를 이용한 사용자 체크 필터
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  public static final String LOGIN_PATH = "/api/v1/members/login";
  private final AuthenticationManager authenticationManager;
  private ObjectMapper objectMapper;

  public CustomAuthenticationFilter(
      AuthenticationManagerBuilder authManagerBuilder,
      AuthenticationSuccessHandler successHandler,
      AuthenticationFailureHandler failureHandler
  ) {
    this.setPostOnly(true);
    this.setFilterProcessesUrl(LOGIN_PATH);
    this.authenticationManager = authManagerBuilder.getOrBuild();
    this.setAuthenticationSuccessHandler(successHandler);
    this.setAuthenticationFailureHandler(failureHandler);
    this.objectMapper = new ObjectMapper();
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
    LoginRequest loginRequest = createLoginRequest(request);
    Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
    return authenticationManager.authenticate(authentication);
  }

  public LoginRequest createLoginRequest(HttpServletRequest request) {
    try {
      return objectMapper.readValue(
          request.getReader().lines().collect(Collectors.joining(System.lineSeparator())),
          LoginRequest.class);
    } catch (IOException e) {
      //TODO : 적절한 예외를 반환할 수 있도록 처리
      throw new RuntimeException(e);
    }
  }
}
