package com.acn.avs.push.messaging.service.impl;

import static org.junit.Assert.assertEquals;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.acn.avs.push.messaging.entity.Messages;
import com.acn.avs.push.messaging.enums.SearchOperator;
import com.acn.avs.push.messaging.exception.PushMessagingException;
import com.acn.avs.push.messaging.model.GetMessageListResponse;
import com.acn.avs.push.messaging.model.GetMessagesResponse;
import com.acn.avs.push.messaging.repository.CustomPushMessagingRepository;
import com.acn.avs.push.messaging.repository.PushMessagingRepository;
import com.acn.avs.push.messaging.util.SearchFilter;

@RunWith(SpringJUnit4ClassRunner.class)
public class PushMessagingServiceImplTest {

	@InjectMocks
	PushMessagingServiceImpl pushMessagingServiceImpl;
	
	@Mock
	PushMessagingRepository pushmsgRepository;
	
	@Mock
	private CustomPushMessagingRepository customMessagingRepository;
	
	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetMessages()
	{
		GetMessagesResponse getMessagesResponse=pushMessagingServiceImpl.getMessages(null, "Service", null, "0", "100");
		assertEquals(0, getMessagesResponse.getMessages().getMessage().size());
	}
	
	@Test
	public void testGetMessages2()
	{
		when(pushmsgRepository.findMessageCountWithLike("1%")).thenReturn(1);
		GetMessagesResponse getMessagesResponse=pushMessagingServiceImpl.getMessages("1*", "Service", null, "0", "100");
		assertEquals(0, getMessagesResponse.getMessages().getMessage().size());
	}
	
	@Test
	public void testGetMessages3()
	{
		when(pushmsgRepository.findMessageCountWithLike("1%")).thenReturn(1);
		GetMessagesResponse getMessagesResponse=pushMessagingServiceImpl.getMessages("1*", null, null, "0", "100");
		assertEquals(0, getMessagesResponse.getMessages().getMessage().size());
	}
	
	@Test(expected=PushMessagingException.class)
	public void testGetMessages4()
	{
		pushMessagingServiceImpl.getMessages("1*", null, null, "0", "100");
	}
	
	@Test
	public void testGetMessageList()
	{
		GetMessageListResponse getMessagesResponse=pushMessagingServiceImpl.getMessageList("MessageName", "CONTAINS", "UNICASTMSG", "active", "MessageName","0","100");
		assertEquals(0, getMessagesResponse.getMessageList().size());
	}
	
	@Test
	public void testGetMessageList2()
	{
		Messages testMessage = new Messages(1L);
		List<Messages> messages = new ArrayList<>();
		messages.add(new Messages());
		messages.add(testMessage);
		
		SearchFilter searchFilter = new SearchFilter();
		searchFilter.setSearchBy("MessageName");
		searchFilter.setSearchValue("UNICASTMSG");
		searchFilter.setSearchOperation(SearchOperator.getOperation("CONTAINS"));
		searchFilter.setStatus("active");
		searchFilter.setSortBy("MessageName");
		searchFilter.setStartIndex(getPageIndexValue("0", false));
		searchFilter.setPageSize(getPageIndexValue("100", true));
		when(customMessagingRepository.search(searchFilter)).thenReturn(messages);
		GetMessageListResponse getMessagesResponse=pushMessagingServiceImpl.getMessageList("MessageName", "CONTAINS", "UNICASTMSG", "active", "MessageName","0","100");
		assertEquals(0, getMessagesResponse.getMessageList().size());
	}
	
	@Test
	public void testPopulateMessageListResponse()
	{
		Messages message=new Messages();
		message.setMultiCast(true);
		List<Messages> messages=new ArrayList<>();
		messages.add(message);
		GetMessageListResponse  getMessageListResponse=pushMessagingServiceImpl.populateMessageListResponse(messages);
		assertEquals(1,getMessageListResponse.getMessageList().size());
	}
	
	@Test
	public void testPopulateMessageListResponse2()
	{
		Messages message=new Messages();
		message.setMultiCast(false);
		message.setActivationDatetime(BigInteger.valueOf(1212121212));
		message.setCreationDatetime(BigInteger.valueOf(1212121212));
		message.setExpirationDatetime(BigInteger.valueOf(12341234));
		message.setStatus(true);
		List<Messages> messages=new ArrayList<>();
		messages.add(message);
		GetMessageListResponse  getMessageListResponse=pushMessagingServiceImpl.populateMessageListResponse(messages);
		assertEquals(1,getMessageListResponse.getMessageList().size());
	}
	
	@Test(expected=PushMessagingException.class)
	public void testGetMessageDelta(){
		pushMessagingServiceImpl.getMessageDelta(null);
	}
	
	private Integer getPageIndexValue(String startIndex, boolean isPageSize) {
		Integer indexValue;
		try {
			indexValue = Integer.parseInt(startIndex);
			if (indexValue < 0) {
				indexValue = isPageSize ? 100 : 0;
			}
		} catch (NumberFormatException exception) {
			indexValue = isPageSize ? 100 : 0;
		}

		return indexValue;
	}

	
}
