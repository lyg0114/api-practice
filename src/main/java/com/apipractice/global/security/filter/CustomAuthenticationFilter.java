package com.apipractice.global.security.filter;

import static com.apipractice.global.exception.CustomErrorCode.INVALID_HTTP_METHOD;

import com.apipractice.global.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.global.security.filter
 * @since : 20.05.24
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  public CustomAuthenticationFilter(
      AuthenticationManagerBuilder authManagerBuilder,
      AuthenticationSuccessHandler successHandler,
      AuthenticationFailureHandler failureHandler
      ) {
    this.setPostOnly(true);
    this.setFilterProcessesUrl("/api/v1/members/login");
    this.authenticationManager = authManagerBuilder.getOrBuild();
    this.setAuthenticationSuccessHandler(successHandler);
    this.setAuthenticationFailureHandler(failureHandler);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
    if (request.getMethod().equals(HttpMethod.GET.name())) { //TODO : CustomException 을 처리해줄 handler 작업 필요함
      throw new CustomException(INVALID_HTTP_METHOD);
    }
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    Authentication token = new UsernamePasswordAuthenticationToken(email, password);
    return authenticationManager.authenticate(token);
  }
}