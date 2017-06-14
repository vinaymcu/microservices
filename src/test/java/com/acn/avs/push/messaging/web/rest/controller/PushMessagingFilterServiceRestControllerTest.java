package com.acn.avs.push.messaging.web.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.acn.avs.push.messaging.config.TestConfig;
import com.acn.avs.push.messaging.model.Filter;
import com.acn.avs.push.messaging.model.MulticastMessageFilter;
import com.acn.avs.push.messaging.model.MulticastMessageFilters;
import com.acn.avs.push.messaging.model.Stb;
import com.acn.avs.push.messaging.model.Stbs;
import com.acn.avs.push.messaging.model.UnicastMessageAssociation;
import com.acn.avs.push.messaging.model.UnicastMessageAssociationRequest;
import com.acn.avs.push.messaging.service.filter.PushMessagingFilterService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Santosh.Sarkar
 *
 */

public class PushMessagingFilterServiceRestControllerTest extends TestConfig {

	@Mock
	private PushMessagingFilterService pushMessagingFilterService;

	@InjectMocks
	private PushMessagingFilterServiceRestController pushMessagingFilterServiceRestController;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	private String REQUEST_URL_UNICAST = "/pushmessaging/unicast/association/{messageId}";
	private String REQUEST_URL_MULTICAST = "/pushmessaging/multicast/filters/{messageId}";

	@Before
	public void setup() {
		// MockitoAnnotations.initMocks(this);
		// mockMvc =
		// MockMvcBuilders.standaloneSetup(pushMessagingFilterServiceRestController).build();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MockitoAnnotations.initMocks(this);
	}

	/* valid messageId */
	@Test
	public void setUnicastAssociation_ValidMessageIdTest() {
		String messageId = "79";
		UnicastMessageAssociationRequest unicastMessageAssociationRequest = new UnicastMessageAssociationRequest();
		UnicastMessageAssociation unicastMessageAssociation = new UnicastMessageAssociation();
		unicastMessageAssociationRequest.setUnicastMessageAssociations(unicastMessageAssociation);

		String macAddress = "MAC112";
		String subscriberId = "23";
		unicastMessageAssociation.setSubscriberId(subscriberId);

		Stbs stbs = new Stbs();
		List<Stb> StbList = new ArrayList<>();

		Stb stb = new Stb();
		stb.setMACAddress(macAddress);
		StbList.add(stb);
		stbs.setStb(StbList);
		unicastMessageAssociation.setStbs(stbs);
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			String mapper = objectMapper.writeValueAsString(unicastMessageAssociationRequest);
			mockMvc.perform(
					post(REQUEST_URL_UNICAST, messageId).content(mapper).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* invalid messageId */
	@Test
	public void setUnicastAssociation_InvalidMessageIdTest() {
		String messageId = "7";
		UnicastMessageAssociationRequest unicastMessageAssociationRequest = new UnicastMessageAssociationRequest();
		UnicastMessageAssociation unicastMessageAssociation = new UnicastMessageAssociation();
		unicastMessageAssociationRequest.setUnicastMessageAssociations(unicastMessageAssociation);

		String macAddress = "MAC112";
		String subscriberId = "23";
		unicastMessageAssociation.setSubscriberId(subscriberId);

		Stbs stbs = new Stbs();
		List<Stb> StbList = new ArrayList<>();

		Stb stb = new Stb();
		stb.setMACAddress(macAddress);
		StbList.add(stb);
		stbs.setStb(StbList);
		unicastMessageAssociation.setStbs(stbs);
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			String mapper = objectMapper.writeValueAsString(unicastMessageAssociationRequest);
			mockMvc.perform(
					post(REQUEST_URL_UNICAST, messageId).content(mapper).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isExpectationFailed());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void setMulticastFilterTest_ValidMessageIdTest() {
		String messageId = "79";
		MulticastMessageFilters multicastMessageFilters = new MulticastMessageFilters();
		MulticastMessageFilter multicastMessageFilter = new MulticastMessageFilter();
		multicastMessageFilters.setMulticastMessageFilter(multicastMessageFilter);
		Filter filter = new Filter();
		List<Filter> filterList = new ArrayList<>();

		filter.setFIPS("");
		filter.setHWVersion("");
		filter.setIPAddress("");
		filter.setLocationID("");
		filter.setPackageID("");
		filter.setSWVersion("");
		filter.setUILanguage("");
		filter.setUIVersion("");
		filter.setWatchedChannelID("");

		filterList.add(filter);
		multicastMessageFilter.setFilter(filterList);

		ObjectMapper objectMapper = new ObjectMapper();

		try{
			String mapper = objectMapper.writeValueAsString(multicastMessageFilters);
			mockMvc.perform(post(REQUEST_URL_MULTICAST,messageId).content(mapper).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void setMulticastFilterTest_InvalidMessageIdTest() {
		String messageId = "7";
		MulticastMessageFilters multicastMessageFilters = new MulticastMessageFilters();
		MulticastMessageFilter multicastMessageFilter = new MulticastMessageFilter();
		multicastMessageFilters.setMulticastMessageFilter(multicastMessageFilter);
		Filter filter = new Filter();
		List<Filter> filterList = new ArrayList<>();

		filter.setFIPS("");
		filter.setHWVersion("");
		filter.setIPAddress("");
		filter.setLocationID("");
		filter.setPackageID("");
		filter.setSWVersion("");
		filter.setUILanguage("");
		filter.setUIVersion("");
		filter.setWatchedChannelID("");

		filterList.add(filter);
		multicastMessageFilter.setFilter(filterList);

		ObjectMapper objectMapper = new ObjectMapper();

		try{
			String mapper = objectMapper.writeValueAsString(multicastMessageFilters);
			mockMvc.perform(post(REQUEST_URL_MULTICAST,messageId).content(mapper).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isExpectationFailed());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void getUnicastAssociationTest_ValidMessageIdTest() {
		String messageId = "79";
		try{
			mockMvc.perform(get(REQUEST_URL_UNICAST,messageId)).andExpect(status().isOk());
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@Test
	public void getUnicastAssociationTest_InalidMessageIdTest() {
		String messageId = "7";
		try{
			mockMvc.perform(get(REQUEST_URL_UNICAST,messageId)).andExpect(status().isExpectationFailed());
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@Test
	public void getMulticastFilterTest_InvalidMessageIdTest() {
		String messageId = "7";
		try{
			mockMvc.perform(get(REQUEST_URL_MULTICAST,messageId)).andExpect(status().isExpectationFailed());
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@Test
	public void getMulticastFilterTest_ValidMessageIdTest() {
		String messageId = "79";
		try{
			mockMvc.perform(get(REQUEST_URL_MULTICAST,messageId)).andExpect(status().isOk());
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}