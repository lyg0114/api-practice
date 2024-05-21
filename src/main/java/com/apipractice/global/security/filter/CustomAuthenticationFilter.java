package com.apipractice.global.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.global.security.filter
 * @since : 20.05.24
 */
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    Authentication token = new UsernamePasswordAuthenticationToken(email, password);
    return authenticationManager.authenticate(token);
  }
}
