package com.acn.avs.push.messaging.service.impl;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.acn.avs.push.messaging.client.UnicastNotifierClient;
import com.acn.avs.push.messaging.config.ConfigurationProperties;
import com.acn.avs.push.messaging.entity.Messages;
import com.acn.avs.push.messaging.enums.ErrorCode;
import com.acn.avs.push.messaging.generator.MessagesEntityGenerator;
import com.acn.avs.push.messaging.helper.MessageValidator;
import com.acn.avs.push.messaging.model.EventUpdate;
import com.acn.avs.push.messaging.model.EventUpdateRequest;
import com.acn.avs.push.messaging.model.GenericResponse;
import com.acn.avs.push.messaging.model.MessageId;
import com.acn.avs.push.messaging.model.MessageIds;
import com.acn.avs.push.messaging.model.ResultObject;
import com.acn.avs.push.messaging.repository.PushMessagingRepository;
import com.acn.avs.push.messaging.util.Constants;

import feign.FeignException;
import feign.codec.DecodeException;

@RunWith(SpringJUnit4ClassRunner.class)
public class ActivateMessageTest {
	@InjectMocks
	private UnicastServiceImpl unicastServiceImpl;
	
	@Mock
	private MessageValidator messageValidator;
	
	@Mock
	private PushMessagingRepository messagesRepository;
	
	MessagesEntityGenerator messagesEntityGenerator=new MessagesEntityGenerator();
	
	@Mock
	private ConfigurationProperties configurationProperties;
	
	@Mock
	MessageSourceAccessor messageSourceAccessor;
	
	@Mock
	UnicastNotifierClient unicastNotifierClient;
	
	
	/**
	 * Setup the webApplicationContext context.
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	

	@Test // message id that does not exist in db
	public void testActivateMessage()
	{
		MessageIds messageIds=new MessageIds();
		MessageId message=new MessageId();
		message.setMessageId(20);
		messageIds.getMessageIds().add(message);
		when(messagesRepository.countByStatusAndMultiCast(Boolean.TRUE,Boolean.FALSE)).thenReturn(50L);
		when(messageValidator.validateActiveMessage(null, message.getMessageId(),messagesRepository.countByStatusAndMultiCast(Boolean.TRUE,Boolean.FALSE))).thenReturn(getResultObject());
		GenericResponse response= unicastServiceImpl.activateMessage(messageIds);
		assertEquals("ACN_9204", response.getResultObject().get(0).getResultCode());
	}
	
	@Test // message id that does not exist in db
	public void testActivateMessage2()
	{
		Messages messageDB=messagesEntityGenerator.getValid().get(0);
		MessageIds messageIds=new MessageIds();
		MessageId message=new MessageId();
		message.setMessageId(20);
		messageIds.getMessageIds().add(message);
		when(messagesRepository.findOne(new Long(message.getMessageId()))).thenReturn(messageDB);
		when(unicastNotifierClient.notifyUnicastNotifier(sendNotificationToUnicast(messageDB))).thenReturn(getResponse());
		GenericResponse response= unicastServiceImpl.activateMessage(messageIds);
		assertEquals("ACN_200", response.getResultObject().get(0).getResultCode());
	}
	
	
	@Test // notifyUnicastNotifier with invalid response
	public void testActivateMessage3()
	{
		Messages messageDB=messagesEntityGenerator.getValid().get(0);
		MessageIds messageIds=new MessageIds();
		MessageId message=new MessageId();
		message.setMessageId(20);
		messageIds.getMessageIds().add(message);
		when(messagesRepository.findOne(new Long(message.getMessageId()))).thenReturn(messageDB);
		when(unicastNotifierClient.notifyUnicastNotifier(sendNotificationToUnicast(messageDB))).thenReturn(getInvResponse());
		GenericResponse response= unicastServiceImpl.activateMessage(messageIds);
		assertEquals("ACN_200", response.getResultObject().get(0).getResultCode());
	}
	
	@Test // test fiegn exception
	public void testActivateMessage4()
	{
		FeignException exception=new DecodeException("ABS");
		Messages messageDB=messagesEntityGenerator.getValid().get(0);
		MessageIds messageIds=new MessageIds();
		MessageId message=new MessageId();
		message.setMessageId(20);
		messageIds.getMessageIds().add(message);
		when(messagesRepository.findOne(new Long(message.getMessageId()))).thenReturn(messageDB);
		when(unicastNotifierClient.notifyUnicastNotifier(sendNotificationToUnicast(messageDB))).thenThrow(exception);
		GenericResponse response= unicastServiceImpl.activateMessage(messageIds);
		assertEquals("ACN_200", response.getResultObject().get(0).getResultCode());
	}
	
	@Test // test fiegn exception with retry
	public void testActivateMessage5()
	{
		FeignException exception=new DecodeException("ABS");
		Messages messageDB=messagesEntityGenerator.getValid().get(0);
		MessageIds messageIds=new MessageIds();
		MessageId message=new MessageId();
		message.setMessageId(20);
		messageIds.getMessageIds().add(message);
		when(configurationProperties.getMsgRetry()).thenReturn(3);
		when(messagesRepository.findOne(new Long(message.getMessageId()))).thenReturn(messageDB);
		when(unicastNotifierClient.notifyUnicastNotifier(sendNotificationToUnicast(messageDB))).thenThrow(exception);
		GenericResponse response= unicastServiceImpl.activateMessage(messageIds);
		assertEquals("ACN_200", response.getResultObject().get(0).getResultCode());
	}
	
	public EventUpdateRequest sendNotificationToUnicast(Messages message) {
		EventUpdateRequest eventUpdateRequest = new EventUpdateRequest();
		EventUpdate eventUpdate = new EventUpdate();
		List<String> macList = new ArrayList<String>();
		eventUpdate.setMACAddress(macList);
		message.getStbAddressingList().forEach(stb -> macList.add(stb.getMacAddress()));
		eventUpdate.setSubscriberId(message.getSubscriberId() + "");
		eventUpdate.setTriggerType(Constants.TRIGGER_TYPE);
		eventUpdate.setTimestamp((new Date().getTime() / 1000l) + "");
		eventUpdate.setTriggerInfo(message.getMessageId() + "");
		eventUpdateRequest.setEventUpdate(eventUpdate);
		System.out.println(eventUpdateRequest);
		return eventUpdateRequest;
	}
	
	private ResultObject getResultObject()
	{
		ResultObject resultObject = new ResultObject();
		resultObject.setId(null);
		resultObject.setResultCode(ErrorCode.MESSAGE_NOT_EXIST.getCode());
		return resultObject;
	}
	
	public ResponseEntity<GenericResponse> getInvResponse()
	{
		GenericResponse genericResponse=new GenericResponse();
		genericResponse.setResultCode(ErrorCode.GENERIC_ERROR.getCode());
		ResponseEntity<GenericResponse> response=ResponseEntity.ok(genericResponse);
		return response;
	}
	
	public ResponseEntity<GenericResponse> getResponse()
	{
		GenericResponse genericResponse=new GenericResponse();
		genericResponse.setResultCode(ErrorCode.SUCCESS.getCode());
		ResponseEntity<GenericResponse> response=ResponseEntity.ok(genericResponse);
		return response;
	}
}
