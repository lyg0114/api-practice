package com.apipractice.global.security.handler;

import static com.apipractice.global.exception.CustomErrorCode.ACCESS_DENIED;
import static com.apipractice.global.exception.CustomErrorCode.LOGIN_FAILED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.apipractice.global.exception.CustomErrorCode;
import com.apipractice.global.exception.CustomException;
import com.apipractice.global.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.global.security.handler
 * @since : 23.05.24
 */
@RequiredArgsConstructor
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
      throws IOException, ServletException {

    response.setStatus(ACCESS_DENIED.getHttpStatus().value());
    response.setContentType(APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("utf-8");

    objectMapper.writeValue(
        response.getWriter(),
        new ErrorResponse(new CustomException(ACCESS_DENIED))
    );
  }
}
