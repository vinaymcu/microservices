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
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.acn.avs.push.messaging.client.UnicastNotifierClient;
import com.acn.avs.push.messaging.config.ConfigurationProperties;
import com.acn.avs.push.messaging.entity.Messages;
import com.acn.avs.push.messaging.entity.StbAddressing;
import com.acn.avs.push.messaging.enums.ErrorCode;
import com.acn.avs.push.messaging.exception.PushMessagingException;
import com.acn.avs.push.messaging.helper.MessageValidator;
import com.acn.avs.push.messaging.model.Body;
import com.acn.avs.push.messaging.model.EventUpdate;
import com.acn.avs.push.messaging.model.EventUpdateRequest;
import com.acn.avs.push.messaging.model.GenericResponse;
import com.acn.avs.push.messaging.model.Header;
import com.acn.avs.push.messaging.model.MessageId;
import com.acn.avs.push.messaging.model.MessageIds;
import com.acn.avs.push.messaging.model.ResultObject;
import com.acn.avs.push.messaging.model.Stb;
import com.acn.avs.push.messaging.model.UnicastMessage;
import com.acn.avs.push.messaging.model.UnicastMessageRequest;
import com.acn.avs.push.messaging.model.UnicastMessageResponse;
import com.acn.avs.push.messaging.repository.CustomPushMessagingRepository;
import com.acn.avs.push.messaging.repository.PushMessagingRepository;
import com.acn.avs.push.messaging.service.UnicastService;
import com.acn.avs.push.messaging.util.Constants;
import com.netflix.hystrix.exception.HystrixRuntimeException;

import feign.FeignException;

/**
 * @author Happy.Dhingra
 *
 */
@Service
public class UnicastServiceImpl implements UnicastService {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UnicastServiceImpl.class);

	@Autowired
	private MessageValidator messageValidator;

	@Autowired
	private PushMessagingRepository messagesRepository;

	@Autowired
	private CustomPushMessagingRepository customRepository;

	@Autowired
	private ConfigurationProperties configurationProperties;

	@Autowired
	UnicastNotifierClient unicastNotifierClient;

	@Autowired
	MessageSourceAccessor messageSourceAccessor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.acn.avs.push.messaging.service.UnicastService#createUnicastMessage(
	 * com.acn.avs.push.messaging.model.UnicastMessageRequest)
	 */
	@Override
	public UnicastMessageResponse createUnicastMessage(UnicastMessageRequest unicastMessageRequest) {
		performValidation(unicastMessageRequest, Boolean.FALSE);
		UnicastMessageResponse unicastMessageResponse = new UnicastMessageResponse();
		List<ResultObject> responseList = unicastMessageResponse.getResultObject();
		for (UnicastMessage unicastMessage : unicastMessageRequest.getUnicastMessage()) {
			ResultObject resultObject = createUnicastMessage(unicastMessage);
			responseList.add(resultObject);
		}
		unicastMessageResponse.setResultObject(responseList);
		return unicastMessageResponse;
	}

	/**
	 * @param responseList
	 * @param unicastMessage
	 */
	@Transactional
	private ResultObject createUnicastMessage(UnicastMessage unicastMessage) {
		Messages messagedb = null;
		if (unicastMessage.getHeader().getMessageId() != null)
			messagedb = messagesRepository.findOne(Long.valueOf(unicastMessage.getHeader().getMessageId()));
		if (messagedb == null) {
			try {
				// when id is in the request param and not existing in DB
				Messages message = populateForCreate(unicastMessage);
				if (Constants.ACTIVE.equalsIgnoreCase(unicastMessage.getHeader().getStatus())) {
					ResultObject association = messageValidator.validateAssociationAndActiveLength(unicastMessage,
							messagesRepository.countByStatusAndMultiCast(Boolean.TRUE, Boolean.FALSE));
					if (association != null) {
						return association;
					}
					message.setActivationDatetime(messageValidator.setDateTimeInEpoc(null));
					message.setStatus(Boolean.TRUE);
				} else
					message.setStatus(Boolean.FALSE);
				Messages messageDB = messagesRepository.save(message);
				unicastMessage.getHeader().setCreationDateTime(
						messageValidator.convertEpocToDateTime(messageDB.getCreationDatetime().toString()));
				if (message.getStatus())
					sendNotificationToUnicast(messageDB);
				return messageValidator.generateResultObject(unicastMessage, ErrorCode.SUCCESS);

			} catch (DataAccessException e) {
				return messageValidator.generateResultObject(unicastMessage, ErrorCode.GENERIC_ERROR);
			}
		} else {

			/*
			 * when id is in the request param and message in db has status
			 * active or that is activated earlier
			 */
			if (messagedb.getStatus() || messagedb.getActivationDatetime() != null) {
				// creating new message
				unicastMessage.getHeader().setMessageId(null);
				Messages newMessage = populateForCreate(unicastMessage);
				if (Constants.ACTIVE.equalsIgnoreCase(unicastMessage.getHeader().getStatus())) {
					ResultObject association = messageValidator.validateAssociationAndActiveLength(unicastMessage,
							messagesRepository.countByStatusAndMultiCast(Boolean.TRUE, Boolean.FALSE));
					if (association != null) {
						return association;
					}
					newMessage.setActivationDatetime(messageValidator.setDateTimeInEpoc(null));
					newMessage.setStatus(Boolean.TRUE);
				} else
					newMessage.setStatus(Boolean.FALSE);
				try {
					Messages messageDB = messagesRepository.save(newMessage);
					unicastMessage.getHeader().setCreationDateTime(
							messageValidator.convertEpocToDateTime(messageDB.getCreationDatetime().toString()));
					if (messageDB.getStatus())
						sendNotificationToUnicast(messageDB);
					ResultObject ro = messageValidator.generateResultObject(unicastMessage, ErrorCode.SUCCESS);
					ro.setId(messageDB.getMessageId() == null ? null : messageDB.getMessageId().toString());
					return ro;
				} catch (DataAccessException exception) {
					return messageValidator.generateResultObject(unicastMessage, ErrorCode.GENERIC_ERROR);
				}
			} else {
				// when id is in the request param and message in db has
				// status inactive
				return messageValidator.generateResultObject(unicastMessage, ErrorCode.MESSAGE_EXIST);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.acn.avs.push.messaging.service.UnicastService#updateUnicastMessage(
	 * com.acn.avs.push.messaging.model.UnicastMessageRequest)
	 */
	@Override
	public UnicastMessageResponse updateUnicastMessage(UnicastMessageRequest unicastMessageRequest) {
		performValidation(unicastMessageRequest, Boolean.TRUE);
		UnicastMessageResponse unicastMessageResponse = new UnicastMessageResponse();
		List<ResultObject> responseList = unicastMessageResponse.getResultObject();

		for (UnicastMessage unicastMessage : unicastMessageRequest.getUnicastMessage()) {

			ResultObject ro = updateUnicastMessage(unicastMessage);
			responseList.add(ro);
		}
		unicastMessageResponse.setResultObject(responseList);
		return unicastMessageResponse;
	}

	/**
	 * @param responseList
	 * @param unicastMessage
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private ResultObject updateUnicastMessage(UnicastMessage unicastMessage) {
		ResultObject resultObject;
		Messages message = messagesRepository.findOne(Long.valueOf(unicastMessage.getHeader().getMessageId()));
		if (message == null) {
			resultObject = messageValidator.generateResultObject(unicastMessage, ErrorCode.MESSAGE_NOT_EXIST);
			return resultObject;
		}

		if (message.getStatus() || message.getActivationDatetime() != null) {
			// creating new message
			unicastMessage.getHeader().setMessageId(null);
			Messages newMessage = populateForCreate(unicastMessage);
			if (Constants.ACTIVE.equalsIgnoreCase(unicastMessage.getHeader().getStatus())) {
				ResultObject association = messageValidator.validateAssociationAndActiveLength(unicastMessage,
						messagesRepository.countByStatusAndMultiCast(Boolean.TRUE, Boolean.FALSE));
				if (association != null) {
					return association;
				}
				newMessage.setActivationDatetime(messageValidator.setDateTimeInEpoc(null));
				newMessage.setStatus(Boolean.TRUE);
			} else
				newMessage.setStatus(Boolean.FALSE);
			try {
				Messages messageDB = messagesRepository.save(newMessage);
				unicastMessage.getHeader().setCreationDateTime(
						messageValidator.convertEpocToDateTime(messageDB.getCreationDatetime().toString()));
				if (messageDB.getStatus())
					sendNotificationToUnicast(messageDB);
				ResultObject ro = messageValidator.generateResultObject(unicastMessage, ErrorCode.SUCCESS);
				ro.setId(messageDB.getMessageId() == null ? null : messageDB.getMessageId().toString());
				return ro;
			} catch (DataAccessException exception) {
				return messageValidator.generateResultObject(unicastMessage, ErrorCode.GENERIC_ERROR);
			}
		} else {
			// updating existing message
			try {
				Messages updatedMessage = populateForUpdate(unicastMessage, message);
				customRepository.deleteByMessageId(message.getMessageId());
				Messages messageDB = messagesRepository.save(updatedMessage);
				unicastMessage.getHeader().setCreationDateTime(
						messageValidator.convertEpocToDateTime(messageDB.getCreationDatetime().toString()));
				return messageValidator.generateResultObject(unicastMessage, ErrorCode.SUCCESS);
			} catch (DataAccessException e) {
				return messageValidator.generateResultObject(unicastMessage, ErrorCode.GENERIC_ERROR);
			}
		}
	}

	/**
	 * @param unicastMessage
	 *            populateForcreate for setting all the values in Message object
	 *            and returns the object to the calling object
	 * @return
	 */
	private Messages populateForCreate(UnicastMessage unicastMessage) {
		Header header = unicastMessage.getHeader();
		LOGGER.info("DATA HEADER are {},{},{},{},{},{} >>", header.getMessageId(), header.getMessageName(),
				header.getIsPopupMessage(), header.getMessageType(), header.getDistributionMode(), header.getStatus());
		Body body = unicastMessage.getBody();
		Messages messages = new Messages();
		if (header.getMessageId() != null) {
			messages.setMessageId(Long.valueOf(header.getMessageId()));
		}
		messages.setMessageName(header.getMessageName());
		messages.setMultiCast(Boolean.FALSE);
		if (body != null) {
			messages.setMessageUrl(body.getMessageURL());
			messages.setMessageText(body.getMessageText());
			messages.setPopupMessageText(body.getMessagePopupText());
		}
		messages.setPopupMessage(header.getIsPopupMessage());

		messages.setMessageType(header.getMessageType().toString());
		messages.setDisplayDatetime(messageValidator.setDateTimeInEpoc(header.getDisplayDateTime()));
		messages.setCreationDatetime(messageValidator.setDateTimeInEpoc(null));
		messages.setExpirationDatetime(messageValidator.setDateTimeInEpoc(header.getValidityDateTime()));
		messages.setAutohideTime(BigInteger.valueOf(header.getAutoHideTime()));
		messages.setSubscriberId(unicastMessage.getSubscriberId());
		if (unicastMessage.getStbs() != null && !CollectionUtils.isEmpty(unicastMessage.getStbs().getStb())) {
			List<StbAddressing> stbAddressings = new ArrayList<>();
			for (Stb stb : unicastMessage.getStbs().getStb()) {
				StbAddressing stbAddressing = new StbAddressing();
				stbAddressing.setMacAddress(stb.getMACAddress());
				stbAddressing.setMessage(messages);
				stbAddressings.add(stbAddressing);
			}
			messages.setStbAddressingList(stbAddressings);
		}
		LOGGER.info("DATA Submitted >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return messages;
	}

	/**
	 * @param unicastMessage,
	 *            method populateForUpdate for setting all the values in Message
	 *            object and returns the object to the calling object
	 * @return Messages
	 */
	private Messages populateForUpdate(UnicastMessage unicastMessage, Messages messages) {
		Header header = unicastMessage.getHeader();
		LOGGER.info("DATA HEADER are {},{},{},{},{},{} >>", header.getMessageId(), header.getMessageName(),
				header.getIsPopupMessage(), header.getMessageType(), header.getDistributionMode(), header.getStatus());
		Body body = unicastMessage.getBody();
		if (header.getMessageId() != null) {
			messages.setMessageId(Long.valueOf(header.getMessageId()));
		}
		messages.setMessageName(header.getMessageName());
		if (body != null) {
			messages.setMessageUrl(body.getMessageURL());
			messages.setMessageText(body.getMessageText());
			messages.setPopupMessageText(body.getMessagePopupText());
		}
		messages.setPopupMessage(header.getIsPopupMessage());

		messages.setMessageType(header.getMessageType().toString());
		messages.setDisplayDatetime(messageValidator.setDateTimeInEpoc(header.getDisplayDateTime()));
		messages.setCreationDatetime(messageValidator.setDateTimeInEpoc(null));
		messages.setExpirationDatetime(messageValidator.setDateTimeInEpoc(header.getValidityDateTime()));
		messages.setAutohideTime(BigInteger.valueOf(header.getAutoHideTime()));
		messages.setSubscriberId(unicastMessage.getSubscriberId());
		if (unicastMessage.getStbs() != null && CollectionUtils.isEmpty(unicastMessage.getStbs().getStb())) {
			List<StbAddressing> stbAddressings = new ArrayList<>();
			for (Stb stb : unicastMessage.getStbs().getStb()) {
				StbAddressing stbAddressing = new StbAddressing();
				stbAddressing.setMacAddress(stb.getMACAddress());
				stbAddressing.setMessage(messages);
				stbAddressings.add(stbAddressing);
			}
			messages.setStbAddressingList(stbAddressings);
		}
		LOGGER.info("DATA Submitted >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return messages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.acn.avs.push.messaging.service.UnicastService#deleteUnicastMessage(
	 * com.acn.avs.push.messaging.model.MessageIds) deletes the message object
	 * from the database
	 */
	@Override
	@Transactional
	public GenericResponse deleteMessage(MessageIds messageIds) {
		validateMessageIds(messageIds);
		GenericResponse genericResponse = new GenericResponse();
		List<ResultObject> responseList = genericResponse.getResultObject();
		for (MessageId messageId : messageIds.getMessageIds()) {
			Messages message = messagesRepository.findOne(Long.valueOf(messageId.getMessageId()));
			ResultObject validationResult = messageValidator.validateMessageIds(message, messageId.getMessageId());
			if (validationResult != null) {
				responseList.add(validationResult);
				continue;
			}
			messagesRepository.delete(message);
			validationResult = new ResultObject();
			validationResult.setId(messageId.getMessageId() == null ? null : messageId.getMessageId().toString());
			validationResult.setResultCode(ErrorCode.SUCCESS.getCode());
			validationResult.setResultDescription(messageSourceAccessor.getMessage(ErrorCode.SUCCESS.getCode()));
			responseList.add(validationResult);
		}
		genericResponse.setResultObject(responseList);
		return genericResponse;
	}

	@Override
	@Transactional
	public GenericResponse activateMessage(MessageIds messageIds) {
		validateMessageIds(messageIds);
		GenericResponse genericResponse = new GenericResponse();
		List<ResultObject> responseList = genericResponse.getResultObject();
		for (MessageId messageId : messageIds.getMessageIds()) {
			Messages message = messagesRepository.findOne(Long.valueOf(messageId.getMessageId()));
			ResultObject validationResult = null;
			if (message == null) {
				validationResult = new ResultObject();
				validationResult.setId(messageId.toString());
				validationResult.setResultCode(ErrorCode.MESSAGE_NOT_EXIST.getCode());
				validationResult
						.setResultDescription(messageSourceAccessor.getMessage(ErrorCode.MESSAGE_NOT_EXIST.getCode()));
			} else {
				validationResult = messageValidator.validateActiveMessage(message, messageId.getMessageId(),
						messagesRepository.countByStatusAndMultiCast(Boolean.TRUE, message.getMultiCast()));
			}
			if (validationResult != null) {
				responseList.add(validationResult);
				continue;
			}
			message.setStatus(Boolean.TRUE);
			message.setActivationDatetime(messageValidator.setDateTimeInEpoc(null));
			messagesRepository.save(message);
			sendNotificationToUnicast(message);
			validationResult = new ResultObject();
			validationResult.setId(messageId.getMessageId() == null ? null : messageId.getMessageId().toString());
			validationResult.setResultCode(ErrorCode.SUCCESS.getCode());
			validationResult.setResultDescription(messageSourceAccessor.getMessage(ErrorCode.SUCCESS.getCode()));
			responseList.add(validationResult);
		}
		genericResponse.setResultObject(responseList);
		return genericResponse;
	}

	@Override
	@Transactional
	public GenericResponse deactivateMessage(MessageIds messageIds) {
		validateMessageIds(messageIds);
		GenericResponse genericResponse = new GenericResponse();
		List<ResultObject> responseList = genericResponse.getResultObject();
		for (MessageId messageId : messageIds.getMessageIds()) {
			Messages message = messagesRepository.findOne(Long.valueOf(messageId.getMessageId()));
			ResultObject validationResult = messageValidator.validateDeactivateMessage(message,
					messageId.getMessageId());
			if (validationResult != null) {
				responseList.add(validationResult);
				continue;
			}
			message.setStatus(Boolean.FALSE);
			messagesRepository.save(message);
			sendNotificationToUnicast(message);
			validationResult = new ResultObject();
			validationResult.setId(messageId.getMessageId() == null ? null : messageId.getMessageId().toString());
			validationResult.setResultCode(ErrorCode.SUCCESS.getCode());
			validationResult.setResultDescription(messageSourceAccessor.getMessage(ErrorCode.SUCCESS.getCode()));
			responseList.add(validationResult);
		}
		genericResponse.setResultObject(responseList);
		return genericResponse;
	}

	/*@Override
	@Transactional
	public UnicastMessageAssociationResponse getUnicastAssociation(String messageIdReq) {
		Long messageId = parseStringToLong(messageIdReq);
		UnicastMessageAssociationResponse unicastMessageAssociationResponse = new UnicastMessageAssociationResponse();
		List<UnicastMessageAssociation> unicastMessageAssociations = new ArrayList<>();
		Messages message = messagesRepository.findOne(messageId);
		Stbs stbs = new Stbs();
		List<Stb> stbList = new ArrayList<>();
		if (message == null) {
			throw new PushMessagingException(ErrorCode.MESSAGE_NOT_EXIST,
					new String[] { ErrorCode.MESSAGE_NOT_EXIST.getCode() });
		}
		UnicastMessageAssociation unicastMessageAssociation = new UnicastMessageAssociation();
		unicastMessageAssociation.setSubscriberId(message.getSubscriberId());
		for (StbAddressing stbAddressing : message.getStbAddressingList()) {
			Stb stb = new Stb();
			stb.setMACAddress(stbAddressing.getMacAddress());
			stbList.add(stb);
		}
		stbs.setStb(stbList);
		unicastMessageAssociation.setStbs(stbs);
		unicastMessageAssociations.add(unicastMessageAssociation);
		unicastMessageAssociationResponse.setUnicastMessageAssociationsList(unicastMessageAssociations);
		return unicastMessageAssociationResponse;
	}*/

	/*@Override
	public GenericResponse setUnicastAssociation(String messageIdReq,
			UnicastMessageAssociationRequest unicastMessageAssociationRequest) {
		Long messageId = parseStringToLong(messageIdReq);
		messageValidator.validateRequestParameters(unicastMessageAssociationRequest,
				Constants.UNICAST_MESSAGE_ASSOCIATION_FILENAME);
		GenericResponse genericResponse = new GenericResponse();
		Messages message = messagesRepository.findOne(messageId);
		if (message == null) {
			throw new PushMessagingException(ErrorCode.MESSAGE_NOT_EXIST,
					new String[] { ErrorCode.MESSAGE_NOT_EXIST.getCode() });
		}
		message.setSubscriberId(unicastMessageAssociationRequest.getUnicastMessageAssociations().getSubscriberId());
		List<StbAddressing> stbAddressingList = new ArrayList<>();
		for (Stb stb : unicastMessageAssociationRequest.getUnicastMessageAssociations().getStbs().getStb()) {
			StbAddressing stbAddressing = new StbAddressing();
			stbAddressing.setMacAddress(stb.getMACAddress());
			stbAddressing.setMessage(message);
			stbAddressingList.add(stbAddressing);
		}
		message.setStbAddressingList(stbAddressingList);
		customRepository.deleteByMessageId(message.getMessageId());
		messagesRepository.save(message);
		genericResponse.setResultCode(ErrorCode.SUCCESS.getCode());
		genericResponse.setResultDescription(messageSourceAccessor.getMessage(ErrorCode.SUCCESS.getCode()));
		genericResponse.setSystemTime(System.currentTimeMillis());

		return genericResponse;
	}*/

	public void performValidation(UnicastMessageRequest unicastMessageRequest, Boolean isUpdate) {
		messageValidator.validateRequestParameters(unicastMessageRequest, Constants.UNICAST_MESSAGE_FILENAME);
		messageValidator.validateMaximumMessages(unicastMessageRequest.getUnicastMessage());
		messageValidator.validateMandatoryParams(unicastMessageRequest);
		if (isUpdate) {
			for (UnicastMessage unicastMessage : unicastMessageRequest.getUnicastMessage()) {
				if (unicastMessage.getHeader().getMessageId() == null) {
					throw new PushMessagingException(ErrorCode.MISSING_PARAMETER,
							new String[] { Constants.MISSING_ID_MSG });
				}
			}
		}
	}

	public void validateMessageIds(MessageIds messageIds) {
		messageValidator.validateRequestParameters(messageIds, Constants.MESSAGE_IDS_FILENAME);
		messageValidator.validateMaximumMessages(messageIds.getMessageIds());
	}

	public Long parseStringToLong(String messageIdReq) {
		Long messageId = null;
		try {
			messageId = Long.valueOf(messageIdReq);
		} catch (NumberFormatException exception) {
			throw new PushMessagingException(ErrorCode.MESSAGE_NOT_EXIST,
					new String[] { ErrorCode.MESSAGE_NOT_EXIST.getCode() });
		}
		return messageId;
	}

	public void sendNotificationToUnicast(Messages message) {
		EventUpdateRequest eventUpdateRequest = new EventUpdateRequest();
		EventUpdate eventUpdate = new EventUpdate();
		List<String> macList = new ArrayList<String>();
		message.getStbAddressingList().forEach(stb -> macList.add(stb.getMacAddress()));
		eventUpdate.setMACAddress(macList);
		eventUpdate.setSubscriberId(message.getSubscriberId() == null ? null : message.getSubscriberId().toString());
		eventUpdate.setTriggerType(Constants.TRIGGER_TYPE);
		eventUpdate.setTimestamp((new Date().getTime() / 1000l) + "");
		eventUpdate.setTriggerInfo(message.getMessageId() == null ? null : message.getMessageId().toString());
		eventUpdateRequest.setEventUpdate(eventUpdate);
		sendMessage(eventUpdateRequest, configurationProperties.getMsgRetry());
	}

	private void sendMessage(EventUpdateRequest eventUpdateRequest, int retry) {

		int rty = retry;
		try {
			ResponseEntity<GenericResponse> response = unicastNotifierClient.notifyUnicastNotifier(eventUpdateRequest);
			if (!response.getBody().getResultCode().equals(ErrorCode.SUCCESS.getCode())) {
				LOGGER.error(response.getBody().getResultDescription() + "");
			}
		} catch (HystrixRuntimeException | FeignException exp) {
			if (rty == 0) {
				LOGGER.error(exp.getMessage());
			} else {
				rty = rty - 1;
				sendMessage(eventUpdateRequest, rty);
			}
		}
	}
}
