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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.acn.avs.push.messaging.model.ResponseWrapper;
import com.acn.avs.push.messaging.util.Constants;

/**
 * DocumentServiceClient for generateDocument
 * 
 *
 */
@FeignClient(name = Constants.DOCUMENT_SERVICE, path = "/documentManager")
public interface DocumentServiceClient {

	/** DOCUMENT_MANAGER_MAPPING */
	public static final String DOCUMENT_MANAGER_MAPPING = "/generateDocument/{ids}";

	/**
	 * end request to Document Manager to initiate the message document generation process
	 * @param ids
	 * @return ResponseEntity
	 */
	@RequestMapping(value = DOCUMENT_MANAGER_MAPPING, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseWrapper> generateDocument(@PathVariable("ids") String ids);
}
