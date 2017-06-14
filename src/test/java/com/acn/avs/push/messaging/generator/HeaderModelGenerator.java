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

import com.acn.avs.push.messaging.model.Header;
import com.acn.avs.push.messaging.model.Header.DistributionMode;
import com.acn.avs.push.messaging.model.Header.MessageType;

/**
 * Entity Object generator for HardwareVersion
 * 
 * @author Singh.Saurabh
 *
 */
public class HeaderModelGenerator extends AbstractEntityGenerator<Header> {

	@Override
	public Class<Header> getEntityClass() {
		return Header.class;
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
		Header header = new Header();
		header.setMessageId(1);
		header.setAutoHideTime(randomData.randomInteger(3));
		header.setDisplayDateTime("10/02/2017 10:22");
		header.setDistributionMode(DistributionMode.UNICAST);
		header.setIsPopupMessage(randomData.randomBoolean());
		header.setMessageName(randomData.randomString(5));
		header.setMessageType(MessageType.SERVICE);
		header.setStatus(randomData.randomString(5));
		header.setValidityDateTime("11/02/2017 10:22");
		addValid(header);
		
		//with status active
		Header header2 = new Header();
		header2.setMessageId(1);
		header2.setAutoHideTime(randomData.randomInteger(3));
		header2.setDisplayDateTime("10/02/2017 10:22");
		header2.setDistributionMode(DistributionMode.UNICAST);
		header2.setIsPopupMessage(randomData.randomBoolean());
		header2.setMessageName(randomData.randomString(5));
		header2.setMessageType(MessageType.SERVICE);
		header2.setStatus("active");
		header2.setValidityDateTime("11/02/2017 10:22");
		addValid(header2);
		
		//without id
		Header header3 = new Header();
		header3.setAutoHideTime(randomData.randomInteger(3));
		header3.setDisplayDateTime("10/02/2017 10:22");
		header3.setDistributionMode(DistributionMode.UNICAST);
		header3.setIsPopupMessage(randomData.randomBoolean());
		header3.setMessageName(randomData.randomString(5));
		header3.setMessageType(MessageType.SERVICE);
		header3.setStatus("active");
		header3.setValidityDateTime("11/02/2017 10:22");
		addInvalid(header3);
	}

	

}
