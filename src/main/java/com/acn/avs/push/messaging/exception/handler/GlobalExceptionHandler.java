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

package com.acn.avs.push.messaging.exception.handler;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.acn.avs.push.messaging.enums.ErrorCode;
import com.acn.avs.push.messaging.exception.BaseException;
import com.acn.avs.push.messaging.model.GenericResponse;

/**
 * The Class GlobalExceptionHandler.
 */
@Order(value = 1)
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	/** The Constant LOGGRE. */
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/** The message source. */
	@Autowired
	private MessageSourceAccessor messageSource;

	/**
	 * Handle base exception.
	 *
	 * @param request
	 *            the request
	 * @param exception
	 * @param e
	 *            the e
	 * @return the response
	 */
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ExceptionHandler(value = BaseException.class)
	public GenericResponse handleBaseException(final HttpServletRequest request, final BaseException exception) {
		final GenericResponse response = new GenericResponse();

		response.setSystemTime(System.currentTimeMillis());
		response.setResultCode(exception.getErrorCode().getCode());
		response.setResultDescription(
				messageSource.getMessage(exception.getErrorCode().getCode(), exception.getArgs()));
		LOGGER.error("Error occurred while serving request ", exception);
		return response;
	}
	
	/**
     * handle HttpRequestMethodNotSupportedException
     * 
     * @param hrme
     */
    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Invalid request method found.")
    public void handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException hrme) {
        LOGGER.error("HttpRequestMethodNotSupportedException found. : ", hrme);
    }

	/**
	 * Handle exception.
	 *
	 * @param request
	 *            the request
	 * @param exception
	 * @param e
	 *            the e
	 * @return the response
	 */
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public GenericResponse handleRequestValidationFailedException(final HttpServletRequest request,
			final Exception exception) {
		final GenericResponse response = new GenericResponse();
		String message = exception.getMessage();

		if (message == null) {
			message = exception.getCause().getMessage();
		}

		response.setSystemTime(System.currentTimeMillis());
		response.setResultCode(ErrorCode.REQUEST_VALIDATION_FAILED.getCode());
		response.setResultDescription(
				messageSource.getMessage(ErrorCode.REQUEST_VALIDATION_FAILED.getCode(), new String[] { message }));
		LOGGER.error("Error occurred while serving request ", exception);

		return response;
	}
	
	
	/**
	 * Handle MethodArgumentTypeMismatchException .
	 *
	 * @param request
	 *            the request
	 * @param exception
	 * @param e
	 *            the e
	 * @return the response
	 */
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
	public GenericResponse handleMethodArgTypeMismatchException(final HttpServletRequest request,
			final Exception exception) {
		final GenericResponse response = new GenericResponse();
		String message = exception.getMessage();

		if (message == null) {
			message = exception.getCause().getMessage();
		}

		response.setSystemTime(System.currentTimeMillis());
		response.setResultCode(ErrorCode.INVALID_PARAMETER.getCode());
		response.setResultDescription(
				messageSource.getMessage(ErrorCode.INVALID_PARAMETER.getCode(), new String[] { message }));
		LOGGER.error("Error occurred while serving request ", exception);

		return response;
	}
	
	/**
	 * Handle MethodArgumentTypeMismatchException .
	 *
	 * @param request
	 *            the request
	 * @param exception
	 * @param e
	 *            the e
	 * @return the response
	 */
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ExceptionHandler(value = MissingServletRequestParameterException.class)
	public GenericResponse handleMissingServletRequestParameterException(final HttpServletRequest request,
			final Exception exception) {
		final GenericResponse response = new GenericResponse();
		String message = exception.getMessage();

		if (message == null) {
			message = exception.getCause().getMessage();
		}

		response.setSystemTime(System.currentTimeMillis());
		response.setResultCode(ErrorCode.MISSING_PARAMETER.getCode());
		response.setResultDescription(
				messageSource.getMessage(ErrorCode.MISSING_PARAMETER.getCode(), new String[] { message }));
		LOGGER.error("Error occurred while serving request ", exception);

		return response;
	}

	/**
	 * Handle exception.
	 *
	 * @param request
	 *            the request
	 * @param exception
	 * @param e
	 *            the e
	 * @return the response
	 */
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ExceptionHandler(value = Exception.class)
	public GenericResponse handleException(final HttpServletRequest request, final Exception exception) {
		final GenericResponse response = new GenericResponse();
		String message = exception.getMessage();

		if (message == null) {
			message = exception.getCause().getMessage();
		}

		response.setSystemTime(System.currentTimeMillis());
		response.setResultCode(ErrorCode.GENERIC_ERROR.getCode());
		response.setResultDescription(
				messageSource.getMessage(ErrorCode.GENERIC_ERROR.getCode(), new String[] { message }));
		LOGGER.error("Error occurred while serving request ", exception);

		return response;
	}

	/**
	 * The Init method.
	 */
	@PostConstruct
	public void init() {
		LOGGER.info("Initializing Global Exception Handler  . ");

	}

}
