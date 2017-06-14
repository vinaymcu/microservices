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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.acn.avs.push.messaging.model.GenericResponse;
import com.acn.avs.push.messaging.model.MessageIds;
import com.acn.avs.push.messaging.model.UnicastMessageRequest;
import com.acn.avs.push.messaging.model.UnicastMessageResponse;
import com.acn.avs.push.messaging.service.UnicastService;

/**
 * @author Happy.Dhingra
 *
 */
@RestController
@RequestMapping(value = "/pushmessaging")
public class UnicastMessageController {

	private static final String REQUEST_URL_CREATE_UNICAST = "/unicast";
	private static final String REQUEST_URL_DELETE_UNICAST = "/delete";
	private static final String REQUEST_URL_ACTIVATE_UNICAST = "/activate";
	private static final String REQUEST_URL_DEACTIVATE_UNICAST = "/deactivate";

	@Autowired
	private UnicastService unicastService;

	/**
	 * Method is used to create unicast messages
	 * 
	 * @param unicastMessageRequest
	 * @return UnicastMessageResponse
	 */
	@RequestMapping(value = REQUEST_URL_CREATE_UNICAST, method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UnicastMessageResponse> createUnicastMessage(
			@RequestBody UnicastMessageRequest unicastMessageRequest) {
		UnicastMessageResponse unicastMessageResponse = unicastService.createUnicastMessage(unicastMessageRequest);
		return ResponseEntity.ok(unicastMessageResponse);

	}

	/**
	 * This method updates the Unicast Messages
	 * 
	 * @param unicastMessageRequest
	 * @return UnicastMessageResponse
	 */
	@RequestMapping(value = REQUEST_URL_CREATE_UNICAST, method = RequestMethod.PUT, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UnicastMessageResponse> updateUnicastMessage(
			@RequestBody UnicastMessageRequest unicastMessageRequest) {
		UnicastMessageResponse unicastMessageResponse = unicastService.updateUnicastMessage(unicastMessageRequest);
		return ResponseEntity.ok(unicastMessageResponse);
	}

	/**
	 * This method deletes the Unicast Messages
	 * 
	 * @param unicastMessageRequest
	 * @return UnicastMessageResponse
	 */
	@RequestMapping(value = REQUEST_URL_DELETE_UNICAST, method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GenericResponse> deleteMessage(@RequestBody MessageIds messageIds) {
		GenericResponse genericResponse = unicastService.deleteMessage(messageIds);
		return ResponseEntity.ok(genericResponse);
	}

	/**
	 * This method is used to activate Unicast messages
	 * 
	 * @param unicastMessageRequest
	 * @return UnicastMessageResponse
	 */
	@RequestMapping(value = REQUEST_URL_ACTIVATE_UNICAST, method = RequestMethod.PUT, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GenericResponse> activateUnicastMessage(@RequestBody MessageIds messageIds) {
		GenericResponse genericResponse = unicastService.activateMessage(messageIds);
		return ResponseEntity.ok(genericResponse);
	}

	/**
	 * This method is used to deactivate Unicast messages
	 * 
	 * @param unicastMessageRequest
	 * @return UnicastMessageResponse
	 */
	@RequestMapping(value = REQUEST_URL_DEACTIVATE_UNICAST, method = RequestMethod.PUT, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GenericResponse> deactivateUnicastMessage(@RequestBody MessageIds messageIds) {
		GenericResponse genericResponse = unicastService.deactivateMessage(messageIds);
		return ResponseEntity.ok(genericResponse);
	}

}
