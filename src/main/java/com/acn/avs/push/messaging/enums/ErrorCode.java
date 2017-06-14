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

package com.acn.avs.push.messaging.enums;

/**
 * 
 * ErrorCode description
 *
 */
public enum ErrorCode {

	/** The success. */
	SUCCESS("ACN_200"),

	/** The generic error. */
	GENERIC_ERROR("ACN_300"),

	/** MISSING_PARAMETER */
	MISSING_PARAMETER("ACN_3000"),
	
	/** INVALID_PARAMETER */
	INVALID_PARAMETER("ACN_3019"),
	
	/**REQUEST_VALIDATION_FAILED*/
	REQUEST_VALIDATION_FAILED("ACN_3020"),
	/** Maximum 50 message are allowed */
	MAXIMUM_50_MESSAGES("ACN_9200"),

	/** Maximum 3 filters are allowed */
	MAXIMUM_3_FILTERS("ACN_9201"),

	/** Filters are missing */
	FILTERS_MISSING("ACN_9202"),

	/** Message’s messageId already exist */
	MESSAGE_EXIST("ACN_9203"),

	/** Message's messageId does not exist. */
	MESSAGE_NOT_EXIST("ACN_9204"),

	/** MESSAGE ID ALREADY EXIST in db */
	MAXIMUM_ACTIVATION_LIMIT("ACN_9205"),

	/** Missing association when status active */
	MISSING_ASSOCIATION("ACN_9206"),

	/** Active meesages can't deleted */
	ACTIVE_CANT_DELETED("ACN_9207"),
	
	/** MESSAGE_ALREADY_INACTIVE */
	MESSAGE_ALREADY_INACTIVE("ACN_9208"),
	
	/** MESSAGE_ALREADY_ACTIVE */
	MESSAGE_ALREADY_ACTIVE("ACN_9209"),
	
	/** MESSAGE_PREVIOUSLY_ACTIVE */
	MESSAGE_PREVIOUSLY_ACTIVE("ACN_9210"),

	/** DISPLAY_DATE_MUST_BE_LESS_THAN_VALIDITY_DATE */
	DISPLAY_DATE_MUST_BE_LESS_THAN_VALIDITY_DATE("ACN_9211"),

	/** INVALID_DISPLAY_DATE_FORMAT */
	INVALID_DISPLAY_DATE_FORMAT("ACN_9212"),

	/** INVALID_DISPLAY_DATE_FORMAT */
	INVALID_VALIDITY_DATE_FORMAT("ACN_9213"),

	/** VALIDITY_DATE_CANNOT_BE_PAST */
	VALIDITY_DATE_CANNOT_BE_PAST("ACN_9214"),
	
	/** DISPLAY_DATE_CANNOT_BE_PAST */
	DISPLAY_DATE_CANNOT_BE_PAST("ACN_9215"),
	
	/** DISPLAY_DATE_CANNOT_BE_PAST */
	INVALID_DISTRIBUTION_MODE("ACN_9216");
	

	/** The error code. */
	private String errorCode;

	/**
	 * Instantiates a new error code.
	 *
	 * @param errorCode
	 *            the error code
	 */
	private ErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return this.errorCode;
	}
}
