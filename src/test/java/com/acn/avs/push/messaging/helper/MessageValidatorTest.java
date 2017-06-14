package com.acn.avs.push.messaging.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.acn.avs.push.messaging.config.ConfigurationProperties;
import com.acn.avs.push.messaging.entity.Messages;
import com.acn.avs.push.messaging.enums.ErrorCode;
import com.acn.avs.push.messaging.exception.PushMessagingException;
import com.acn.avs.push.messaging.generator.BodyModelGenerator;
import com.acn.avs.push.messaging.generator.HeaderModelGenerator;
import com.acn.avs.push.messaging.generator.MessagesEntityGenerator;
import com.acn.avs.push.messaging.generator.StbsModelGenerator;
import com.acn.avs.push.messaging.model.Header;
import com.acn.avs.push.messaging.model.Header.DistributionMode;
import com.acn.avs.push.messaging.model.ResultObject;
import com.acn.avs.push.messaging.model.UnicastMessage;
import com.acn.avs.push.messaging.model.UnicastMessageRequest;

@RunWith(SpringJUnit4ClassRunner.class)
public class MessageValidatorTest {

	@InjectMocks
	MessageValidator messageValidator;

	@Mock
	MessageSourceAccessor messageSourceAccessor;

	@Mock
	ConfigurationProperties configurationProperties;

	HeaderModelGenerator headerModelGenerator = new HeaderModelGenerator();
	BodyModelGenerator bodyModelGenerator = new BodyModelGenerator();
	StbsModelGenerator stbsModelGenerator = new StbsModelGenerator();
	MessagesEntityGenerator messagesEntityGenerator = new MessagesEntityGenerator();

	/**
	 * Setup the webApplicationContext context.
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test(expected = PushMessagingException.class) // Invalid Distribution mode
	public void testValidateMandatoryParams() {
		UnicastMessageRequest unicastMessageRequest = new UnicastMessageRequest();
		UnicastMessage unicastMessage = new UnicastMessage();
		Header header = headerModelGenerator.getValid().get(0);
		header.setDistributionMode(DistributionMode.MULTICAST);
		unicastMessage.setHeader(header);
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessageRequest.getUnicastMessage().add(unicastMessage);
		messageValidator.validateMandatoryParams(unicastMessageRequest);
	}

	@Test(expected = PushMessagingException.class) // Invalid Display DateTime
	public void testValidateMandatoryParams2() {
		UnicastMessageRequest unicastMessageRequest = new UnicastMessageRequest();
		UnicastMessage unicastMessage = new UnicastMessage();
		Header header = headerModelGenerator.getValid().get(0);
		header.setDisplayDateTime("10/12/2015");
		unicastMessage.setHeader(header);
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessageRequest.getUnicastMessage().add(unicastMessage);
		messageValidator.validateMandatoryParams(unicastMessageRequest);
	}

	@Test(expected = PushMessagingException.class) // Invalid Validity DateTime
	public void testValidateMandatoryParams3() {
		UnicastMessageRequest unicastMessageRequest = new UnicastMessageRequest();
		UnicastMessage unicastMessage = new UnicastMessage();
		Header header = headerModelGenerator.getValid().get(0);
		header.setValidityDateTime("10/12/2015");
		unicastMessage.setHeader(header);
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessageRequest.getUnicastMessage().add(unicastMessage);
		messageValidator.validateMandatoryParams(unicastMessageRequest);
	}

	@Test(expected = PushMessagingException.class) // Validity DateTime in past
	public void testValidateMandatoryParams4() {
		UnicastMessageRequest unicastMessageRequest = new UnicastMessageRequest();
		UnicastMessage unicastMessage = new UnicastMessage();
		Header header = headerModelGenerator.getValid().get(0);
		header.setValidityDateTime("10/12/2015 10:12");
		unicastMessage.setHeader(header);
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessageRequest.getUnicastMessage().add(unicastMessage);
		messageValidator.validateMandatoryParams(unicastMessageRequest);
	}

	@Test(expected = PushMessagingException.class) // validity DateTime in past
	public void testValidateMandatoryParams5() {
		UnicastMessageRequest unicastMessageRequest = new UnicastMessageRequest();
		UnicastMessage unicastMessage = new UnicastMessage();
		Header header = headerModelGenerator.getValid().get(0);
		header.setValidityDateTime("10/12/2015 10:20");
		unicastMessage.setHeader(header);
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessageRequest.getUnicastMessage().add(unicastMessage);
		messageValidator.validateMandatoryParams(unicastMessageRequest);
	}

	@Test(expected = PushMessagingException.class) // Display DateTime in past
	public void testValidateMandatoryParams6() {
		UnicastMessageRequest unicastMessageRequest = new UnicastMessageRequest();
		UnicastMessage unicastMessage = new UnicastMessage();
		Header header = headerModelGenerator.getValid().get(0);
		header.setDisplayDateTime("10/12/2015 10:20");
		unicastMessage.setHeader(header);
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessageRequest.getUnicastMessage().add(unicastMessage);
		messageValidator.validateMandatoryParams(unicastMessageRequest);
	}

	@Test(expected = PushMessagingException.class) // Display DateTime less than
													// validity date time
	public void testValidateMandatoryParams7() {
		UnicastMessageRequest unicastMessageRequest = new UnicastMessageRequest();
		UnicastMessage unicastMessage = new UnicastMessage();
		Header header = headerModelGenerator.getValid().get(0);
		header.setValidityDateTime("05/13/2017 08:20");
		header.setDisplayDateTime("05/13/2017 10:20");
		unicastMessage.setHeader(header);
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessageRequest.getUnicastMessage().add(unicastMessage);
		messageValidator.validateMandatoryParams(unicastMessageRequest);
	}

	@Test(expected = PushMessagingException.class) // UmParsable dateTime
	public void testValidateDateFormat() {
		UnicastMessageRequest unicastMessageRequest = new UnicastMessageRequest();
		UnicastMessage unicastMessage = new UnicastMessage();
		Header header = headerModelGenerator.getValid().get(0);
		header.setValidityDateTime("abcd");
		unicastMessage.setHeader(header);
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessageRequest.getUnicastMessage().add(unicastMessage);
		messageValidator.validateMandatoryParams(unicastMessageRequest);
	}

	@Test // test setDateTimeInEpoc
	public void testDateTimeInEpoc() {
		UnicastMessageRequest unicastMessageRequest = new UnicastMessageRequest();
		UnicastMessage unicastMessage = new UnicastMessage();
		Header header = headerModelGenerator.getValid().get(0);
		header.setValidityDateTime("abcd");
		unicastMessage.setHeader(header);
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessageRequest.getUnicastMessage().add(unicastMessage);
		messageValidator.setDateTimeInEpoc(null);
		messageValidator.setDateTimeInEpoc("10/12/2017 10:20");
		assertNull(messageValidator.setDateTimeInEpoc("invalid"));
	}

	@Test // test for unparsable dateTime
	public void testCheckDateInPast() {
		UnicastMessageRequest unicastMessageRequest = new UnicastMessageRequest();
		UnicastMessage unicastMessage = new UnicastMessage();
		Header header = headerModelGenerator.getValid().get(0);
		header.setValidityDateTime("abcd");
		unicastMessage.setHeader(header);
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessageRequest.getUnicastMessage().add(unicastMessage);
		assertEquals(false, messageValidator.checkDateInPast("invalid", "invalid"));
	}

	@Test
	public void testGenerateResultObject() {
		UnicastMessage unicastMessage = new UnicastMessage();
		unicastMessage.setHeader(headerModelGenerator.getValid().get(0));
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		ResultObject ro = messageValidator.generateResultObject(unicastMessage, ErrorCode.SUCCESS);
		assertEquals(ro.getResultCode(), "ACN_200");
	}

	// /validateAssociationAndActiveLength

	@Test
	public void testActiveLength() {
		when(configurationProperties.getMaxActiveMessage()).thenReturn(5000);
		UnicastMessage unicastMessage = new UnicastMessage();
		unicastMessage.setHeader(headerModelGenerator.getValid().get(0));
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		ResultObject ro = messageValidator.validateAssociationAndActiveLength(unicastMessage, 5000L);
		assertEquals(ro.getResultCode(), "ACN_9205");
	}

	@Test // without stb object
	public void testValidateAssociation() {
		when(configurationProperties.getMaxActiveMessage()).thenReturn(5000);
		UnicastMessage unicastMessage = new UnicastMessage();
		unicastMessage.setHeader(headerModelGenerator.getValid().get(0));
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		ResultObject ro = messageValidator.validateAssociationAndActiveLength(unicastMessage, 50L);
		assertEquals(ro.getResultCode(), "ACN_9206");
	}

	@Test // without mac address
	public void testValidateAssociation2() {
		when(configurationProperties.getMaxActiveMessage()).thenReturn(5000);
		UnicastMessage unicastMessage = new UnicastMessage();
		unicastMessage.setHeader(headerModelGenerator.getValid().get(0));
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessage.setSubscriberId("1212");
		unicastMessage.setStbs(stbsModelGenerator.getInvalid().get(0));
		ResultObject ro = messageValidator.validateAssociationAndActiveLength(unicastMessage, 50L);
		assertEquals(ro.getResultCode(), "ACN_9206");
	}

	@Test // when message object is null
	public void testValidateMessageIds() {
		ResultObject ro = messageValidator.validateMessageIds(null, 1);
		assertEquals(ro.getResultCode(), "ACN_9204");
	}

	@Test // when status is active
	public void testValidateMessageIds2() {
		Messages message = messagesEntityGenerator.getValid().get(0);
		ResultObject ro = messageValidator.validateMessageIds(message, 1);
		assertEquals(ro.getResultCode(), "ACN_9207");
	}


	@Test // when max active message reach
	public void testValidateActiveUnicastMessage2() {
		when(configurationProperties.getMaxActiveMessage()).thenReturn(5000);
		Messages message = messagesEntityGenerator.getValid().get(0);
		ResultObject ro = messageValidator.validateActiveMessage(message, 1, 5000L);
		assertEquals(ro.getResultCode(), "ACN_9205");
	}

	@Test // when status is active
	public void testValidateActiveUnicastMessage3() {
		when(configurationProperties.getMaxActiveMessage()).thenReturn(5000);
		Messages message = messagesEntityGenerator.getValid().get(0);
		ResultObject ro = messageValidator.validateActiveMessage(message, 1, 50L);
		assertEquals(ro.getResultCode(), "ACN_9209");
	}

	@Test // MESSAGE_PREVIOUSLY_ACTIVE
	public void testValidateActiveUnicastMessage4() {
		when(configurationProperties.getMaxActiveMessage()).thenReturn(5000);
		Messages message = messagesEntityGenerator.getValid().get(3);
		ResultObject ro = messageValidator.validateActiveMessage(message, 1, 50L);
		assertEquals(ro.getResultCode(), "ACN_9210");
	}
	
	@Test // active limit for multicast
	public void testValidateActiveUnicastMessage5() {
		when(configurationProperties.getMaxActiveMulticastMessages()).thenReturn(5000);
		Messages message = messagesEntityGenerator.getValid().get(3);
		message.setMultiCast(true);
		ResultObject ro = messageValidator.validateActiveMessage(message, 1, 5004L);
		assertEquals(ro.getResultCode(), "ACN_9205");
	}
	
	@Test // without stbaddressing
	public void testValidateActiveUnicastMessage6() {
		when(configurationProperties.getMaxActiveMessage()).thenReturn(5000);
		Messages message = messagesEntityGenerator.getInvalid().get(0);
		message.setStatus(false);
		ResultObject ro = messageValidator.validateActiveMessage(message, 1, 50L);
		assertEquals(ro.getResultCode(), "ACN_9206");
	}

	@Test // without mac
	public void testValidateActiveUnicastMessage7() {
		when(configurationProperties.getMaxActiveMessage()).thenReturn(5000);
		Messages message = messagesEntityGenerator.getInvalid().get(1);
		message.setStatus(false);
		ResultObject ro = messageValidator.validateActiveMessage(message, 1, 50L);
		assertEquals(ro.getResultCode(), "ACN_9206");
	}


	@Test // validateDeactivateMessage with null message
	public void testValidateDeactivateMessage() {
		ResultObject ro = messageValidator.validateDeactivateMessage(null, 1);
		assertEquals(ro.getResultCode(), "ACN_9204");
	}

	@Test // validateDeactivateMessage with inactive status
	public void testValidateDeactivateMessage2() {
		Messages message = messagesEntityGenerator.getValid().get(3);
		ResultObject ro = messageValidator.validateDeactivateMessage(message, 1);
		assertEquals(ro.getResultCode(), "ACN_9208");
	}

	@Test(expected=PushMessagingException.class) // validateMaximumMessages
	public void testValidateMaximumMessages() {
		when(configurationProperties.getMaxMessagePerRequest()).thenReturn(1);
		List<String> list=Arrays.asList("test","test2");
		messageValidator.validateMaximumMessages(list);
	}
	
	@Test(expected=PushMessagingException.class) // validateDistributionMode
	public void testValidateDistributionMode() {
		messageValidator.validateDistributionMode("invalid");
	}
	
	
	
	@Test
	public void testConvertEpocToDateTime()
	{
		messageValidator.convertEpocToDateTime("1414141414");
	}
}
