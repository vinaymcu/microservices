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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acn.avs.push.messaging.model.GetMessageListResponse;
import com.acn.avs.push.messaging.model.GetMessagesResponse;
import com.acn.avs.push.messaging.model.MessageTypesResponse;
import com.acn.avs.push.messaging.service.PushMessagingService;
import com.acn.avs.push.messaging.tenant.TenantContext;
import com.acn.avs.push.messaging.tenant.Tenants;

@RestController
@RequestMapping(value = "/pushmessaging", produces = { MediaType.APPLICATION_JSON_VALUE })
public class PushMessagingRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PushMessagingRestController.class);

	private static final String REQUEST_URL_GET_MESSAGES = "/messages";
	private static final String REQUEST_URL_GET_MESSAGELIST = "/messagelist";
	private static final String REQUEST_URL_GET_MESSAGETYPE = "/messagetypes";
	private static final String REQUEST_URL_GET_MESSAGE_DELTA = "/messagedelta";
	private static final String REQUEST_URL_GET_MESSAGE_DOC = "/messageDocument";

	/** SEARCHBY_STR */
	private static final String SEARCHBY_STR = "searchBy";

	/** SEARCHVALUE_STR */
	private static final String SEARCHVALUE_STR = "searchValue";

	/** SEARCHOPERATION_STR */
	private static final String SEARCHOPERATION_STR = "searchOperation";

	/** SORTBY_STR */
	private static final String SORTBY_STR = "sortBy";

	/** STARTINDEX_STR */
	private static final String STARTINDEX_STR = "startIndex";

	/** PAGESIZE_STR */
	private static final String PAGESIZE_STR = "pageSize";

	/** STATUS_STR */
	private static final String STATUS_STR = "status";

	/** TIMESTAMP_STR */
	private static final String TIMESTAMP_STR = "timestamp";

	@Autowired
	private PushMessagingService messageService;
	
	/**
	 * Method to get message using provided parameters
	 * 
	 * @param messageId
	 * @param messageType
	 * @param mode
	 * @param startIndex
	 * @param pageSize
	 * 
	 * @return GetMessagesResponse
	 */
	@RequestMapping(value = REQUEST_URL_GET_MESSAGES, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GetMessagesResponse> getMessages(
			@RequestParam(value = "messageId", required = false) String messageId,
			@RequestParam(value = "messageType", required = false) String messageType,
			@RequestParam(value = "distributionMode", required = false) String mode,
			@RequestParam(value = "startIndex", defaultValue = "0") String startIndex,
			@RequestParam(value = "pageSize", defaultValue = "100") String pageSize) {

		LOGGER.info("+ getMessages");
		
		TenantContext.setCurrentTenant(Tenants.READ);
		GetMessagesResponse response = messageService.getMessages(messageId, messageType, mode, startIndex, pageSize);
		
		LOGGER.info("- getMessages");
		return ResponseEntity.ok(response);
	}

	/**
	 * Method to get messages using provided parameters
	 * 
	 * @param searchBy
	 * @param searchOperation
	 * @param searchValue
	 * @param status
	 * @param sortBy
	 * @param startIndex
	 * @param pageSize
	 * 
	 * @return GetMessageListResponse
	 */
	@RequestMapping(value = REQUEST_URL_GET_MESSAGELIST, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetMessageListResponse> getMessageList(
			@RequestParam(value = SEARCHBY_STR, required = false) String searchBy,
			@RequestParam(value = SEARCHOPERATION_STR, required = false) String searchOperation,
			@RequestParam(value = SEARCHVALUE_STR, required = false) String searchValue,
			@RequestParam(value = STATUS_STR, required = false) String status,
			@RequestParam(value = SORTBY_STR, required = false) String sortBy,
			@RequestParam(value = STARTINDEX_STR, defaultValue = "0") String startIndex,
			@RequestParam(value = PAGESIZE_STR, defaultValue = "100") String pageSize) {
		LOGGER.info("+ getMessageList");

		TenantContext.setCurrentTenant(Tenants.READ);
		GetMessageListResponse response = messageService.getMessageList(searchBy, searchOperation, searchValue, status,
				sortBy, startIndex, pageSize);
		
		LOGGER.info("- getMessageList");

		return ResponseEntity.ok(response);
	}

	/**
	 * Method to get all available message types
	 * 
	 * @return MessageTypesResponse
	 */
	@RequestMapping(value = REQUEST_URL_GET_MESSAGETYPE, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MessageTypesResponse> getMessageTypes() {
		LOGGER.info("+ getMessageTypes");

		TenantContext.setCurrentTenant(Tenants.READ);
		MessageTypesResponse response = messageService.getMessageType();

		LOGGER.info("- getMessageTypes");
		return ResponseEntity.ok(response);
	}

	/**
	 * Method to get all messages having creation time greater or equal to provided time in request
	 * 
	 * @param timeStamp
	 * 
	 * @return GetMessagesResponse
	 */
	@RequestMapping(value = REQUEST_URL_GET_MESSAGE_DELTA, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GetMessagesResponse> getMessageDelta(
			@RequestParam(value = TIMESTAMP_STR) Long timeStamp) {
		LOGGER.info("+ getMessageDelta");
		
		TenantContext.setCurrentTenant(Tenants.READ);
		GetMessagesResponse response = messageService.getMessageDelta(timeStamp);

		LOGGER.info("- getMessageDelta");
		return ResponseEntity.ok(response);
	}

	/**
	 * Get all message for document generation 
	 * 
	 * @return GetMessagesResponse
	 */
	@RequestMapping(value = REQUEST_URL_GET_MESSAGE_DOC, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GetMessagesResponse> getMessageDocument() {
		LOGGER.info("+ getMessageDocument");
		
		TenantContext.setCurrentTenant(Tenants.READ);
		GetMessagesResponse response = messageService.getMessageDocument();
		
		LOGGER.info("- getMessageDocument");
		return ResponseEntity.ok(response);
	}

}
