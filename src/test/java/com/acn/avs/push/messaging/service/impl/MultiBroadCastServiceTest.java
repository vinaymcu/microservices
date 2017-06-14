package com.acn.avs.push.messaging.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
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
import com.acn.avs.push.messaging.helper.MessageValidator;
import com.acn.avs.push.messaging.model.Body;
import com.acn.avs.push.messaging.model.Filter;
import com.acn.avs.push.messaging.model.Header;
import com.acn.avs.push.messaging.model.Header.DistributionMode;
import com.acn.avs.push.messaging.model.Header.MessageType;
import com.acn.avs.push.messaging.model.MulticastMessage;
import com.acn.avs.push.messaging.model.MulticastMessageRequest;
import com.acn.avs.push.messaging.model.MulticastMessageResponse;
import com.acn.avs.push.messaging.repository.CustomPushMessagingRepository;
import com.acn.avs.push.messaging.repository.PushMessagingRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class MultiBroadCastServiceTest {

	@InjectMocks
	private MultiBroadCastServiceImpl multiBroadCastServiceImpl;

	@Mock
	MessageValidator messageValidator;

	@Mock
	PushMessagingRepository messagesRepository;

	@Mock
	MessageSourceAccessor messageSourceAccessor;

	@Mock
	ConfigurationProperties configProperties;

	@Mock
	CustomPushMessagingRepository customRepository;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreateMultiBroadCastMessage_MessageNotExist() {
		MulticastMessageRequest mmRequest = new MulticastMessageRequest();

		MulticastMessage mm = new MulticastMessage();
		Integer id = 1;
		mm.setHeader(prepareHeader(id, DistributionMode.UNICAST));
		mm.setBody(prepareBody());
		mm.getFilter().add(new Filter());
		mmRequest.getMulticastMessages().add(mm);

		Messages message = new Messages();
		message.setMessageId(id.longValue());

		when(messagesRepository.save(message)).thenReturn(message);
		MulticastMessageResponse response = multiBroadCastServiceImpl.createMultiBroadCastMessage(mmRequest);
		assertEquals(String.valueOf(id), response.getResultObject().get(0).getId());
		assertEquals("ACN_200", response.getResultObject().get(0).getResultCode());
	}

	@Test
	public void testCreateMultiBroadCastMessage() {
		MulticastMessageRequest mmRequest = new MulticastMessageRequest();

		MulticastMessage mm = new MulticastMessage();
		mm.setHeader(prepareHeader(null, DistributionMode.UNICAST));
		mm.setBody(prepareBody());
		mm.getFilter().add(new Filter());
		mmRequest.getMulticastMessages().add(mm);

		Messages messages = new Messages();
		messages.setMessageId(1L);
		messages.setStatus(Boolean.TRUE);

		when(messagesRepository.save(messages)).thenReturn(messages);
		MulticastMessageResponse response = multiBroadCastServiceImpl.createMultiBroadCastMessage(mmRequest);
		assertNull(response.getResultObject().get(0).getId());
		assertEquals("ACN_300", response.getResultObject().get(0).getResultCode());
	}

	@Test
	public void testCreateMultiBroadCastMessage_Filters() {
		MulticastMessageRequest mmRequest = new MulticastMessageRequest();
		Integer id = prepareMulticastMessageWithFilter(mmRequest);

		when(configProperties.getMaxNoFilters()).thenReturn(0);

		Messages messages = new Messages();
		messages.setMessageId(1L);
		messages.setStatus(Boolean.TRUE);
		when(messagesRepository.findOne(1L)).thenReturn(null);

		when(messagesRepository.save(messages)).thenReturn(messages);

		MulticastMessageResponse response = multiBroadCastServiceImpl.createMultiBroadCastMessage(mmRequest);
		assertEquals(String.valueOf(id), response.getResultObject().get(0).getId());
		assertEquals("ACN_9201", response.getResultObject().get(0).getResultCode().toString());

	}

	private Integer prepareMulticastMessageWithFilter(MulticastMessageRequest mmRequest) {
		Integer id = 1;
		MulticastMessage mm = new MulticastMessage();
		mm.setFilter(prepareFilter());
		mm.setHeader(prepareHeader(1, DistributionMode.MULTICAST));
		mm.setBody(prepareBody());
		mmRequest.getMulticastMessages().add(mm);
		return id;
	}

	@Test
	public void testCreateMultiBroadCastMessage_FiltersMissing() {
		MulticastMessageRequest mmRequest = new MulticastMessageRequest();
		Integer id = 1;
		MulticastMessage mm = new MulticastMessage();
		List<Filter> fList = new ArrayList<>();
		mm.setFilter(fList);
		mm.setHeader(prepareHeader(1, DistributionMode.MULTICAST));
		mm.setBody(prepareBody());
		mmRequest.getMulticastMessages().add(mm);

		when(configProperties.getMaxNoFilters()).thenReturn(5);

		Messages messages = new Messages();
		messages.setMessageId(1L);
		messages.setStatus(Boolean.TRUE);
		when(messagesRepository.findOne(1L)).thenReturn(null);

		when(messagesRepository.save(messages)).thenReturn(messages);

		MulticastMessageResponse response = multiBroadCastServiceImpl.createMultiBroadCastMessage(mmRequest);
		assertEquals(String.valueOf(id), response.getResultObject().get(0).getId());
		assertEquals("ACN_9202", response.getResultObject().get(0).getResultCode().toString());

	}

	@Test
	public void testCreateMultiBroadCastMessage_MaxActiveMessage() {
		MulticastMessageRequest mmRequest = new MulticastMessageRequest();
		Integer id = prepareMulticastMessageWithEmptyFilter(mmRequest);

		when(configProperties.getMaxNoFilters()).thenReturn(0);

		when(messagesRepository.countByStatusAndMultiCast(true, true)).thenReturn(2L);
		when(configProperties.getMaxActiveMulticastMessages()).thenReturn(1);

		Messages messages = new Messages();
		messages.setMessageId(1L);
		messages.setStatus(Boolean.TRUE);
		when(messagesRepository.findOne(1L)).thenReturn(null);

		when(messagesRepository.save(messages)).thenReturn(messages);

		MulticastMessageResponse response = multiBroadCastServiceImpl.createMultiBroadCastMessage(mmRequest);
		assertEquals(String.valueOf(id), response.getResultObject().get(0).getId());
		assertEquals("ACN_9205", response.getResultObject().get(0).getResultCode().toString());
	}

	private Integer prepareMulticastMessageWithEmptyFilter(MulticastMessageRequest mmRequest) {
		Integer id = 1;
		MulticastMessage mm = new MulticastMessage();
		mm.getFilter().add(new Filter());
		mm.setHeader(prepareHeader(1, DistributionMode.MULTICAST));
		mm.setBody(prepareBody());
		mmRequest.getMulticastMessages().add(mm);
		return id;
	}

	@Test
	public void testCreateMultiBroadCastMessage_ActiveMessage() {
		MulticastMessageRequest mmRequest = new MulticastMessageRequest();
		Integer id = prepareMulticastMessageWithEmptyFilter(mmRequest);

		Messages messages = new Messages();
		messages.setMessageId(1L);
		messages.setStatus(Boolean.TRUE);
		messages.setActivationDatetime(BigInteger.valueOf(11111));
		when(messagesRepository.findOne(1L)).thenReturn(messages);

		when(messagesRepository.save(messages)).thenReturn(messages);

		MulticastMessageResponse response = multiBroadCastServiceImpl.createMultiBroadCastMessage(mmRequest);
		assertEquals(String.valueOf(id), response.getResultObject().get(0).getId());
		assertEquals("ACN_9201", response.getResultObject().get(0).getResultCode().toString());
	}

	@Test
	public void testCreateMultiBroadCastMessage_Broadcast() {
		MulticastMessageRequest mmRequest = new MulticastMessageRequest();

		MulticastMessage mm = new MulticastMessage();
		Integer id = 1;
		Header header = prepareHeader(id, DistributionMode.BROADCAST);
		header.setStatus("IACTIVE");
		mm.setHeader(header);
		mm.setBody(prepareBody());
		mm.getFilter().add(new Filter());
		mmRequest.getMulticastMessages().add(mm);

		Messages message = new Messages();
		message.setMessageId(id.longValue());

		when(messagesRepository.save(message)).thenReturn(message);
		MulticastMessageResponse response = multiBroadCastServiceImpl.createMultiBroadCastMessage(mmRequest);
		assertEquals(String.valueOf(id), response.getResultObject().get(0).getId());
		assertEquals("ACN_200", response.getResultObject().get(0).getResultCode());
	}

	@Test
	public void testUpdateMultiBroadCastMessage_Filters() {
		MulticastMessageRequest mmRequest = new MulticastMessageRequest();
		Integer id = prepareMulticastMessageWithFilter(mmRequest);

		when(configProperties.getMaxNoFilters()).thenReturn(0);

		Messages messages = new Messages();
		messages.setMessageId(1L);
		messages.setStatus(Boolean.FALSE);
		messages.setActivationDatetime(null);

		when(messagesRepository.findOne(1L)).thenReturn(messages);

		MulticastMessageResponse response = multiBroadCastServiceImpl.updateMultiBroadCastMessage(mmRequest);
		assertEquals(String.valueOf(id), response.getResultObject().get(0).getId());
		assertEquals("ACN_9201", response.getResultObject().get(0).getResultCode().toString());

	}

	@Test
	public void testUpdateMultiBroadCastMessage_FiltersMissing() {
		MulticastMessageRequest mmRequest = new MulticastMessageRequest();
		Integer id = 1;
		MulticastMessage mm = new MulticastMessage();
		List<Filter> fList = new ArrayList<>();
		mm.setFilter(fList);
		mm.setHeader(prepareHeader(1, DistributionMode.MULTICAST));
		mm.setBody(prepareBody());
		mmRequest.getMulticastMessages().add(mm);

		Messages messages = new Messages();
		messages.setMessageId(1L);
		messages.setStatus(Boolean.FALSE);
		messages.setActivationDatetime(null);
		when(messagesRepository.findOne(1L)).thenReturn(messages);

		MulticastMessageResponse response = multiBroadCastServiceImpl.updateMultiBroadCastMessage(mmRequest);
		assertEquals(String.valueOf(id), response.getResultObject().get(0).getId());
		assertEquals("ACN_9202", response.getResultObject().get(0).getResultCode().toString());

	}

	@Test
	public void testUpdateMultiBroadCastMessage_MaxActiveMessage() {
		MulticastMessageRequest mmRequest = new MulticastMessageRequest();
		Integer id = prepareMulticastMessageWithEmptyFilter(mmRequest);

		when(configProperties.getMaxNoFilters()).thenReturn(0);

		when(messagesRepository.countByStatusAndMultiCast(true, true)).thenReturn(2L);
		when(configProperties.getMaxActiveMulticastMessages()).thenReturn(1);

		Messages messages = new Messages();
		messages.setMessageId(1L);
		messages.setStatus(Boolean.TRUE);
		when(messagesRepository.findOne(1L)).thenReturn(messages);

		MulticastMessageResponse response = multiBroadCastServiceImpl.updateMultiBroadCastMessage(mmRequest);
		assertEquals(String.valueOf(id), response.getResultObject().get(0).getId());
		assertEquals("ACN_9205", response.getResultObject().get(0).getResultCode().toString());
	}

	@Test
	public void testUpdateMultiBroadCastMessage_MessageNotExist() {
		MulticastMessageRequest mmRequest = new MulticastMessageRequest();
		Integer id = 1;
		MulticastMessage mm = new MulticastMessage();
		mm.setFilter(prepareFilter());
		Header header = prepareHeader(id, DistributionMode.BROADCAST);
		header.setStatus("IACTIVE");
		mm.setHeader(header);
		mm.setBody(prepareBody());
		mmRequest.getMulticastMessages().add(mm);

		when(configProperties.getMaxNoFilters()).thenReturn(2);

		Messages messages = new Messages();
		messages.setMessageId(1L);
		messages.setStatus(Boolean.TRUE);
		when(messagesRepository.findOne(1L)).thenReturn(null);

		MulticastMessageResponse response = multiBroadCastServiceImpl.updateMultiBroadCastMessage(mmRequest);
		assertEquals("ACN_9204", response.getResultObject().get(0).getResultCode().toString());
	}

	@Test
	public void testUpdateMultiBroadCastMessage_MessageNotExistRequest() {
		MulticastMessageRequest mmRequest = new MulticastMessageRequest();
		Integer id = 1;
		MulticastMessage mm = new MulticastMessage();
		mm.setFilter(prepareFilter());

		Header header = prepareHeader(id, DistributionMode.BROADCAST);
		header.setStatus("IACTIVE");
		header.setMessageId(null);
		mm.setHeader(header);
		mm.setBody(prepareBody());
		mmRequest.getMulticastMessages().add(mm);

		when(configProperties.getMaxNoFilters()).thenReturn(2);

		Messages messages = new Messages();
		messages.setMessageId(1L);
		messages.setStatus(Boolean.TRUE);
		when(messagesRepository.findOne(1L)).thenReturn(null);

		MulticastMessageResponse response = multiBroadCastServiceImpl.updateMultiBroadCastMessage(mmRequest);
		assertEquals("ACN_3020", response.getResultObject().get(0).getResultCode().toString());
	}

	@Test
	public void testUpdateMultiBroadCastMessage_GenericException() {
		MulticastMessageRequest mmRequest = new MulticastMessageRequest();
		Integer id = 1;
		MulticastMessage mm = new MulticastMessage();

		mm.getFilter().add(new Filter());

		Header header = prepareHeader(id, DistributionMode.BROADCAST);
		header.setStatus("IACTIVE");
		mm.setHeader(header);
		mm.setBody(prepareBody());
		mmRequest.getMulticastMessages().add(mm);

		Messages messages = new Messages();
		messages.setMessageId(1L);
		messages.setStatus(Boolean.TRUE);
		messages.setActivationDatetime(BigInteger.valueOf(11111));
		
		when(messagesRepository.findOne(1L)).thenReturn(messages);
		when(messagesRepository.save(messages)).thenReturn(messages);

		MulticastMessageResponse response = multiBroadCastServiceImpl.updateMultiBroadCastMessage(mmRequest);
		assertEquals("ACN_300", response.getResultObject().get(0).getResultCode().toString());
	}

	@Test
	public void testUpdateMultiBroadCastMessage_IactiveMessage() {
		MulticastMessageRequest mmRequest = new MulticastMessageRequest();
		Integer id = 1;
		MulticastMessage mm = new MulticastMessage();

		mm.getFilter().add(new Filter());

		Header header = prepareHeader(id, DistributionMode.BROADCAST);
		header.setStatus("IACTIVE");
		mm.setHeader(header);
		mm.setBody(prepareBody());
		mmRequest.getMulticastMessages().add(mm);

		Messages messages = new Messages();
		messages.setMessageId(1L);
		messages.setStatus(Boolean.FALSE);
		messages.setActivationDatetime(null);
		
		when(messagesRepository.findOne(1L)).thenReturn(messages);

		when(messagesRepository.save(messages)).thenReturn(messages);

		MulticastMessageResponse response = multiBroadCastServiceImpl.updateMultiBroadCastMessage(mmRequest);
		assertEquals("ACN_200", response.getResultObject().get(0).getResultCode().toString());
	}
	
	private Header prepareHeader(Integer id, DistributionMode mode) {
		Header header = new Header();
		header.setMessageId(id);
		header.setAutoHideTime(20);
		header.setDisplayDateTime("10/02/2017 10:22");
		header.setDistributionMode(mode);
		header.setIsPopupMessage(true);
		header.setMessageName("TestMessage");
		header.setMessageType(MessageType.PRODUCT);
		header.setStatus("ACTIVE");
		header.setValidityDateTime("11/02/2017 10:22");
		return header;
	}

	private Body prepareBody() {

		Body body = new Body();
		body.setMessagePopupText("TestMessagePopupText");
		body.setMessageText("TestMessageBody");
		body.withMessageURL("TestMessageURL");
		return body;
	}

	private List<Filter> prepareFilter() {
		List<Filter> filters = new ArrayList<Filter>();
		Filter filter = new Filter();
		filter.setFIPS("fIPS");
		filter.setHWVersion("HWVersion");
		filter.setLocationID("TestLocation");
		filter.setUILanguage("UILanguage");
		filters.add(filter);
		return filters;
	}
}
