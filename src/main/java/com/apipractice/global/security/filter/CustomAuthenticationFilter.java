package com.apipractice.global.security.filter;

import static com.apipractice.global.exception.CustomErrorCode.INVALID_VALUE;

import com.apipractice.domain.member.dto.MemberDto.LoginRequest;
import com.apipractice.global.aop.annotation.Trace;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
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
 *  - id, pw 를 이용한 사용자 체크 필터
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

  /**
   * @param request
   * @param response
   * @return authentication
   * @throws AuthenticationException
   *  - Provider에서 사용할 Authentication 객체 생성 및 반환
   */
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
    LoginRequest loginRequest = createLoginRequest(request);
    Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
    return authenticationManager.authenticate(authentication);
  }

  /**
   * @param request
   * @return
   *   - json으로부터 LoginRequest 객체 생성 및 반환
   */
  public LoginRequest createLoginRequest(HttpServletRequest request) {
    try {
      return objectMapper.readValue(getUserInfo(request), LoginRequest.class);
    } catch (IOException e) {
      throw new AuthenticationServiceException(INVALID_VALUE.getErrorMessage(), e);
    }
  }

  /**
   * @param request
   * @return
   * @throws IOException
   *
   *   -  request 로 부터 JSON stirng 추출
   */
  private String getUserInfo(HttpServletRequest request) throws IOException {
    return request
        .getReader()
        .lines()
        .collect(Collectors.joining(System.lineSeparator()));
  }
}
