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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.acn.avs.push.messaging.entity.Messages;
import com.acn.avs.push.messaging.entity.StbAddressing;

/**
 * Entity Object generator for HardwareVersion
 * 
 * @author Singh.Saurabh
 *
 */
public class MessagesEntityGenerator extends AbstractEntityGenerator<Messages> {

	@Override
	public Class<Messages> getEntityClass() {
		return Messages.class;
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
		
		List<StbAddressing> stbAddressingList = new ArrayList<>();
		List<StbAddressing> invstbAddressingList = new ArrayList<>();
		StbAddressing invstbAddressing = new StbAddressing();
		invstbAddressingList.add(invstbAddressing);
		
		Messages messages = new Messages();
		StbAddressing stbAddressing = new StbAddressing();
		messages.setSubscriberId("1");
		messages.setCreationDatetime(BigInteger.valueOf(1414141414L));
		stbAddressing.setMacAddress("ABCDE");
		stbAddressingList.add(stbAddressing);
		messages.setMessageId(1L);
		messages.setMultiCast(false);
		messages.setStbAddressingList(stbAddressingList);
		messages.setStatus(Boolean.TRUE);
		addValid(messages);

		// without mac
		Messages messages2 = new Messages();
		messages2.setCreationDatetime(BigInteger.valueOf(1414141414L));
		messages2.setSubscriberId("1");
		messages2.setMessageId(1L);
		messages2.setStatus(Boolean.TRUE);
		messages2.setMultiCast(false);
		addInvalid(messages2);

		// without mac
		Messages invmessages = new Messages();
		invmessages.setCreationDatetime(BigInteger.valueOf(1414141414L));
		invmessages.setSubscriberId("1");
		invmessages.setMessageId(1L);
		invmessages.setStatus(Boolean.TRUE);
		invmessages.setStbAddressingList(invstbAddressingList);
		invmessages.setMultiCast(false);
		addInvalid(invmessages);

		// with status false
		Messages messages3 = new Messages();
		messages3.setCreationDatetime(BigInteger.valueOf(1414141414L));
		messages3.setSubscriberId("1");
		messages3.setStbAddressingList(stbAddressingList);
		messages3.setStatus(Boolean.FALSE);
		messages3.setMessageId(5L);
		addValid(messages3);

		// without message id
		Messages messages4 = new Messages();
		messages4.setCreationDatetime(BigInteger.valueOf(1414141414L));
		messages4.setSubscriberId("1");
		messages4.setStbAddressingList(stbAddressingList);
		messages4.setStatus(Boolean.TRUE);
		addValid(messages4);

		// with status false but Previously Active
		Messages messages5 = new Messages();
		messages5.setCreationDatetime(BigInteger.valueOf(1414141414L));
		messages5.setSubscriberId("1");
		messages5.setStbAddressingList(stbAddressingList);
		messages5.setStatus(Boolean.FALSE);
		messages5.setMessageId(5L);
		messages5.setMultiCast(false);
		messages5.setActivationDatetime(BigInteger.valueOf(1472125463));
		addValid(messages5);

		Messages messages6 = new Messages();
		messages6.setSubscriberId("1");
		messages6.setCreationDatetime(BigInteger.valueOf(1414141414L));
		messages6.setMessageId(1L);
		messages6.setStbAddressingList(stbAddressingList);
		messages6.setStatus(Boolean.FALSE);
		addValid(messages6);
	}

}
