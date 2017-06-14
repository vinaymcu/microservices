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

package com.acn.avs.push.messaging.service.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acn.avs.push.messaging.config.ConfigurationProperties;
import com.acn.avs.push.messaging.entity.Filters;
import com.acn.avs.push.messaging.entity.MessageTypes;
import com.acn.avs.push.messaging.entity.Messages;
import com.acn.avs.push.messaging.entity.StbAddressing;
import com.acn.avs.push.messaging.enums.DistributionModes;
import com.acn.avs.push.messaging.enums.ErrorCode;
import com.acn.avs.push.messaging.enums.MessageTypeEnum;
import com.acn.avs.push.messaging.enums.SearchOperator;
import com.acn.avs.push.messaging.enums.Status;
import com.acn.avs.push.messaging.exception.PushMessagingException;
import com.acn.avs.push.messaging.model.Body;
import com.acn.avs.push.messaging.model.Filter;
import com.acn.avs.push.messaging.model.GetMessageListResponse;
import com.acn.avs.push.messaging.model.GetMessagesResponse;
import com.acn.avs.push.messaging.model.Header;
import com.acn.avs.push.messaging.model.Message;
import com.acn.avs.push.messaging.model.MessageList;
import com.acn.avs.push.messaging.model.MessageTypesResponse;
import com.acn.avs.push.messaging.model.Stb;
import com.acn.avs.push.messaging.model.Stbs;
import com.acn.avs.push.messaging.repository.CustomPushMessagingRepository;
import com.acn.avs.push.messaging.repository.MessageTypeRepository;
import com.acn.avs.push.messaging.repository.PushMessagingRepository;
import com.acn.avs.push.messaging.service.PushMessagingService;
import com.acn.avs.push.messaging.util.Constants;
import com.acn.avs.push.messaging.util.SearchFilter;

/**
 * @author naresh.kumar
 *
 */
@Service
public class PushMessagingServiceImpl implements PushMessagingService {

	public static final Logger LOGGER = LoggerFactory.getLogger(PushMessagingServiceImpl.class);

	@Autowired
	PushMessagingRepository pushmsgRepository;

	@Autowired
	MessageTypeRepository msgTypeRespo;

	@Autowired
	private CustomPushMessagingRepository customMessagingRepository;

	/** The configuration properties. */
	@Autowired
	private ConfigurationProperties configurationProperties;

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
	@Override
	@Transactional
	public GetMessagesResponse getMessages(String messageId, String messageType, String mode, String startIndex,
			String pageSize) {

		StringBuilder loggerStr = new StringBuilder();
		loggerStr.append("++ getMessages with Parameters [ messageId = ").append(messageId).append(", messageType = ")
				.append(messageType).append(", mode = ").append(mode).append(", startIndex = ").append(startIndex)
				.append(", pageSize = ").append(pageSize).append(" ]");

		LOGGER.info(loggerStr.toString());

		if (messageId != null) {
			if (messageId.contains(Constants.STAR_STR)) {
				messageId = messageId.replace(Constants.STAR_STR, Constants.PERCENTAGE_STR);
			}
			int count = pushmsgRepository.findMessageCountWithLike(messageId);
			if (count == 0) {
				throw new PushMessagingException(ErrorCode.MESSAGE_NOT_EXIST,
						new String[] { ErrorCode.MESSAGE_NOT_EXIST.getCode() });
			}
		} 
		else {
			messageId = Constants.PERCENTAGE_STR;
		}

		if (messageType == null || !isValidMessageType(messageType)) {
			messageType = Constants.PERCENTAGE_STR;
		}

		Boolean multicast = Constants.UNICAST.equals(mode) ? Boolean.FALSE : Boolean.TRUE;

		if (mode == null || !isValidDistributionMode(mode)) {
			mode = Constants.PERCENTAGE_STR;
		}

		Integer startIndexValue = getPageIndexValue(startIndex, false);
		Integer pageSizeValue = getPageIndexValue(pageSize, true);

		List<Messages> messageListDb = pushmsgRepository.getMessagesByTypeAndMode(messageId, messageType, multicast,
				startIndexValue, pageSizeValue);

		GetMessagesResponse response = new GetMessagesResponse();
		com.acn.avs.push.messaging.model.Messages messages = new com.acn.avs.push.messaging.model.Messages();
		List<Message> respMessageList = messages.getMessage();
		response.setMessages(messages);

		populateMessageDetails(respMessageList, messageListDb);

		LOGGER.info("-- getMessages with total messages {}", messageListDb.size());

		return response;
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
	@Override
	@Transactional
	public GetMessageListResponse getMessageList(String searchBy, String searchOperation, String searchValue,
			String status, String sortBy, String startIndex, String pageSize) {

		StringBuilder loggerStr = new StringBuilder();
		loggerStr.append("++ getMessageList with Parameters [ searchBy = ").append(searchBy)
				.append(", searchOperation = ").append(searchOperation).append(", searchValue = ").append(searchValue)
				.append(", status = ").append(status).append(", sortBy=").append(sortBy).append(", startIndex = ")
				.append(startIndex).append(", pageSize = ").append(pageSize).append(" ]");

		LOGGER.info(loggerStr.toString());

		SearchFilter searchFilter = new SearchFilter();
		searchFilter.setSearchBy(searchBy);
		searchFilter.setSearchValue(searchValue);
		searchFilter.setSearchOperation(searchOperation == null ? null : SearchOperator.getOperation(searchOperation));
		searchFilter.setStatus(status);
		searchFilter.setSortBy(sortBy);
		searchFilter.setStartIndex(getPageIndexValue(startIndex, false));
		searchFilter.setPageSize(getPageIndexValue(pageSize, true));
		List<Messages> messagesDb = customMessagingRepository.search(searchFilter);
		GetMessageListResponse messageListResponse = populateMessageListResponse(messagesDb);

		LOGGER.info("-- getMessageList with total messages : " + messagesDb.size());

		return messageListResponse;
	}

	/**
	 * Method to populate message list response
	 * 
	 * @param messagesDb
	 * 
	 * @return GetMessageListResponse
	 */
	GetMessageListResponse populateMessageListResponse(List<Messages> messagesDb) {
		GetMessageListResponse messageListResponse = new GetMessageListResponse();
		for (Messages tmpMessage : messagesDb) {
			MessageList messageList = new MessageList();
			messageList.setMessageId(tmpMessage.getMessageId() == null ? null : tmpMessage.getMessageId().intValue());
			messageList.setMessageName(tmpMessage.getMessageName());
			messageList.setDistributionMode(
					getMsgListDistributionMode(tmpMessage.getMultiCast(), tmpMessage.getFiltersList()));
			messageList.setCreationDate(tmpMessage.getCreationDatetime() == null ? null
					: formatDate(tmpMessage.getCreationDatetime().longValue()));
			messageList.setActivationDate(tmpMessage.getActivationDatetime() == null ? null
					: formatDate(tmpMessage.getActivationDatetime().longValue()));
			messageList.setExprireDate(tmpMessage.getExpirationDatetime() == null ? null
					: formatDate(tmpMessage.getExpirationDatetime().longValue()));
			messageList.setStatus(tmpMessage.getStatus() == null ? null
					: (tmpMessage.getStatus() == true ? Status.getStatusValue("active").getStatus()
							: Status.getStatusValue("inactive").getStatus()));
			messageListResponse.getMessageList().add(messageList);
		}

		return messageListResponse;
	}

	/**
	 * Method to get all available message types
	 * 
	 * @return MessageTypesResponse
	 */
	@Override
	public MessageTypesResponse getMessageType() {

		List<MessageTypes> msgTypeList = msgTypeRespo.findAll();

		List<String> messageNames = msgTypeList.stream().map(MessageTypes::getMessageType).collect(Collectors.toList());

		MessageTypesResponse response = new MessageTypesResponse();
		response.getMessageType().addAll(messageNames);

		return response;
	}

	/**
	 * Method to get all messages having creation time greater or equal to
	 * provided time in request
	 * 
	 * @param timeStamp
	 * 
	 * @return GetMessagesResponse
	 */
	@Override
	@Transactional
	public GetMessagesResponse getMessageDelta(Long timeStamp) {
		if (timeStamp == null) {
			throw new PushMessagingException(ErrorCode.INVALID_PARAMETER,
					new String[] { ErrorCode.INVALID_PARAMETER.getCode() });
		}

		GetMessagesResponse response = new GetMessagesResponse();
		List<Messages> tmpMessages = pushmsgRepository.findByCreationDatetime(BigInteger.valueOf(timeStamp / 1000));

		com.acn.avs.push.messaging.model.Messages messages = new com.acn.avs.push.messaging.model.Messages();
		List<Message> msgList = messages.getMessage();
		populateMessageDetails(msgList, tmpMessages);
		response.setMessages(messages);

		return response;
	}

	/**
	 * Get all message for document generation
	 * 
	 * @return GetMessagesResponse
	 */
	@Override
	@Transactional
	public GetMessagesResponse getMessageDocument() {
		long currentEpoc = new Date().getTime() / 1000;
		List<Messages> messageListDb = customMessagingRepository
				.getAllActiveMulticastMessages(configurationProperties.getMsgInclusionTimeInHours(), currentEpoc);
		GetMessagesResponse response = new GetMessagesResponse();
		com.acn.avs.push.messaging.model.Messages messages = new com.acn.avs.push.messaging.model.Messages();
		response.setMessages(messages);
		populateMessageDetails(messages.getMessage(), messageListDb);

		return response;

	}

	/**
	 * @param msgDetailList
	 * @param tmpMessages
	 */
	private void populateMessageDetails(List<Message> msgDetailList, List<Messages> tmpMessagesDb) {
		for (Iterator<Messages> iterator = tmpMessagesDb.iterator(); iterator.hasNext();) {
			Messages tempMsgDb = iterator.next();
			Message msg = new Message();

			Header header = new Header();
			header.setMessageId(tempMsgDb.getMessageId().intValue());
			header.setMessageName(tempMsgDb.getMessageName());

			header.setDistributionMode(getMsgDistributionMode(tempMsgDb.getMultiCast(), tempMsgDb.getFiltersList()));

			header.setIsPopupMessage(tempMsgDb.getPopupMessage());
			header.setMessageType(Header.MessageType.fromValue(tempMsgDb.getMessageType()));
			header.setCreationDateTime(tempMsgDb.getDisplayDatetime() == null ? null
					: formatDate(tempMsgDb.getCreationDatetime().longValue()));
			header.setDisplayDateTime(tempMsgDb.getDisplayDatetime() == null ? null
					: formatDate(tempMsgDb.getDisplayDatetime().longValue()));
			header.setValidityDateTime(tempMsgDb.getExpirationDatetime() == null ? null
					: formatDate(tempMsgDb.getExpirationDatetime().longValue()));
			header.setAutoHideTime(tempMsgDb.getAutohideTime() == null ? null : tempMsgDb.getAutohideTime().intValue());
			header.setStatus(tempMsgDb.getStatus() == null || tempMsgDb.getStatus() == false ? Constants.INACTIVE
					: Constants.ACTIVE);

			msg.setHeader(header);

			Body body = new Body();
			body.setMessageURL(tempMsgDb.getMessageUrl());
			body.setMessageText(tempMsgDb.getMessageText());
			body.setMessagePopupText(tempMsgDb.getPopupMessageText());
			msg.setBody(body);

			msg.setSubscriberId(tempMsgDb.getSubscriberId());

			populateFilterDetails(msg, tempMsgDb.getFiltersList());
			populateMessageAssociationDetails(msg, tempMsgDb.getStbAddressingList());

			msgDetailList.add(msg);
		}
	}

	/**
	 * Populate Message filters details
	 * 
	 * @param msg
	 * @param tempFilters
	 */
	private void populateFilterDetails(Message msg, List<Filters> tempFiltersDb) {

		if (CollectionUtils.isNotEmpty(tempFiltersDb)) {
			for (Filters tmpFilter : tempFiltersDb) {
				Filter filter = new Filter();
				filter.setIPAddress(tmpFilter.getIpAddress());
				filter.setPackageID(tmpFilter.getPackageId());
				filter.setWatchedChannelID(tmpFilter.getWatchedChannelId());
				filter.setLocationID(tmpFilter.getLocationId());
				filter.setSWVersion(tmpFilter.getSoftwareVersion());
				filter.setHWVersion(tmpFilter.getHardwareVersion());
				filter.setUILanguage(tmpFilter.getCurrentUiLanguage());
				filter.setFIPS(tmpFilter.getFipsCode());
				msg.getFilter().add(filter);
			}
		} else {
			msg.setFilter(null);
		}
	}

	/**
	 * Populate Message association details
	 * 
	 * @param Message
	 * @param tempStbAddressing
	 */
	private void populateMessageAssociationDetails(Message msg, List<StbAddressing> tempStbAddressing) {
		if (CollectionUtils.isNotEmpty(tempStbAddressing)) {
			for (StbAddressing tmpStbAddressing : tempStbAddressing) {
				Stbs stbs = new Stbs();
				List<Stb> stbList = stbs.getStb();
				Stb stb = new Stb();
				stb.setMACAddress(tmpStbAddressing.getMacAddress());
				stbs.setStb(stbList);
				msg.setStbs(stbs);
			}
		}
	}

	/**
	 * 
	 * @param epochTime
	 * @return
	 */
	private String formatDate(long epochTime) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATEFORMAT);
		return dateFormat.format(new Date(epochTime * 1000));
	}

	/**
	 * Get Message Distribution Mode
	 * 
	 * @param isMulticast
	 * @param filters
	 * 
	 * @return Header.DistributionMode
	 */
	private Header.DistributionMode getMsgDistributionMode(boolean isMulticast, List<Filters> filters) {
		if (isMulticast) {
			if (CollectionUtils.isEmpty(filters)) {
				return Header.DistributionMode.BROADCAST;
			}
			return Header.DistributionMode.MULTICAST;
		} else {
			return Header.DistributionMode.UNICAST;
		}

	}

	/**
	 * Get message List Distribution Mode
	 * 
	 * @param isMulticast
	 * @param filters
	 * 
	 * @return MessageList.DistributionMode
	 */
	private MessageList.DistributionMode getMsgListDistributionMode(boolean isMulticast, List<Filters> filters) {
		if (isMulticast) {
			if (CollectionUtils.isEmpty(filters)) {
				return MessageList.DistributionMode.BROADCAST;
			}
			return MessageList.DistributionMode.MULTICAST;
		} else {
			return MessageList.DistributionMode.UNICAST;
		}

	}

	/**
	 * Method to check if message type is valid
	 * 
	 * @param messageType
	 * @return
	 */
	private boolean isValidMessageType(String messageType) {
		boolean valid = false;
		for (MessageTypeEnum msgType : MessageTypeEnum.values()) {
			if (msgType.getValue().equals(messageType)) {
				valid = true;
				break;
			}
		}
		return valid;
	}

	/**
	 * Method to check if the provided distribution mode is valid
	 * 
	 * @param mode
	 * @return
	 */
	private boolean isValidDistributionMode(String mode) {
		boolean valid = false;
		for (DistributionModes tmpMode : DistributionModes.values()) {
			if (tmpMode.getValue().equals(mode)) {
				valid = true;
				break;
			}
		}

		return valid;
	}

	/**
	 * Method to get correct index value for pagination
	 * 
	 * @param startIndex
	 * @param isPageSize
	 * 
	 * @return Integer
	 */
	private Integer getPageIndexValue(String startIndex, boolean isPageSize) {
		Integer indexValue;
		try {
			indexValue = Integer.parseInt(startIndex);
			if (indexValue < 0) {
				indexValue = isPageSize ? 100 : 0;
			}
		} catch (NumberFormatException exception) {
			indexValue = isPageSize ? 100 : 0;
		}

		return indexValue;
	}

}
