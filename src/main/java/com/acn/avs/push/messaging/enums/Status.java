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
 * @author surendra.kumar
 *
 */
public enum Status {

	/** The active. */
	ACTIVE("active", true),
	/** The inactive. */
	INACTIVE("inactive", false);
	
	private String status;

	private Boolean statusValue;

	/**
	 * @param status
	 * @param statusValue
	 */
	private Status(String status, Boolean statusValue) {
		this.status = status;
		this.statusValue = statusValue;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the statusValue
	 */
	public Boolean getStatusValue() {
		return statusValue;
	}

	/**
	 * 
	 * @param searchOperation
	 * @return
	 */
	public static Status getStatusValue(String status) {
		Status tempStatus = null;
		status = status.toLowerCase();
		switch (status) {
		case "active":
			tempStatus = Status.ACTIVE;
			break;
		case "inactive":
			tempStatus = Status.INACTIVE;
			break;
		default:
			tempStatus = null;
		}
		
		return tempStatus;
	}
}
