package com.acn.avs.push.messaging.web.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.acn.avs.push.messaging.config.TestConfig;
import com.acn.avs.push.messaging.generator.BodyModelGenerator;
import com.acn.avs.push.messaging.generator.HeaderModelGenerator;
import com.acn.avs.push.messaging.generator.StbsModelGenerator;
import com.acn.avs.push.messaging.model.MessageId;
import com.acn.avs.push.messaging.model.MessageIds;
import com.acn.avs.push.messaging.model.UnicastMessage;
import com.acn.avs.push.messaging.model.UnicastMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Happy.Dhingra
 * This class is used for integration test case 
 * for Unicast Message Controller
 *
 */
public class UnicastMessageControllerTest extends TestConfig {
	/** The mock mvc. */
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private static final String REQUEST_URL_CREATE_UNICAST = "/pushmessaging/unicast";
	private static final String REQUEST_URL_DELETE_UNICAST = "/pushmessaging/delete";
	private static final String REQUEST_URL_ACTIVATE_UNICAST = "/pushmessaging/activate";
	private static final String REQUEST_URL_DEACTIVATE_UNICAST = "/pushmessaging/deactivate";
	HeaderModelGenerator headerModelGenerator = new HeaderModelGenerator();
	BodyModelGenerator bodyModelGenerator = new BodyModelGenerator();
	StbsModelGenerator stbsModelGenerator = new StbsModelGenerator();

	/**
	 * Setup the webApplicationContext context.
	 */
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Integration test cases for creating Unicast Message
	 */
	@Test
	public void createUnicastMessage() {
		UnicastMessageRequest unicastMessageRequest = new UnicastMessageRequest();
		UnicastMessage unicastMessage = new UnicastMessage();
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessage.setHeader(headerModelGenerator.getValid().get(0));
		unicastMessage.setStbs(stbsModelGenerator.getValid().get(0));
		unicastMessage.setSubscriberId("1");
		unicastMessageRequest.getUnicastMessage().add(unicastMessage);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String mapper = objectMapper.writeValueAsString(unicastMessageRequest);
			mockMvc.perform(post(REQUEST_URL_CREATE_UNICAST).content(mapper).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Integration test cases for updating Unicast Message
	 */
	@Test
	public void updateUnicastMessage() {
		UnicastMessageRequest unicastMessageRequest = new UnicastMessageRequest();
		UnicastMessage unicastMessage = new UnicastMessage();
		unicastMessage.setBody(bodyModelGenerator.getValid().get(0));
		unicastMessage.setHeader(headerModelGenerator.getValid().get(0));
		unicastMessage.setStbs(stbsModelGenerator.getValid().get(0));
		unicastMessage.setSubscriberId("1");
		unicastMessageRequest.getUnicastMessage().add(unicastMessage);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String mapper = objectMapper.writeValueAsString(unicastMessageRequest);
			mockMvc.perform(put(REQUEST_URL_CREATE_UNICAST).content(mapper).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Integration test cases for activating Unicast Message
	 */
	@Test
	public void activateUnicastMessage() {
		MessageIds messageIds=new MessageIds();
		MessageId message=new MessageId();
		message.setMessageId(2);;
		messageIds.getMessageIds().add(message);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String mapper = objectMapper.writeValueAsString(messageIds);
			mockMvc.perform(put(REQUEST_URL_ACTIVATE_UNICAST).content(mapper).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Integration test cases for deleting Unicast Message
	 */
	@Test
	public void deleteUnicastMessage() {
		MessageIds messageIds=new MessageIds();
		MessageId message=new MessageId();
		message.setMessageId(2);;
		messageIds.getMessageIds().add(message);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String mapper = objectMapper.writeValueAsString(messageIds);
			mockMvc.perform(post(REQUEST_URL_DELETE_UNICAST).content(mapper).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Integration test cases for deactivating Unicast Message
	 */
	@Test
	public void deactivateUnicastMessage() {
		MessageIds messageIds=new MessageIds();
		MessageId message=new MessageId();
		message.setMessageId(2);;
		messageIds.getMessageIds().add(message);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String mapper = objectMapper.writeValueAsString(messageIds);
			mockMvc.perform(put(REQUEST_URL_DEACTIVATE_UNICAST).content(mapper).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
