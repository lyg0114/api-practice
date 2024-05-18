package com.apipractice.global.exception;

import lombok.Getter;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.global.exception
 * @since : 18.05.24
 */
@Getter
public class CustomException extends RuntimeException {

  private final CustomErrorCode errorCode;
  private final String errorMessage;

  // Without Cause Exception
  public CustomException(CustomErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
    this.errorMessage = errorCode.getErrorMessage();
  }

  public CustomException(CustomErrorCode errorCode, String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  // With Cause Exception
  public CustomException(CustomErrorCode errorCode, Exception cause) {
    super(errorCode.getErrorMessage(), cause);
    this.errorCode = errorCode;
    this.errorMessage = errorCode.getErrorMessage();
  }

  public CustomException(CustomErrorCode errorCode, String errorMessage, Exception cause) {
    super(errorMessage, cause);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }
}
