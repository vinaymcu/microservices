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

package com.acn.avs.push.messaging.service;

import com.acn.avs.push.messaging.model.GenericResponse;
import com.acn.avs.push.messaging.model.MessageIds;
import com.acn.avs.push.messaging.model.UnicastMessageRequest;
import com.acn.avs.push.messaging.model.UnicastMessageResponse;

/**
 * @author Happy.Dhingra
 *
 */
public interface UnicastService {
    
	/**
	 * create UnicastMessage
	 * @param unicastMessageRequest
	 * @return UnicastMessageResponse
	 */
	UnicastMessageResponse createUnicastMessage(UnicastMessageRequest unicastMessageRequest);

	/**
	 * 
	 * update UnicastMessage
	 * 
	 * @param unicastMessageRequest
	 * @return UnicastMessageResponse
	 */
	UnicastMessageResponse updateUnicastMessage(UnicastMessageRequest unicastMessageRequest);

	/**
	 * delete Message
	 * 
	 * @param messageIds
	 * @return GenericResponse
	 */
	GenericResponse deleteMessage(MessageIds messageIds);

	/**
	 * activate Message
	 * 
	 * @param messageIds
	 * @return GenericResponse
	 */
	GenericResponse activateMessage(MessageIds messageIds);

	/**
	 * deactivate Message
	 * 
	 * @param messageIds
	 * @return GenericResponse
	 */
	GenericResponse deactivateMessage(MessageIds messageIds);

/*	*//**
	 * get UnicastAssociation
	 * 
	 * @param messageId
	 * @return UnicastMessageAssociationResponse
	 *//*
	UnicastMessageAssociationResponse getUnicastAssociation(String messageId);

	*//**
	 * set UnicastAssociation
	 * 
	 * @param messageId
	 * @param unicastMessageAssociationRequest
	 * @return GenericResponse
	 *//*
	GenericResponse setUnicastAssociation(String messageId,
			UnicastMessageAssociationRequest unicastMessageAssociationRequest);*/
}
