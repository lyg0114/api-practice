package com.apipractice.global.security.filter;

import static com.apipractice.global.security.filter.CustomAuthenticationFilter.LOGIN_PATH;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.global.security.filter
 * @since : 01.06.24
 */
public class NoNeedAuthentication {

  public static boolean isNoNeedAuthenticationURL(HttpServletRequest request) {
    return request.getPathInfo().equals(LOGIN_PATH)
        || (request.getPathInfo().equals("/api/items/v1") && request.getMethod().equals("GET")); // 상품목록 조회 api
  }
}
