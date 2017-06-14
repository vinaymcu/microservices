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

import com.acn.avs.push.messaging.model.GetMessageListResponse;
import com.acn.avs.push.messaging.model.GetMessagesResponse;
import com.acn.avs.push.messaging.model.MessageTypesResponse;

public interface PushMessagingService {

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
	public GetMessagesResponse getMessages(String messageId, String messageType, String mode, String startIndex,
			String pageSize);

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
	public GetMessageListResponse getMessageList(String searchBy, String searchOperation, String searchValue,
			String status, String sortBy, String startIndex, String pageSize);

	/**
	 * Method to get all available message types
	 * 
	 * @return MessageTypesResponse
	 */
	public MessageTypesResponse getMessageType();

	/**
	 * Method to get all messages having creation time greater or equal to provided time in request
	 * 
	 * @param timeStamp
	 * 
	 * @return GetMessagesResponse
	 */
	public GetMessagesResponse getMessageDelta(Long timeStamp);

	/**
	 * Get all message for document generation 
	 * 
	 * @return GetMessagesResponse
	 */
	public GetMessagesResponse getMessageDocument();

}
