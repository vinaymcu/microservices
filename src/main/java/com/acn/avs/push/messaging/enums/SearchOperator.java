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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author surendra.kumar
 *
 */
public enum SearchOperator {

	/** The equals. */
	EQUALS,
	/** The contains. */
	CONTAINS,
	/** The beginswith. */
	BEGINSWITH;

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = Logger.getLogger(SearchOperator.class);

	/**
	 * 
	 * @param searchOperation
	 * @return
	 */
	public static SearchOperator getOperation(String searchOperation) {
		try {
			if (StringUtils.isEmpty(searchOperation)) {
				return SearchOperator.EQUALS;
			}
			return SearchOperator.valueOf(searchOperation);
		} catch (IllegalArgumentException exception) {
			LOGGER.error("exception occured during getOperation", exception);
			return SearchOperator.EQUALS;
		}
	}
}
