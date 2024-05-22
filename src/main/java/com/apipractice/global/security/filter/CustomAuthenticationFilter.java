package com.apipractice.global.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

  public static final String LOGIN_PATH = "/api/v1/members/login";
  private final AuthenticationManager authenticationManager;

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
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    Authentication token = new UsernamePasswordAuthenticationToken(email, password);
    return authenticationManager.authenticate(token);
  }
}
