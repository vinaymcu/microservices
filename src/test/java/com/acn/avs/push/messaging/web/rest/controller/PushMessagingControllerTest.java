package com.acn.avs.push.messaging.web.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.acn.avs.push.messaging.config.TestConfig;

public class PushMessagingControllerTest extends TestConfig {
	
	private static final String REQUEST_URL_GET_MESSAGES = "/pushmessaging/messages?messageId=64&messageType=Service &startIndex=0&pageSize=100";
	//private static final String REQUEST_URL_GET_MESSAGELIST = "/pushmessaging/messagelist?searchBy=MessageName&searchOperation=CONTAINS&searchValue=UNICASTMSG&status=active& sortBy=MessageName&startIndex=0&pageSize=100";
	private static final String REQUEST_URL_GET_MESSAGETYPE = "/pushmessaging/messagetypes";
	private static final String REQUEST_URL_GET_MESSAGE_DELTA = "/pushmessaging/messagedelta?timestamp=1353962280000";
	private static final String REQUEST_URL_GET_MESSAGE_DOC = "/pushmessaging/messageDocument";

	/** The mock mvc. */
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	
	
	/**
	 * Setup the webApplicationContext context.
	 */
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Integration test cases for GetMessages
	 */
	@Test
	public void testGetMessages() {
		try {
		mockMvc.perform(get(REQUEST_URL_GET_MESSAGES)).andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Integration test cases for getMessageTypes
	 */
	@Test
	public void testGetMessageTypes() {
		try {
		mockMvc.perform(get(REQUEST_URL_GET_MESSAGETYPE)).andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Integration test cases for getMessageDelta
	 */
	@Test
	public void testGetMessageDelta() {
		try {
		mockMvc.perform(get(REQUEST_URL_GET_MESSAGE_DELTA)).andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Integration test cases for getMessageDocument
	 */
	@Test
	public void testGetMessageDocument() {
		try {
		mockMvc.perform(get(REQUEST_URL_GET_MESSAGE_DOC)).andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
