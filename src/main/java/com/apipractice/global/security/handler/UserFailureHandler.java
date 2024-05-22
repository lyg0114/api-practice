package com.apipractice.global.security.handler;

import static com.apipractice.global.exception.CustomErrorCode.LOGIN_FAILED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.apipractice.global.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.global.security.handler
 * @since : 20.05.24
 */
@RequiredArgsConstructor
@Component
public class UserFailureHandler implements AuthenticationFailureHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception
  ) {
    try {
      ErrorResponse errorResponse = new ErrorResponse(LOGIN_FAILED);
      response.setStatus(LOGIN_FAILED.getHttpStatus().value());
      response.setContentType(APPLICATION_JSON_VALUE);
      response.setCharacterEncoding("utf-8");
      objectMapper.writeValue(response.getWriter(), errorResponse);
    } catch (IOException e) {
      throw new RuntimeException(e); //TODO : 적절한 예외로 변환할 것
    }
  }
}