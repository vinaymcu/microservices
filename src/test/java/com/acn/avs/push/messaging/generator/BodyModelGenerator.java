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
package com.acn.avs.push.messaging.generator;

import com.acn.avs.push.messaging.model.Body;

/**
 * Entity Object generator for HardwareVersion
 * 
 * @author Singh.Saurabh
 *
 */
public class BodyModelGenerator extends AbstractEntityGenerator<Body> {

	@Override
	public Class<Body> getEntityClass() {
		return Body.class;
	}

	@Override
	protected void buildLists() {
		RandomData randomData = new RandomData();
		
		// 0
		withRandomValues(randomData);
	}

	/**
	 * HardwareVersion with random values
	 * 
	 * @param randomData
	 */
	private void withRandomValues(RandomData randomData) {
		Body body = new Body();
		body.setMessagePopupText(randomData.randomString(6));
		body.setMessageText(randomData.randomString(6));
		body.withMessageURL(randomData.randomString(5));
		addValid(body);
	}

	

}
