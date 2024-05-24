package com.apipractice.global.security.filter;

import static com.apipractice.global.exception.CustomErrorCode.INVALID_HTTP_METHOD;
import static com.apipractice.global.security.filter.CustomAuthenticationFilter.LOGIN_PATH;
import static org.springframework.http.HttpMethod.GET;
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
 *
 * - 로그인 요청의 메서드 타입을 체크하는 필터
 */
public class LoginMethodTypeCheckFilter extends OncePerRequestFilter {

  private final ObjectMapper objectMapper;

  public LoginMethodTypeCheckFilter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
  ) throws ServletException, IOException {

    if (request.getMethod().equals(GET.name()) && request.getServletPath().equals(LOGIN_PATH)) {

      CustomErrorCode errorCode = INVALID_HTTP_METHOD;
      response.setStatus(errorCode.getHttpStatus().value());
      response.setContentType(APPLICATION_JSON_VALUE);
      response.setCharacterEncoding("utf-8");
      ErrorResponse errorResponse = new ErrorResponse(errorCode);
      objectMapper.writeValue(response.getWriter(), errorResponse);

      return;
    }

    filterChain.doFilter(request, response);
  }
}
