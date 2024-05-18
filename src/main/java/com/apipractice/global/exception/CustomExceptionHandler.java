package com.apipractice.global.exception;

import static com.apipractice.global.exception.CustomErrorCode.INVALID_VALUE;
import static com.apipractice.global.exception.CustomErrorCode.SERVER_INTERNAL_ERROR;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.global.exception
 * @since : 18.05.24
 */
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

  // CustomException
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(
      CustomException ex, HttpServletRequest request
  ) {
    log.error("[CustomException] url: {} | errorCode: {} | errorMessage: {} | cause Exception: ",
        request.getRequestURL(), ex.getErrorCode(), ex.getErrorMessage(), ex.getCause());

    return ResponseEntity
        .status(ex.getErrorCode().getHttpStatus())
        .body(new ErrorResponse(ex));
  }

  // Validation Exception
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(
      MethodArgumentNotValidException ex,
      HttpServletRequest request
  ) {
    String validationMessage = Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();
    log.error("[MethodArgumentNotValidException] url: {} | errorCode: {} | errorMessage: {} | cause Exception: ",
        request.getRequestURL(), INVALID_VALUE, validationMessage, ex);

    CustomException customException = new CustomException(INVALID_VALUE, validationMessage);
    return ResponseEntity
        .status(INVALID_VALUE.getHttpStatus())
        .body(new ErrorResponse(customException));
  }

  // Other Error
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> exception(Exception e, HttpServletRequest request) {
    log.error("[Common Exception] url: {} | errorMessage: {}",
        request.getRequestURL(), e.getMessage());
    return ResponseEntity
        .status(SERVER_INTERNAL_ERROR.getHttpStatus())
        .body(new ErrorResponse(SERVER_INTERNAL_ERROR));
  }
}
