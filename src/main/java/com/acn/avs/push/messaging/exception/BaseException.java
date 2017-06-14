/***************************************************************************
 * Copyright (C) Accenture
 *
 * The reproduction, transmission or use of this document or its contents is not permitted without
 * prior express written consent of Accenture. Offenders will be liable for damages. All rights,
 * including but not limited to rights created by patent grant or registration of a utility model or
 * design, are reserved.
 *
 * Accenture reserves the right to modify technical specifications and features.
 *
 * Technical specifications and features are binding only insofar as they are specifically and
 * expressly agreed upon in a written contract.
 *
 **************************************************************************/
package com.acn.avs.push.messaging.exception;

import java.util.Arrays;

import com.acn.avs.push.messaging.enums.ErrorCode;


/**
 * The Class BaseException.
 *
 * @author Sumit.Sharma
 * @version 1.0
 */

public abstract class BaseException extends RuntimeException {
  
  /** Use serialVersionUID for interoperability. */
  private static final long serialVersionUID = -1006030314433106516L;
  
  /** The error code. */
  private final ErrorCode errorCode;
  
  /** The args. */
  private final String[] args;
  
  /* customMessage */
  private String customMessage;
  
  /**
   * Instantiates a new base exception.
   *
   * @param errorCode the error code
   */
  public BaseException(final ErrorCode errorCode) {
    super();
    this.errorCode = errorCode;
    this.args = null;
  }
  
  /**
   * Instantiates a new base exception.
   *
   * @param errorCode the error code
   * @param msgParams the msg params
   */
  public BaseException(final ErrorCode errorCode, final String[] msgParams) {
    super();
    this.errorCode = errorCode;
    this.args = Arrays.copyOf(msgParams, msgParams.length);
  }
  
  /**
   * Instantiates a new base exception.
   *
   * @param errorCode the error code
   * @param msgParams the msg params
   * @param throwable the throwable
   */
  public BaseException(final ErrorCode errorCode, final String[] msgParams,
      final Throwable throwable) {
    super(throwable);
    this.errorCode = errorCode;
    this.args = Arrays.copyOf(msgParams, msgParams.length);
  }
  
  /**
   * Instantiates a new base exception.
   *
   * @param errorCode the error code
   * @param msgParams the msg params
   * @param customMessage
   */
  public BaseException(final ErrorCode errorCode, final String[] msgParams, String customMessage) {
    super();
    this.errorCode = errorCode;
    this.args = Arrays.copyOf(msgParams, msgParams.length);
    this.customMessage = customMessage;
  }
  
  /**
   * Instantiates a new base exception.
   *
   * @param errorCode the error code
   * @param throwable the throwable
   */
  public BaseException(final ErrorCode errorCode, final Throwable throwable) {
    super(throwable);
    this.errorCode = errorCode;
    this.args = null;
  }
  
  /**
   * Gets the args.
   *
   * @return the args
   */
  public String[] getArgs() {
    if (args != null) {
      return Arrays.copyOf(args, args.length);
    }
    return new String[0];
  }
  
  /**
   * Gets the error code.
   *
   * @return the error code
   */
  public ErrorCode getErrorCode() {
    return errorCode;
  }
  
  /**
   * @return
   */
  public String getCustomMessage() {
    return customMessage;
  }
  
  /**
   * @param customMessage
   */
  public void setCustomMessage(String customMessage) {
    this.customMessage = customMessage;
  }
}
