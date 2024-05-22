package com.apipractice.global.security.filter;

import static com.apipractice.global.security.filter.CustomAuthenticationFilter.LOGIN_PATH;
import static com.apipractice.global.security.service.JwtService.TOKEN_HEADER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.apipractice.global.exception.CustomErrorCode;
import com.apipractice.global.exception.ErrorResponse;
import com.apipractice.global.security.service.JwtService;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.global.security.filter
 * @since : 20.05.24
 */
@Slf4j
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
  ) throws ServletException, IOException {

    if (isAuthenticationURL(request)) {
      filterChain.doFilter(request, response);
    }

    String authorizationHeader = request.getHeader(AUTHORIZATION);
    CustomErrorCode errorCode = null;

    // Header에 토큰이 존재하지 않을 시
    if (!hasToken(authorizationHeader)) {
      errorCode = CustomErrorCode.TOKEN_NOT_EXIST;
    } else {
      try {
        String accessToken = authorizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        DecodedJWT decodedJWT = jwtService.verifyToken(accessToken);

        String role = decodedJWT.getClaim("role").asString();
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
        String email = decodedJWT.getSubject();

        // SecurityContextHolder에 accessToken 포함하여 저장
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, accessToken, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

      } catch (TokenExpiredException e) {
        // Access Token 만료
        errorCode = CustomErrorCode.ACCESS_TOKEN_EXPIRED;
      } catch (Exception e) {
        // 유효하지 않은 Access Token
        errorCode = CustomErrorCode.INVALID_TOKEN;
      }
    }

    if (errorCode != null) {
      response.setStatus(errorCode.getHttpStatus().value());
      response.setContentType(APPLICATION_JSON_VALUE);
      response.setCharacterEncoding("utf-8");
      ErrorResponse errorResponse = new ErrorResponse(errorCode);
      objectMapper.writeValue(response.getWriter(), errorResponse);
    }
  }

  private boolean hasToken(String authorizationHeader) {
    return authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_HEADER_PREFIX);
  }

  private boolean isAuthenticationURL(HttpServletRequest request) {
    return request.getServletPath().equals(LOGIN_PATH);
  }
}
