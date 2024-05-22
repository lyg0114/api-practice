package com.apipractice.global.security.filter;

import static com.apipractice.global.exception.CustomErrorCode.INVALID_HTTP_METHOD;
import static com.apipractice.global.security.filter.CustomAuthenticationFilter.LOGIN_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.apipractice.global.exception.CustomErrorCode;
import com.apipractice.global.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.global.security.filter
 * @since : 22.05.24
 */
public class LoginMethodTypeCheckFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
  ) throws ServletException, IOException {

    if (request.getMethod().equals(HttpMethod.GET.name())
        && request.getRequestURI().equals(LOGIN_PATH)) {

      CustomErrorCode errorCode = INVALID_HTTP_METHOD;
      response.setStatus(errorCode.getHttpStatus().value());
      response.setContentType(APPLICATION_JSON_VALUE);
      response.setCharacterEncoding("utf-8");
      ErrorResponse errorResponse = new ErrorResponse(errorCode);
      new ObjectMapper().writeValue(response.getWriter(), errorResponse);

      return;
    }

    filterChain.doFilter(request, response);
  }
}
