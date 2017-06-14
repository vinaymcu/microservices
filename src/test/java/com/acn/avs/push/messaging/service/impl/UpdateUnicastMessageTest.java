package com.acn.avs.push.messaging.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.acn.avs.push.messaging.client.UnicastNotifierClient;
import com.acn.avs.push.messaging.config.ConfigurationProperties;
import com.acn.avs.push.messaging.entity.Messages;
import com.acn.avs.push.messaging.enums.ErrorCode;
import com.acn.avs.push.messaging.exception.PushMessagingException;
import com.acn.avs.push.messaging.generator.BodyModelGenerator;
import com.acn.avs.push.messaging.generator.HeaderModelGenerator;
import com.acn.avs.push.messaging.generator.MessagesEntityGenerator;
import com.acn.avs.push.messaging.generator.StbsModelGenerator;
import com.acn.avs.push.messaging.helper.MessageValidator;
import com.acn.avs.push.messaging.model.EventUpdate;
import com.acn.avs.push.messaging.model.EventUpdateRequest;
import com.acn.avs.push.messaging.model.GenericResponse;
import com.acn.avs.push.messaging.model.ResultObject;
import com.acn.avs.push.messaging.model.UnicastMessage;
import com.acn.avs.push.messaging.model.UnicastMessageRequest;
import com.acn.avs.push.messaging.model.UnicastMessageResponse;
import com.acn.avs.push.messaging.repository.CustomPushMessagingRepository;
import com.acn.avs.push.messaging.repository.PushMessagingRepository;
import com.acn.avs.push.messaging.util.Constants;



@RunWith(SpringJUnit4ClassRunner.class)
public class UpdateUnicastMessageTest{

	@InjectMocks
	private UnicastServiceImpl unicastServiceImpl;
	
	@Mock
	MessageValidator messageValidator;
	
	@Mock
	private CustomPushMessagingRepository customRepository;
	
	@Mock
	private PushMessagingRepository messagesRepository;
	
	@Mock
	private ConfigurationProperties configurationProperties;
	
	@Mock
	UnicastNotifierClient unicastNotifierClient;
	
	HeaderModelGenerator headerModelGenerator=new HeaderModelGenerator();
	BodyModelGenerator bodyModelGenerator=new BodyModelGenerator();
	MessagesEntityGenerator messagesEntityGenerator=new MessagesEntityGenerator();
	StbsModelGenerator stbsModelGenerator=new StbsModelGenerator();
	/**
	 * Setup the webApplicationContext context.
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test // message id that does not exist in db
	public void testUpdateUnicastMessage()
	{
		UnicastMessageRequest unicastMessageRequest=new UnicastMessageRequest();
		UnicastMessage unicastMessage=new  UnicastMessage();
		unicastMessage.setHeader(headerModelGenerator.getValid().get(0));
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessageRequest.getUnicastMessage().add(unicastMessage);
		when(messageValidator.generateResultObject(unicastMessage, ErrorCode.MESSAGE_NOT_EXIST)).thenReturn(generateResultObject(unicastMessage, ErrorCode.MESSAGE_NOT_EXIST));
		UnicastMessageResponse response= unicastServiceImpl.updateUnicastMessage(unicastMessageRequest);
		Integer messageId= response.getResultObject().get(0).getUnicastMessage().getHeader().getMessageId();
		// due to validator mock, it is returning nulll
		assertEquals(new Integer(1),messageId);
	}
	
	@Test //with active status
	public void testUpdateUnicastMessage2()
	{
		Messages message=messagesEntityGenerator.getValid().get(0);
		Messages messageWithoutId=messagesEntityGenerator.getValid().get(2);
		UnicastMessageRequest unicastMessageRequest=new UnicastMessageRequest();
		UnicastMessage unicastMessage=new  UnicastMessage();
		unicastMessage.setHeader(headerModelGenerator.getValid().get(1));
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessage.setSubscriberId("1");
		unicastMessage.setStbs(stbsModelGenerator.getValid().get(0));
		unicastMessageRequest.getUnicastMessage().add(unicastMessage);
		when(messagesRepository.findOne(new Long(unicastMessage.getHeader().getMessageId()))).thenReturn(message);
		when(messageValidator.generateResultObject(unicastMessage, ErrorCode.SUCCESS)).thenReturn(generateResultObject(unicastMessage, ErrorCode.SUCCESS));
		when(messagesRepository.save(messageWithoutId)).thenReturn(message);
		when(configurationProperties.getMsgRetry()).thenReturn(3);
		when(unicastNotifierClient.notifyUnicastNotifier(sendNotificationToUnicast(message))).thenReturn(getResponse());
		UnicastMessageResponse response= unicastServiceImpl.updateUnicastMessage(unicastMessageRequest);
		String errorCode= (String)response.getResultObject().get(0).getResultCode();
		assertEquals("ACN_200",errorCode);
	}
	
	@Test //with active status and association missing
	public void testUpdateUnicastMessage3()
	{
		Messages message=messagesEntityGenerator.getValid().get(0);
		UnicastMessageRequest unicastMessageRequest=new UnicastMessageRequest();
		UnicastMessage unicastMessage=new  UnicastMessage();
		unicastMessage.setHeader(headerModelGenerator.getValid().get(1));
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessage.setSubscriberId("1");
		unicastMessage.setStbs(stbsModelGenerator.getValid().get(0));
		unicastMessageRequest.getUnicastMessage().add(unicastMessage);
		when(messagesRepository.findOne(new Long(unicastMessage.getHeader().getMessageId()))).thenReturn(message);
		when(messageValidator.generateResultObject(unicastMessage, ErrorCode.SUCCESS)).thenReturn(generateResultObject(unicastMessage, ErrorCode.SUCCESS));
		when(messagesRepository.countByStatusAndMultiCast(Boolean.TRUE,Boolean.FALSE)).thenReturn(50L);
		when(messageValidator.validateAssociationAndActiveLength(unicastMessage,50L)).thenReturn(generateResultObject(unicastMessage,ErrorCode.MISSING_ASSOCIATION));
		when(messagesRepository.save(message)).thenReturn(message);
		when(configurationProperties.getMsgRetry()).thenReturn(3);
		when(unicastNotifierClient.notifyUnicastNotifier(sendNotificationToUnicast(message))).thenReturn(getResponse());
		UnicastMessageResponse response= unicastServiceImpl.updateUnicastMessage(unicastMessageRequest);
		String errorCode= (String)response.getResultObject().get(0).getResultCode();
		assertEquals("ACN_9206",errorCode);
	}
	

	@Test //test generic error
	public void testUpdateUnicastMessage4()
	{
		Messages message=messagesEntityGenerator.getInvalid().get(0);
		Messages messageWithoutId=messagesEntityGenerator.getValid().get(2);
		UnicastMessageRequest unicastMessageRequest=new UnicastMessageRequest();
		UnicastMessage unicastMessage=new  UnicastMessage();
		unicastMessage.setHeader(headerModelGenerator.getValid().get(1));
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessage.setSubscriberId("1");
		unicastMessage.setStbs(stbsModelGenerator.getValid().get(0));
		unicastMessageRequest.getUnicastMessage().add(unicastMessage);
		when(messagesRepository.findOne(new Long(unicastMessage.getHeader().getMessageId()))).thenReturn(message);
		when(messageValidator.generateResultObject(unicastMessage, ErrorCode.GENERIC_ERROR)).thenReturn(generateResultObject(unicastMessage, ErrorCode.GENERIC_ERROR));
		when(messagesRepository.save(messageWithoutId)).thenThrow(new DataAccessResourceFailureException("Unable to access data"));
		UnicastMessageResponse response= unicastServiceImpl.updateUnicastMessage(unicastMessageRequest);
		String errorCode= (String)response.getResultObject().get(0).getResultCode();
		assertEquals("ACN_300",errorCode);
	}
	
		
	@Test //When messageId is already exist in db with active status but request with inactive status
	public void testUpdateUnicastMessage6()
	{
		Messages message=messagesEntityGenerator.getValid().get(0);
		Messages messageWithoutId=messagesEntityGenerator.getValid().get(2);
		UnicastMessageRequest unicastMessageRequest=new UnicastMessageRequest();
		UnicastMessage unicastMessage=new  UnicastMessage();
		unicastMessage.setHeader(headerModelGenerator.getValid().get(0));
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessageRequest.getUnicastMessage().add(unicastMessage);
		when(messagesRepository.findOne(new Long(unicastMessage.getHeader().getMessageId()))).thenReturn(message);
		when(unicastNotifierClient.notifyUnicastNotifier(sendNotificationToUnicast(message))).thenReturn(getResponse());
		when(messageValidator.generateResultObject(unicastMessage, ErrorCode.SUCCESS)).thenReturn(generateResultObject(unicastMessage, ErrorCode.SUCCESS));
		when(messagesRepository.save(messageWithoutId)).thenReturn(message);
		UnicastMessageResponse response= unicastServiceImpl.updateUnicastMessage(unicastMessageRequest);
		String errorCode= (String)response.getResultObject().get(0).getResultCode();
		assertEquals("ACN_200",errorCode);
	}
	
	@Test //When messageId is already exist in db with inactive status
	public void testUpdateUnicastMessage7()
	{
		Messages message=messagesEntityGenerator.getValid().get(4);
		UnicastMessageRequest unicastMessageRequest=new UnicastMessageRequest();
		UnicastMessage unicastMessage=new  UnicastMessage();
		unicastMessage.setHeader(headerModelGenerator.getValid().get(1));
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessage.setSubscriberId("1");
		unicastMessage.setStbs(stbsModelGenerator.getValid().get(0));
		unicastMessageRequest.getUnicastMessage().add(unicastMessage);
		when(messagesRepository.findOne(new Long(unicastMessage.getHeader().getMessageId()))).thenReturn(message);
		when(unicastNotifierClient.notifyUnicastNotifier(sendNotificationToUnicast(message))).thenReturn(getResponse());
		when(messageValidator.generateResultObject(unicastMessage, ErrorCode.SUCCESS)).thenReturn(generateResultObject(unicastMessage, ErrorCode.SUCCESS));
		when(messagesRepository.save(message)).thenReturn(message);
		when(messageValidator.setDateTimeInEpoc(null)).thenReturn(new BigInteger("1414141414"));
		UnicastMessageResponse response= unicastServiceImpl.updateUnicastMessage(unicastMessageRequest);
		String errorCode= (String)response.getResultObject().get(0).getResultCode();
		assertEquals("ACN_200",errorCode);
	}
	

	/**
	 *  testing exception 
	 */
	@Test 
	public void testupdateUnicastMessage8()
	{
		Messages message=messagesEntityGenerator.getValid().get(1);
		UnicastMessageRequest unicastMessageRequest=new UnicastMessageRequest();
		UnicastMessage unicastMessage=new  UnicastMessage();
		unicastMessage.setHeader(headerModelGenerator.getValid().get(1));
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessage.setSubscriberId("1");
		unicastMessage.setStbs(stbsModelGenerator.getValid().get(0));
		unicastMessageRequest.getUnicastMessage().add(unicastMessage);
		when(messageValidator.generateResultObject(unicastMessage, ErrorCode.GENERIC_ERROR)).thenReturn(generateResultObject(unicastMessage, ErrorCode.GENERIC_ERROR));
		when(messagesRepository.save(message)).thenThrow(new DataAccessResourceFailureException("Unable to access data"));
		when(messagesRepository.findOne(new Long(unicastMessage.getHeader().getMessageId()))).thenReturn(message);
		UnicastMessageResponse response= unicastServiceImpl.updateUnicastMessage(unicastMessageRequest);
		String errorCode= (String)response.getResultObject().get(0).getResultCode();
		assertEquals("ACN_300",errorCode);
	}
	
	/**
	 * request without id
	 */
	@Test(expected=PushMessagingException.class) 
	public void testupdateUnicastMessage9()
	{
		UnicastMessageRequest unicastMessageRequest=new UnicastMessageRequest();
		UnicastMessage unicastMessage=new  UnicastMessage();
		unicastMessage.setHeader(headerModelGenerator.getInvalid().get(0));
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessage.setSubscriberId("1");
		unicastMessage.setStbs(stbsModelGenerator.getValid().get(0));
		unicastMessageRequest.getUnicastMessage().add(unicastMessage);
		unicastServiceImpl.updateUnicastMessage(unicastMessageRequest);
	}
	
	public ResultObject generateResultObject(UnicastMessage unicastmessage, ErrorCode errorCode) {
		ResultObject resultObject = new ResultObject();
		resultObject.setId(unicastmessage.getHeader().getMessageId() + "");
		resultObject.setResultCode(errorCode.getCode());
		resultObject.setUnicastMessage(unicastmessage);
		return resultObject;
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
	
	//without stbaddressing
	public EventUpdateRequest sendInvalidNotificationToUnicast(Messages message) {
		EventUpdateRequest eventUpdateRequest = new EventUpdateRequest();
		EventUpdate eventUpdate = new EventUpdate();
		List<String> macList = new ArrayList<String>();
		eventUpdate.setMACAddress(macList);
		eventUpdate.setSubscriberId(message.getSubscriberId() + "");
		eventUpdate.setTriggerType(Constants.TRIGGER_TYPE);
		eventUpdate.setTimestamp((new Date().getTime() / 1000l) + "");
		eventUpdate.setTriggerInfo(message.getMessageId() + "");
		eventUpdateRequest.setEventUpdate(eventUpdate);
		return eventUpdateRequest;
	}
	
	public ResponseEntity<GenericResponse> getResponse()
	{
		GenericResponse genericResponse=new GenericResponse();
		genericResponse.setResultCode(ErrorCode.SUCCESS.getCode());
		ResponseEntity<GenericResponse> response=ResponseEntity.ok(genericResponse);
		return response;
	}
	
}
