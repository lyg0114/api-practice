package com.apipractice.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.global.exception
 * @since : 18.05.24
 */
@Getter
@RequiredArgsConstructor
public enum CustomErrorCode {
  // User
  EMAIL_ALREADY_EXIST(BAD_REQUEST, "이미 존재하는 메일주소 입니다."),
  NICKNAME_ALREADY_EXIST(BAD_REQUEST, "이미 존재하는 닉네임입니다."),
  LOGIN_FAILED(UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다."),
  USER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다."),

  // General
  INVALID_HTTP_METHOD(METHOD_NOT_ALLOWED, "잘못된 Http Method 요청입니다."),
  INVALID_VALUE(BAD_REQUEST, "잘못된 입력값입니다."),
  UNKNON_INVALID_VALUE(BAD_REQUEST, "입력값을 확인해 주세요."),
  SERVER_INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");

  private final HttpStatus httpStatus;
  private final String errorMessage;
}
