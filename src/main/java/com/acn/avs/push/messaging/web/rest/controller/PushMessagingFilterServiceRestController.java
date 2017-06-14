/***************************************************************************
 * Copyright (C) Accenture
 * 
 * The reproduction, transmission or use of this document or its contents is not permitted without
 * prior express written consent of Accenture. Offenders will be liable for damages. All rights,
 * including but not limited to rights created by patent grant or registration of a utility model or
 * design, are reserved.
 * 
 * Accenture reserves the right to modify technical specifications and features.
 * 
 * Technical specifications and features are binding only insofar as they are specifically and
 * expressly agreed upon in a written contract.
 * 
 **************************************************************************/

package com.acn.avs.push.messaging.web.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.acn.avs.push.messaging.model.GenericResponse;
import com.acn.avs.push.messaging.model.MulticastMessageFilters;
import com.acn.avs.push.messaging.model.UnicastMessageAssociationRequest;
import com.acn.avs.push.messaging.model.UnicastMessageAssociationResponse;
import com.acn.avs.push.messaging.service.filter.PushMessagingFilterService;
import com.acn.avs.push.messaging.tenant.TenantContext;
import com.acn.avs.push.messaging.tenant.Tenants;

/**
 * @author santosh.sarkar
 *
 */
@RestController
@RequestMapping(value = "/pushmessaging", produces = { MediaType.APPLICATION_JSON_VALUE })
public class PushMessagingFilterServiceRestController {

	@Autowired
	private PushMessagingFilterService pushMessagingFilterService;
	
	/** The Constant logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PushMessagingFilterServiceRestController.class);
	
	private static final String REQUEST_URL_UNICAST = "unicast/association/{messageId}";
	private static final String REQUEST_URL_MULTICAST = "multicast/filters/{messageId}";

	/*
	 * @RequestMapping(value = "/messageFilters", method = RequestMethod.GET)
	 * public ResponseEntity<GetMessageFiltersData> getMessageFilterData() {
	 * 
	 * LOGGER.info("	GET MESSAGE FILTER Data");
	 * 
	 * TenantContext.setCurrentTenant(Tenants.READ); GetMessageFiltersData
	 * filterData = pushMessagingFilterService.fetchMessageFiltersData();
	 * 
	 * return ResponseEntity.ok(filterData);
	 * 
	 * }
	 */

	@RequestMapping(value = REQUEST_URL_UNICAST, method = RequestMethod.GET)
	public ResponseEntity<UnicastMessageAssociationResponse> getUnicastAssociation(@PathVariable("messageId") String messageId) {

		LOGGER.info("	GET UNICAST Assoc ..		");
		
		TenantContext.setCurrentTenant(Tenants.READ);
		UnicastMessageAssociationResponse unicastMessageAssociationResponse = pushMessagingFilterService
				.getUnicastMessageAssociations(messageId);

		return ResponseEntity.ok(unicastMessageAssociationResponse);

	}

	@RequestMapping(value = REQUEST_URL_UNICAST, method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GenericResponse> setUnicastAssociation(@PathVariable("messageId") String messageId,
			@RequestBody UnicastMessageAssociationRequest unicastMessageAssociationRequest) {

		LOGGER.info("	PUT UNICAST Assoc ..		");
		
		TenantContext.setCurrentTenant(Tenants.WRITE);
		GenericResponse genericResponse = new GenericResponse();
		genericResponse = pushMessagingFilterService.setUnicastMessageAssociations(messageId,unicastMessageAssociationRequest);

		return ResponseEntity.ok(genericResponse);

	}

	@RequestMapping(value = REQUEST_URL_MULTICAST, method = RequestMethod.GET)
	public ResponseEntity<MulticastMessageFilters> getMulticastFilter(@PathVariable("messageId") String messageId) {

		LOGGER.info("	GET MULTICAST Filter ..		");
		
		TenantContext.setCurrentTenant(Tenants.READ);
		MulticastMessageFilters multicastMessageFilters = pushMessagingFilterService.getMulticastMessageFilters(messageId);

		return ResponseEntity.ok(multicastMessageFilters);

	}

	@RequestMapping(value = REQUEST_URL_MULTICAST, method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GenericResponse> setMulticastFilter(@PathVariable("messageId") String messageId,
			@RequestBody MulticastMessageFilters multicastMessageFilters) {

		LOGGER.info("	SET MULTICAST Filter ..		");
		
		TenantContext.setCurrentTenant(Tenants.WRITE);
		GenericResponse genericResponse = new GenericResponse();
		genericResponse = pushMessagingFilterService.setMulticastMessageFilters(messageId,multicastMessageFilters);

		return ResponseEntity.ok(genericResponse);
	}

}