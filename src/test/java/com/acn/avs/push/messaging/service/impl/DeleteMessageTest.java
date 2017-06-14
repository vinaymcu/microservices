package com.acn.avs.push.messaging.service.impl;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.acn.avs.push.messaging.entity.Messages;
import com.acn.avs.push.messaging.enums.ErrorCode;
import com.acn.avs.push.messaging.generator.MessagesEntityGenerator;
import com.acn.avs.push.messaging.helper.MessageValidator;
import com.acn.avs.push.messaging.model.GenericResponse;
import com.acn.avs.push.messaging.model.MessageId;
import com.acn.avs.push.messaging.model.MessageIds;
import com.acn.avs.push.messaging.model.ResultObject;
import com.acn.avs.push.messaging.repository.PushMessagingRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class DeleteMessageTest {
	@InjectMocks
	private UnicastServiceImpl unicastServiceImpl;
	
	@Mock
	private MessageValidator messageValidator;
	
	@Mock
	private PushMessagingRepository messagesRepository;
	
	MessagesEntityGenerator messagesEntityGenerator=new MessagesEntityGenerator();
	
	@Mock
	MessageSourceAccessor messageSourceAccessor;
	/**
	 * Setup the webApplicationContext context.
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	

	@Test // message id that does not exist in db
	public void testDeleteMessage()
	{
		MessageIds messageIds=new MessageIds();
		MessageId message=new MessageId();
		message.setMessageId(20);
		messageIds.getMessageIds().add(message);
		when(messageValidator.validateMessageIds(null, message.getMessageId())).thenReturn(getResultObject());
		GenericResponse response= unicastServiceImpl.deleteMessage(messageIds);
		assertEquals("ACN_9204", response.getResultObject().get(0).getResultCode());
	}
	
	@Test // message id that does not exist in db
	public void testDeleteMessage2()
	{
		Messages messageDB=messagesEntityGenerator.getValid().get(0);
		MessageIds messageIds=new MessageIds();
		MessageId message=new MessageId();
		message.setMessageId(20);
		messageIds.getMessageIds().add(message);
		when(messagesRepository.findOne(new Long(message.getMessageId()))).thenReturn(messageDB);
		GenericResponse response= unicastServiceImpl.deleteMessage(messageIds);
		assertEquals("ACN_200", response.getResultObject().get(0).getResultCode());
	}
	
	private ResultObject getResultObject()
	{
		ResultObject resultObject = new ResultObject();
		resultObject.setId(null);
		resultObject.setResultCode(ErrorCode.MESSAGE_NOT_EXIST.getCode());
		return resultObject;
	}
}
