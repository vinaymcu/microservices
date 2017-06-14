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

package com.acn.avs.push.messaging.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.acn.avs.push.messaging.model.EventUpdateRequest;
import com.acn.avs.push.messaging.model.GenericResponse;
import com.acn.avs.push.messaging.util.Constants;

/**
 * UnicastNotifierClient for notify UnicastNotifier
 * 
 *
 */
@FeignClient(name = Constants.UNICAST_NOTIFIER, path = "/unicastnotifier")
public interface UnicastNotifierClient {

	/** UNICAST_TRIGGER_MAPPING */
	public static final String UNICAST_TRIGGER_MAPPING = "/trigger";

	/**
	 * Send notification to unicast notifier
	 * 
	 * @param eventUpdateRequest
	 * @return response entity
	 */
	@RequestMapping(value = UNICAST_TRIGGER_MAPPING, method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GenericResponse> notifyUnicastNotifier(@RequestBody EventUpdateRequest eventUpdateRequest);
}
