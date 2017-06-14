package com.acn.avs.push.messaging.web.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.acn.avs.push.messaging.config.TestConfig;
import com.acn.avs.push.messaging.model.Body;
import com.acn.avs.push.messaging.model.Filter;
import com.acn.avs.push.messaging.model.Header;
import com.acn.avs.push.messaging.model.Header.DistributionMode;
import com.acn.avs.push.messaging.model.Header.MessageType;
import com.acn.avs.push.messaging.model.MulticastMessage;
import com.acn.avs.push.messaging.model.MulticastMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MultiBroadCastContollerTest extends TestConfig {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	/** Url */
	private static final String REQUEST_URL_CREATE_MULTICAST = "/pushmessaging/multicast";

	/**
	 * Setup the webApplicationContext context.
	 */
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Integration test cases for creating multicast Message
	 */
	@Test
	public void createMultiCastMessage() {

		MulticastMessageRequest multicastMessageRequest = getMulticastMessageRequest("TestMsgName");

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String mapper = objectMapper.writeValueAsString(multicastMessageRequest);
			mockMvc.perform(post(REQUEST_URL_CREATE_MULTICAST).content(mapper).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Integration test cases for creating multicast Message
	 */
	@Test
	public void createMultiCastMessage_validationFailed() {
		MulticastMessageRequest multicastMessageRequest = getMulticastMessageRequest(null);

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String mapper = objectMapper.writeValueAsString(multicastMessageRequest);
			mockMvc.perform(post(REQUEST_URL_CREATE_MULTICAST).content(mapper).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Integration test cases for update multicast Message
	 */
	@Test
	public void updateMultiCastMessage() {
		MulticastMessageRequest multicastMessageRequest = getMulticastMessageRequest("TestMsgName");

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String mapper = objectMapper.writeValueAsString(multicastMessageRequest);
			mockMvc.perform(put(REQUEST_URL_CREATE_MULTICAST).content(mapper).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void updateMultiCastMessage_validationFailed() {
		MulticastMessageRequest multicastMessageRequest = getMulticastMessageRequest(null);

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String mapper = objectMapper.writeValueAsString(multicastMessageRequest);
			mockMvc.perform(put(REQUEST_URL_CREATE_MULTICAST).content(mapper).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private MulticastMessageRequest getMulticastMessageRequest(String messageName) {
		MulticastMessageRequest multicastMessageRequest = new MulticastMessageRequest();
		MulticastMessage mm = new MulticastMessage();
		mm.setFilter(prepareFilter());
		mm.setHeader(prepareHeader(1, DistributionMode.MULTICAST, messageName));
		mm.setBody(prepareBody());
		multicastMessageRequest.getMulticastMessages().add(mm);
		return multicastMessageRequest;
	}

	
	private Header prepareHeader(Integer id, DistributionMode mode, String messageName) {
		Header header = new Header();
		header.setMessageId(id);
		header.setAutoHideTime(20);
		header.setDisplayDateTime("10/02/2017 10:22");
		header.setDistributionMode(mode);
		header.setIsPopupMessage(true);
		header.setMessageName(messageName);
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
