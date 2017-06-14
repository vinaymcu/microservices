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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.acn.avs.push.messaging.config.ConfigurationProperties;
import com.acn.avs.push.messaging.entity.Filters;
import com.acn.avs.push.messaging.entity.Messages;
import com.acn.avs.push.messaging.enums.ErrorCode;
import com.acn.avs.push.messaging.exception.PushMessagingException;
import com.acn.avs.push.messaging.helper.MessageValidator;
import com.acn.avs.push.messaging.model.Body;
import com.acn.avs.push.messaging.model.Filter;
import com.acn.avs.push.messaging.model.Header;
import com.acn.avs.push.messaging.model.MulticastMessage;
import com.acn.avs.push.messaging.model.MulticastMessageRequest;
import com.acn.avs.push.messaging.model.MulticastMessageResponse;
import com.acn.avs.push.messaging.model.ResultObject;
import com.acn.avs.push.messaging.repository.CustomPushMessagingRepository;
import com.acn.avs.push.messaging.repository.PushMessagingRepository;
import com.acn.avs.push.messaging.service.MultiBroadCastService;
import com.acn.avs.push.messaging.util.Constants;

/**
 * 
 * MultiBroadCastService for create ,update  multi/broad cast type messages
 *
 */
@Service
public class MultiBroadCastServiceImpl implements MultiBroadCastService {

	/** The logger instance */
	private static final Logger LOGGER = LoggerFactory.getLogger(MultiBroadCastServiceImpl.class);

	/** PushMessagingRepository instance */
	@Autowired
	private PushMessagingRepository messagesRepository;

	/** MessageValidator instance */
	@Autowired
	private MessageValidator messageValidator;

	/** CustomPushMessagingRepository */
	@Autowired
	private CustomPushMessagingRepository customRepository;

	/** ConfigurationProperties */
	@Autowired
	private ConfigurationProperties configProperties;

	/** MessageSourceAccessor */
	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

	/** constant */
	private static final String EMPTY = "";

	/**
	 * createMultiBroadCastMessage
	 * 
	 * @param mmRequest
	 * @return mmResponse of MulticastMessageResponse
	 */
	@Override
	public MulticastMessageResponse createMultiBroadCastMessage(MulticastMessageRequest mmRequest)
			throws PushMessagingException {
		LOGGER.info("+++++createMultiBroadCastMessage+++++ ");
		List<MulticastMessage> mmList = mmRequest.getMulticastMessages();
		messageValidator.validateMaximumMessages(mmList);

		MulticastMessageResponse mmResponse = new MulticastMessageResponse();
		List<ResultObject> rsList = mmResponse.getResultObject();

		for (MulticastMessage mm : mmList) {

			Header header = mm.getHeader();
			LOGGER.info(" MessageId from request[{}]", header.getMessageId());
			messageValidator.validateDistributionMode(header.getDistributionMode().value());
			Messages messages = null;

			boolean createFlag = true;
			ResultObject ro = null;
			if (header.getDistributionMode().name().equalsIgnoreCase(Constants.MULTICAST)) {
				LOGGER.info("Distibution mode is muticast and filter in request size is[{}]", mm.getFilter().size());
				if (mm.getFilter().size() > configProperties.getMaxNoFilters()) {
					ro = setErrorCode(mm, ErrorCode.MAXIMUM_3_FILTERS.getCode());
					createFlag = false;
				}
			}
			if(Constants.INACTIVE.equalsIgnoreCase(header.getStatus())){
				Messages msg = getMessages(mm.getHeader());
				
				if (msg != null && (!msg.getStatus() || msg.getActivationDatetime() == null)) {
					LOGGER.info("Status [{}]|| ActivationDatetime [{}]", msg.getStatus(), msg.getActivationDatetime());
					ro = setErrorCode(mm, ErrorCode.MESSAGE_ALREADY_INACTIVE.getCode());
					createFlag = false;
				}
			}
			if (Constants.ACTIVE.equalsIgnoreCase(header.getStatus())) {

				if (header.getDistributionMode().name().equalsIgnoreCase(Constants.MULTICAST)
						&& CollectionUtils.isEmpty(mm.getFilter())) {
					LOGGER.info("Staus Active && Distibution mode is 'muticast' and filters in request is Empty");
					ro = setErrorCode(mm, ErrorCode.FILTERS_MISSING.getCode());
					createFlag = false;
				}
				if (messagesRepository.countByStatusAndMultiCast(true, true) > configProperties
						.getMaxActiveMulticastMessages()) {
					LOGGER.info("Total active message for multi/broadcast message beyond the limit.");
					ro = setMaximumActiveErrorMessages(mm, ErrorCode.MAXIMUM_ACTIVATION_LIMIT.getCode());
					createFlag = false;
				}
			}
			messages = prepareMultiBroadcastMessage(mm);
			try {
				LOGGER.info("Messages is before save in DB [{}],createFlag [{}]", messages, createFlag);
				if (messages != null && createFlag) {
					Messages updateMessages = messagesRepository.save(messages);
					ro = setSuccessMessage(mm, updateMessages);
					LOGGER.info("Multi/Broadcast Messages created/update successfully");
				}
			} catch (Exception e) {
				ro = setExceptionMessage(mm, e);
			}
			rsList.add(ro);
		}
		LOGGER.info("-----createMultiBroadCastMessage----- ");
		return mmResponse;
	}

	/**
	 * updateMultiBroadCastMessage
	 * 
	 * @param mmRequest
	 * @return mmResponse of MulticastMessageResponse
	 */
	@Override
	public MulticastMessageResponse updateMultiBroadCastMessage(MulticastMessageRequest mmRequest)
			throws PushMessagingException {
		LOGGER.info("+++++updateMultiBroadCastMessage+++++ ");
		List<MulticastMessage> mmList = mmRequest.getMulticastMessages();

		MulticastMessageResponse mmResponse = new MulticastMessageResponse();
		List<ResultObject> rsList = mmResponse.getResultObject();
		messageValidator.validateMaximumMessages(mmList);

		processMultiBroadCastMessage(mmList, rsList);
		LOGGER.info("-----updateMultiBroadCastMessage----- ");
		return mmResponse;
	}

	/**
	 * process MultiBroadCastMessage
	 * @param mmList
	 * @param rsList
	 */
	private void processMultiBroadCastMessage(List<MulticastMessage> mmList, List<ResultObject> rsList) {
		for (MulticastMessage mm : mmList) {

			Header header = mm.getHeader();
			LOGGER.info(" MessageId from request[{}]", header.getMessageId());
			messageValidator.validateDistributionMode(header.getDistributionMode().value());
			Messages messages = null;
			ResultObject ro = null;
			boolean updateFlag = true;

			if (header.getDistributionMode().name().equalsIgnoreCase(Constants.MULTICAST)) {
				LOGGER.info("Distibution mode is muticast and filter in request size is[{}]", mm.getFilter().size());
				if (mm.getFilter().size() > configProperties.getMaxNoFilters()) {
					ro = setErrorCode(mm, ErrorCode.MAXIMUM_3_FILTERS.getCode());
					updateFlag = false;
				}
			}
			if (Constants.ACTIVE.equalsIgnoreCase(header.getStatus())) {

				if (header.getDistributionMode().name().equalsIgnoreCase(Constants.MULTICAST)
						&& CollectionUtils.isEmpty(mm.getFilter())) {
					LOGGER.info("Staus Active && Distibution mode is 'muticast' and filters in request is Empty");
					ro = setErrorCode(mm, ErrorCode.FILTERS_MISSING.getCode());
					updateFlag = false;
				}
				if (messagesRepository.countByStatusAndMultiCast(true, true) > configProperties
						.getMaxActiveMulticastMessages()) {
					LOGGER.info("Total active message for multi/broadcast message beyond the limit.");
					ro = setMaximumActiveErrorMessages(mm, ErrorCode.MAXIMUM_ACTIVATION_LIMIT.getCode());
					updateFlag = false;
				}
			}

			if (header.getMessageId() == null) {
				LOGGER.info("Message without message id in request body");
				ro = messageIdNotExist(mm, ErrorCode.REQUEST_VALIDATION_FAILED.getCode());
				updateFlag = false;
			} else {

				Messages msg = getMessages(header);
				if (msg == null) {
					ro = setErrorCode(mm, ErrorCode.MESSAGE_NOT_EXIST.getCode());
					updateFlag = false;
				} else
					if (msg.getStatus() || msg.getActivationDatetime() != null) {
						LOGGER.info("prepare new Message when Status is active or have been active in past");
						messages = prepareMessage(mm);
					} else {
						LOGGER.info("prepareUpdateMessage with message id get from db");
						messages = prepareUpdateMessage(mm, msg);
					}
			}
			try {
				LOGGER.info("Messages is before save in DB [{}],createFlag [{}]", messages, updateFlag);
				if (messages != null && updateFlag) {
					Messages updateMessages = messagesRepository.save(messages);
					ro = setSuccessMessage(mm, updateMessages);
					LOGGER.info("Multi/Broadcast Messages update successfully");
				}
			} catch (Exception e) {
				ro = setExceptionMessage(mm, e);
			}
			rsList.add(ro);
		}
	}

	/**
	 * 
	 * @param mm of MulticastMessage
	 * @return messages of Messages
	 */
	private Messages prepareMultiBroadcastMessage(MulticastMessage mm) {
		Messages messages;
		if (mm.getHeader().getMessageId() == null) {
			LOGGER.info("Message without 'MESSAGE ID' in request");
			messages = prepareMessage(mm);
		} else {
			Messages msg = getMessages(mm.getHeader());
			if (msg != null && (msg.getStatus() || msg.getActivationDatetime() != null)) {
				LOGGER.info("Status [{}]|| ActivationDatetime [{}]", msg.getStatus(), msg.getActivationDatetime());
				messages = prepareMessage(mm);
			} else {
				LOGGER.info("Message is not in DB so prepare new mesage and set ID");
				messages = prepareMessage(mm);
				messages.setMessageId(Long.valueOf(mm.getHeader().getMessageId()));
			}
		}
		return messages;
	}

	/**
	 * 
	 * @param header of Header
	 * @return  msg of Messages
	 */
	private Messages getMessages(Header header) {
		Messages msg = messagesRepository.findOne(Long.valueOf(header.getMessageId()));
		LOGGER.info("Message from DB for update [{}] for message Id [{}]", msg, header.getMessageId());
		return msg;
	}

	/**
	 * prepareMessage for multicast Messsage
	 * 
	 * @param multicastMessage of MulticastMessage
	 * @return messages of Messages
	 */
	private Messages prepareMessage(MulticastMessage multicastMessage) {
		Body body = multicastMessage.getBody();
		Header header = multicastMessage.getHeader();
		Messages messages = new Messages();
		prepareMessageDetails(body, header, messages);

		List<Filters> filtersList = getFilterList(multicastMessage, messages);
		LOGGER.info("DistributionMode for create[{}]", header.getDistributionMode().name());
		if (header.getDistributionMode().name().equalsIgnoreCase(Constants.MULTICAST)) {
			if (CollectionUtils.isNotEmpty(filtersList)) {
				messages.setFiltersList(filtersList);
			}
		}
		return messages;
	}

	/**
	 * PrepareMessageDetails
	 * 
	 * @param body
	 * @param header
	 * @param messages
	 */
	private void prepareMessageDetails(Body body, Header header, Messages messages) {

		messages.setMessageName(header.getMessageName());
		if (body.getMessageURL() != null) {
			messages.setMessageUrl(body.getMessageURL());
		}

		if (body.getMessageText() != null) {
			messages.setMessageText(body.getMessageText());
		}
		messages.setMessageType(header.getMessageType().value());
		messages.setPopupMessage(header.getIsPopupMessage());
		if (body.getMessagePopupText() != null) {
			messages.setPopupMessageText(body.getMessagePopupText());
		}
		messages.setAutohideTime(BigInteger.valueOf(header.getAutoHideTime()));
		messages.setMultiCast(Boolean.TRUE);
		if (header.getValidityDateTime() != null) {
			messages.setExpirationDatetime(messageValidator.setDateTimeInEpoc(header.getValidityDateTime()));
		}
		if (header.getDisplayDateTime() != null) {
			messages.setDisplayDatetime(messageValidator.setDateTimeInEpoc(header.getDisplayDateTime()));
		}

		if (header.getCreationDateTime() != null) {
			messages.setCreationDatetime(messageValidator.setDateTimeInEpoc(header.getCreationDateTime()));
		}
		if (Constants.ACTIVE.equalsIgnoreCase(header.getStatus())) {
			messages.setStatus(Boolean.TRUE);
		} else {
			messages.setStatus(Boolean.FALSE);
		}
	}

	/**
	 * prepare the list of Filter
	 * 
	 * @param mm
	 *            of MulticastMessage
	 * @param messages
	 *            of Messages
	 * @return list of filters
	 */
	private List<Filters> getFilterList(MulticastMessage mm, Messages messages) {
		Filters filters = null;
		List<Filters> filtersList = new ArrayList<>();
		for (Filter f : mm.getFilter()) {

			filters = new Filters();
			filters.setIpAddress(f.getIPAddress());
			filters.setPackageId(f.getPackageID());
			filters.setWatchedChannelId(f.getWatchedChannelID());
			filters.setLocationId(f.getLocationID());
			filters.setSoftwareVersion(f.getSWVersion());
			filters.setHardwareVersion(f.getHWVersion());
			filters.setUiVersion(f.getUIVersion());
			filters.setCurrentUiLanguage(f.getUILanguage());
			filters.setFipsCode(f.getFIPS());
			filters.setMessage(messages);
			filtersList.add(filters);
		}
		return filtersList;
	}

	/**
	 * prepareUpdateMessage
	 * 
	 * @param multicastMessage
	 * @param messages
	 * @return messages
	 */
	private Messages prepareUpdateMessage(MulticastMessage multicastMessage, Messages messages) {
		Body body = multicastMessage.getBody();
		Header header = multicastMessage.getHeader();
		prepareMessageDetails(body, header, messages);

		if (Constants.ACTIVE.equalsIgnoreCase(header.getStatus())) {
			messages.setStatus(Boolean.TRUE);
		} else {
			messages.setStatus(Boolean.FALSE);
		}
		LOGGER.info("DistributionMode for update[{}]", header.getDistributionMode().name());
		if (header.getDistributionMode().name().equalsIgnoreCase(Constants.MULTICAST)) {
			if (CollectionUtils.isNotEmpty(multicastMessage.getFilter())) {
				List<Filters> filterList = getFilterList(multicastMessage, messages);
				customRepository.deleteFilterByMessageId(messages.getMessageId());
				messages.setFiltersList(filterList);
			}
		}
		return messages;
	}

	/**
	 * setExceptionMessage
	 * 
	 * @param mm
	 * @param e
	 * @return
	 */
	public ResultObject setExceptionMessage(MulticastMessage mm, Exception e) {
		ResultObject ro = new ResultObject();
		ro.setMulticastMessage(mm);
		ro.setResultCode(ErrorCode.GENERIC_ERROR.getCode());
		ro.setResultDescription(e.getMessage());

		return ro;
	}

	/**
	 * setSuccessMessage
	 * 
	 * @param mm
	 *            of MulticastMessage
	 * @param messages
	 * @return ResultObject
	 */
	public ResultObject setSuccessMessage(MulticastMessage mm, Messages messages) {
		ResultObject ro = new ResultObject();
		ro.setResultCode(ErrorCode.SUCCESS.getCode());
		ro.setResultDescription(messageSourceAccessor.getMessage(ErrorCode.SUCCESS.getCode()));
		ro.setId(messages.getMessageId() + EMPTY);
		mm.getHeader().setMessageId(messages.getMessageId().intValue());
		if (mm.getHeader().getDistributionMode().name().equalsIgnoreCase(Constants.BROADCAST)) {
			mm.setFilter(null);
			ro.setMulticastMessage(mm);
		} else {
			ro.setMulticastMessage(mm);
		}
		return ro;
	}

	/**
	 * setMaximumActiveErrorMessages
	 * 
	 * @param mm
	 *            of MulticastMessage
	 * @param errorCode
	 * @return ResultObject
	 */
	public ResultObject setMaximumActiveErrorMessages(MulticastMessage mm, String errorCode) {
		ResultObject ro = new ResultObject();
		ro.setId(mm.getHeader().getMessageId() + EMPTY);
		ro.setMulticastMessage(mm);
		ro.setResultCode(errorCode);
		ro.setResultDescription(messageSourceAccessor.getMessage(errorCode,
				new String[] { String.valueOf(configProperties.getMaxActiveMulticastMessages()),
						String.valueOf(configProperties.getMaxActiveMessage()) }));
		return ro;
	}

	/**
	 * setErrorCode
	 * 
	 * @param mm
	 *            of MulticastMessage
	 * @param errorCode
	 * @return ResultObject
	 */
	public ResultObject setErrorCode(MulticastMessage mm, String errorCode) {
		ResultObject ro = new ResultObject();
		ro.setId(mm.getHeader().getMessageId() + EMPTY);
		ro.setMulticastMessage(mm);
		ro.setResultCode(errorCode);
		ro.setResultDescription(messageSourceAccessor.getMessage(errorCode));
		return ro;
	}

	/**
	 * messageIdNotInRequest
	 * 
	 * @param mm
	 *            of MulticastMessage
	 * @param errorCode
	 * @return ResultObject
	 */
	private ResultObject messageIdNotExist(MulticastMessage mm, String errorCode) {
		ResultObject ro = new ResultObject();
		ro.setId(mm.getHeader().getMessageId() + EMPTY);
		ro.setMulticastMessage(mm);
		ro.setResultCode(ErrorCode.REQUEST_VALIDATION_FAILED.getCode());
		ro.setResultDescription(messageSourceAccessor.getMessage(ErrorCode.REQUEST_VALIDATION_FAILED.getCode(),
				new String[] { Constants.MESSAGE_ID_NOT_EXIST }));
		return ro;
	}
}
