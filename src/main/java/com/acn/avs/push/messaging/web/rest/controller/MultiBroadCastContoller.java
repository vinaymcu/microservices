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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.acn.avs.push.messaging.enums.ErrorCode;
import com.acn.avs.push.messaging.model.MulticastMessageRequest;
import com.acn.avs.push.messaging.model.MulticastMessageResponse;
import com.acn.avs.push.messaging.service.MultiBroadCastService;
import com.acn.avs.push.messaging.util.Constants;
import com.acn.avs.push.messaging.util.JsonRequestValidator;

@RestController
@RequestMapping(value = "/pushmessaging")
public class MultiBroadCastContoller {

	@Autowired
	private JsonRequestValidator jsonRequestValidator;

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MultiBroadCastContoller.class);

	@Autowired
	private MultiBroadCastService multiBroadCastService;

	/**
	 * create the Multi/BroadCast Messages 
	 * @param mmRequest
	 * @return mmResponse of MulticastMessageResponse
	 */
	@RequestMapping(value = "/multicast", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MulticastMessageResponse> createMultiBroadCastMessage(
			@RequestBody MulticastMessageRequest mmRequest) {
		LOGGER.info("createMultiBroadCastMessage ");

		MulticastMessageResponse mmResponse = null;
		String requestValidationResult = validateMulticastMessageRequest(mmRequest);
		if (StringUtils.isEmpty(requestValidationResult)) {
			LOGGER.info("process the createMultiBroadCastMessage request ");
			mmResponse = multiBroadCastService.createMultiBroadCastMessage(mmRequest);
		} else {
			mmResponse = new MulticastMessageResponse();
			mmResponse.setResultCode(ErrorCode.REQUEST_VALIDATION_FAILED.getCode());
			mmResponse.setResultDescription(requestValidationResult);
		}
		mmResponse.setSystemTime(System.currentTimeMillis());
		return ResponseEntity.ok(mmResponse);
	}

	/**
	 * update the Multi/BroadCast Messages 
	 * @param mmRequest
	 * @return mmResponse of MulticastMessageResponse
	 */
	@RequestMapping(value = "/multicast", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MulticastMessageResponse> updateMultiBroadCastMessage(
			@RequestBody MulticastMessageRequest mmRequest) {

		LOGGER.info("updateMultiBroadCastMessage  in MultiBroadCastContoller ");

		MulticastMessageResponse mmResponse = null;
		String requestValidationResult = validateMulticastMessageRequest(mmRequest);
		if (StringUtils.isEmpty(requestValidationResult)) {
			LOGGER.info("process the updateMultiBroadCastMessage request ");
			mmResponse = multiBroadCastService.updateMultiBroadCastMessage(mmRequest);
		} else {
			mmResponse = new MulticastMessageResponse();
			mmResponse.setResultCode(ErrorCode.REQUEST_VALIDATION_FAILED);
			mmResponse.setResultDescription(requestValidationResult);
		}
		mmResponse.setSystemTime(System.currentTimeMillis());
		return ResponseEntity.ok(mmResponse);
	}

	/**
	 * validate MulticastMessageRequest
	 * 
	 * @param mmRequest
	 * @return
	 */
	private String validateMulticastMessageRequest(MulticastMessageRequest mmRequest) {

		return jsonRequestValidator.validate(mmRequest, "MulticastMessageRequest.json", Constants.SCHEMA_FOLDER_PATH);
	}
}
