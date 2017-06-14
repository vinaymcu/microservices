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

package com.acn.avs.push.messaging.helper;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import com.acn.avs.push.messaging.config.ConfigurationProperties;
import com.acn.avs.push.messaging.entity.Messages;
import com.acn.avs.push.messaging.entity.StbAddressing;
import com.acn.avs.push.messaging.enums.ErrorCode;
import com.acn.avs.push.messaging.exception.PushMessagingException;
import com.acn.avs.push.messaging.model.Header;
import com.acn.avs.push.messaging.model.MessageIds;
import com.acn.avs.push.messaging.model.ResultObject;
import com.acn.avs.push.messaging.model.Stb;
import com.acn.avs.push.messaging.model.Stbs;
import com.acn.avs.push.messaging.model.UnicastMessage;
import com.acn.avs.push.messaging.model.UnicastMessageAssociationRequest;
import com.acn.avs.push.messaging.model.UnicastMessageRequest;
import com.acn.avs.push.messaging.util.Constants;
import com.acn.avs.push.messaging.util.JsonRequestValidator;

@Component
public class MessageValidator {

	/** * LOGGER */
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageValidator.class);

	@Autowired
	JsonRequestValidator jsonRequestValidator;

	@Autowired
	ConfigurationProperties configProperties;

	@Autowired
	MessageSourceAccessor messageSourceAccessor;

	private static final ThreadLocal<DateFormat> formatter = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("MM/dd/yyyy HH:mm");
		}
	};

	// message for Unicast Message
	public ResultObject generateResultObject(UnicastMessage unicastmessage, ErrorCode errorCode) {
		ResultObject resultObject = new ResultObject();
		resultObject.setId(unicastmessage.getHeader().getMessageId()!=null?Integer.toString(unicastmessage.getHeader().getMessageId()):null);
		resultObject.setResultCode(errorCode.getCode());
		resultObject.setResultDescription(messageSourceAccessor.getMessage(errorCode.getCode()));
		resultObject.setUnicastMessage(unicastmessage);
		return resultObject;
	}

	/**
	 * @param unicastmessage
	 * @param count
	 * @return ResultObject by validating association and active length when
	 *         status is active
	 */
	public ResultObject validateAssociationAndActiveLength(UnicastMessage unicastmessage, Long count) {
		Stbs stbs = unicastmessage.getStbs();
		ResultObject resultObject = null;
		if (count >= Long.valueOf(configProperties.getMaxActiveMessage()).longValue()) {
			resultObject = new ResultObject();
			resultObject.setId(unicastmessage.getHeader().getMessageId()!=null?Integer.toString(unicastmessage.getHeader().getMessageId()):null);
			resultObject.setResultCode(ErrorCode.MAXIMUM_ACTIVATION_LIMIT.getCode());
			resultObject
					.setResultDescription(
							messageSourceAccessor
									.getMessage(ErrorCode.MAXIMUM_ACTIVATION_LIMIT.getCode(),
											new String[] {
													Integer.toString(configProperties.getMaxActiveMulticastMessages()),
													Integer.toString(configProperties.getMaxActiveMessage()) }));
			resultObject.setUnicastMessage(unicastmessage);
		} else if (StringUtils.isEmpty(unicastmessage.getSubscriberId()) || stbs == null
				|| CollectionUtils.isEmpty(stbs.getStb())) {
			resultObject = new ResultObject();
			resultObject.setId(unicastmessage.getHeader().getMessageId()!=null?Integer.toString(unicastmessage.getHeader().getMessageId()):null);
			resultObject.setResultCode(ErrorCode.MISSING_ASSOCIATION.getCode());
			resultObject
					.setResultDescription(messageSourceAccessor.getMessage(ErrorCode.MISSING_ASSOCIATION.getCode()));
			resultObject.setUnicastMessage(unicastmessage);
		} else {
			for (Stb stb : stbs.getStb()) {
				if (stb.getMACAddress() == null) {
					resultObject = new ResultObject();
					resultObject.setId(unicastmessage.getHeader().getMessageId()!=null?Integer.toString(unicastmessage.getHeader().getMessageId()):null);
					resultObject.setResultCode(ErrorCode.MISSING_ASSOCIATION.getCode());
					resultObject.setResultDescription(
							messageSourceAccessor.getMessage(ErrorCode.MISSING_ASSOCIATION.getCode()));
					resultObject.setUnicastMessage(unicastmessage);
				}
			}
		}

		return resultObject;
	}

	/**
	 * @param message
	 * @param messageId
	 * @return ResultObject This methods validates message id and status of the
	 *         message object
	 */
	public ResultObject validateMessageIds(Messages message, Integer messageId) {
		ResultObject resultObject = null;
		if (message == null) {
			resultObject = new ResultObject();
			resultObject.setId(messageId!=null ? Integer.toString(messageId):null);
			resultObject.setResultCode(ErrorCode.MESSAGE_NOT_EXIST.getCode());
			resultObject.setResultDescription(messageSourceAccessor.getMessage(ErrorCode.MESSAGE_NOT_EXIST.getCode()));
		} else if (message.getStatus()) {
			resultObject = new ResultObject();
			resultObject.setId(messageId!=null ? Integer.toString(messageId):null);
			resultObject.setResultCode(ErrorCode.ACTIVE_CANT_DELETED.getCode());
			resultObject
					.setResultDescription(messageSourceAccessor.getMessage(ErrorCode.ACTIVE_CANT_DELETED.getCode()));
		}
		return resultObject;

	}

	/**
	 * @param message
	 * @param messageId
	 * @return ResultObject This methods validates unicast message for
	 *         activation
	 */
	public ResultObject validateActiveMessage(Messages message, Integer messageId, Long count) {
		ResultObject resultObject = null;
		if (!message.getMultiCast() && count >= Long.valueOf(configProperties.getMaxActiveMessage())) {
			resultObject = new ResultObject();
			resultObject.setId(messageId!=null ? Integer.toString(messageId):null);
			resultObject.setResultCode(ErrorCode.MAXIMUM_ACTIVATION_LIMIT.getCode());
			resultObject
					.setResultDescription(
							messageSourceAccessor
									.getMessage(ErrorCode.MAXIMUM_ACTIVATION_LIMIT.getCode(),
											new String[] {
													Integer.toString(configProperties.getMaxActiveMulticastMessages()),
													Integer.toString(configProperties.getMaxActiveMessage()) }));
		} else if (message.getMultiCast() && count >= Long.valueOf(configProperties.getMaxActiveMulticastMessages())) {
			resultObject = new ResultObject();
			resultObject.setId(messageId!=null ? Integer.toString(messageId):null);
			resultObject.setResultCode(ErrorCode.MAXIMUM_ACTIVATION_LIMIT.getCode());
			resultObject
					.setResultDescription(
							messageSourceAccessor
									.getMessage(ErrorCode.MAXIMUM_ACTIVATION_LIMIT.getCode(),
											new String[] {
													Integer.toString(configProperties.getMaxActiveMulticastMessages()),
													Integer.toString(configProperties.getMaxActiveMessage()) }));
		} else if (message.getStatus()) {
			resultObject = new ResultObject();
			resultObject.setId(messageId!=null ? Integer.toString(messageId):null);
			resultObject.setResultCode(ErrorCode.MESSAGE_ALREADY_ACTIVE.getCode());
			resultObject
					.setResultDescription(messageSourceAccessor.getMessage(ErrorCode.MESSAGE_ALREADY_ACTIVE.getCode()));
		} else if (message.getActivationDatetime() != null) {
			resultObject = new ResultObject();
			resultObject.setId(messageId!=null ? Integer.toString(messageId):null);
			resultObject.setResultCode(ErrorCode.MESSAGE_PREVIOUSLY_ACTIVE.getCode());
			resultObject.setResultDescription(
					messageSourceAccessor.getMessage(ErrorCode.MESSAGE_PREVIOUSLY_ACTIVE.getCode()));
		} else if (!message.getMultiCast() && (StringUtils.isEmpty(message.getSubscriberId())
				|| CollectionUtils.isEmpty(message.getStbAddressingList()))) {
			resultObject = new ResultObject();
			resultObject.setId(message.getMessageId()!=null ? Long.toString(message.getMessageId()):null);
			resultObject.setResultCode(ErrorCode.MISSING_ASSOCIATION.getCode());
			resultObject
					.setResultDescription(messageSourceAccessor.getMessage(ErrorCode.MISSING_ASSOCIATION.getCode()));
		} else if (!message.getMultiCast()) {
			for (StbAddressing stb : message.getStbAddressingList()) {
				if (StringUtils.isEmpty(stb.getMacAddress())) {
					resultObject = new ResultObject();
					resultObject.setId(message.getMessageId()!=null ? Long.toString(message.getMessageId()):null);
					resultObject.setResultCode(ErrorCode.MISSING_ASSOCIATION.getCode());
					resultObject.setResultDescription(
							messageSourceAccessor.getMessage(ErrorCode.MISSING_ASSOCIATION.getCode()));
				}
			}
		}

		return resultObject;
	}

	/**
	 * @param message
	 * @param messageId
	 * @return ResultObject This methods validates unicast message for
	 *         activation
	 */
	public ResultObject validateDeactivateMessage(Messages message, Integer messageId) {
		ResultObject resultObject = null;
		if (message == null) {
			resultObject = new ResultObject();
			resultObject.setId(messageId!=null ? Integer.toString(messageId):null);
			resultObject.setResultCode(ErrorCode.MESSAGE_NOT_EXIST.getCode());
			resultObject.setResultDescription(messageSourceAccessor.getMessage(ErrorCode.MESSAGE_NOT_EXIST.getCode()));
		} else if (!message.getStatus()) {
			resultObject = new ResultObject();
			resultObject.setId(messageId!=null ? Integer.toString(messageId):null);
			resultObject.setResultCode(ErrorCode.MESSAGE_ALREADY_INACTIVE.getCode());
			resultObject.setResultDescription(
					messageSourceAccessor.getMessage(ErrorCode.MESSAGE_ALREADY_INACTIVE.getCode()));
		}

		return resultObject;
	}

	/**
	 * @param unicastmessage
	 * @return ResultObject generate error response
	 */
	public void validateMandatoryParams(UnicastMessageRequest unicastMessageRequest) {
		for (UnicastMessage unicastmessage : unicastMessageRequest.getUnicastMessage()) {
			Header header = unicastmessage.getHeader();
			if (!Constants.UNICAST.equalsIgnoreCase(header.getDistributionMode().value())) {
				String errorMessage = messageSourceAccessor.getMessage(ErrorCode.INVALID_DISTRIBUTION_MODE.getCode(),
						new String[] { ErrorCode.INVALID_DISTRIBUTION_MODE.getCode() });
				LOGGER.error(errorMessage);
				throw new PushMessagingException(ErrorCode.INVALID_DISTRIBUTION_MODE,
						new String[] { ErrorCode.INVALID_DISTRIBUTION_MODE.getCode() });
			} else if (header.getDisplayDateTime() != null && !validateDateFormat(header.getDisplayDateTime())) {
				String errorMessage = messageSourceAccessor.getMessage(ErrorCode.INVALID_DISPLAY_DATE_FORMAT.getCode(),
						new String[] { ErrorCode.INVALID_DISPLAY_DATE_FORMAT.getCode() });
				LOGGER.error(errorMessage);
				throw new PushMessagingException(ErrorCode.INVALID_DISPLAY_DATE_FORMAT,
						new String[] { ErrorCode.INVALID_DISPLAY_DATE_FORMAT.getCode() });
			} else if (!validateDateFormat(header.getValidityDateTime())) {
				String errorMessage = messageSourceAccessor.getMessage(ErrorCode.INVALID_VALIDITY_DATE_FORMAT.getCode(),
						new String[] { ErrorCode.INVALID_VALIDITY_DATE_FORMAT.getCode() });
				LOGGER.error(errorMessage);
				throw new PushMessagingException(ErrorCode.INVALID_VALIDITY_DATE_FORMAT,
						new String[] { ErrorCode.INVALID_VALIDITY_DATE_FORMAT.getCode() });
			} else if (checkDateInPast(header.getValidityDateTime(), null)) {
				String errorMessage = messageSourceAccessor.getMessage(ErrorCode.VALIDITY_DATE_CANNOT_BE_PAST.getCode(),
						new String[] { ErrorCode.VALIDITY_DATE_CANNOT_BE_PAST.getCode() });
				LOGGER.error(errorMessage);
				throw new PushMessagingException(ErrorCode.VALIDITY_DATE_CANNOT_BE_PAST,
						new String[] { ErrorCode.VALIDITY_DATE_CANNOT_BE_PAST.getCode() });
			} else if (header.getDisplayDateTime() != null && checkDateInPast(header.getDisplayDateTime(), null)) {
				String errorMessage = messageSourceAccessor.getMessage(ErrorCode.DISPLAY_DATE_CANNOT_BE_PAST.getCode(),
						new String[] { ErrorCode.DISPLAY_DATE_CANNOT_BE_PAST.getCode() });
				LOGGER.error(errorMessage);
				throw new PushMessagingException(ErrorCode.DISPLAY_DATE_CANNOT_BE_PAST,
						new String[] { ErrorCode.DISPLAY_DATE_CANNOT_BE_PAST.getCode() });
			} else if (header.getDisplayDateTime() != null
					&& !checkDateInPast(header.getDisplayDateTime(), header.getValidityDateTime())) {
				String errorMessage = messageSourceAccessor.getMessage(
						ErrorCode.DISPLAY_DATE_MUST_BE_LESS_THAN_VALIDITY_DATE.getCode(),
						new String[] { ErrorCode.DISPLAY_DATE_MUST_BE_LESS_THAN_VALIDITY_DATE.getCode() });
				LOGGER.error(errorMessage);
				throw new PushMessagingException(ErrorCode.DISPLAY_DATE_MUST_BE_LESS_THAN_VALIDITY_DATE,
						new String[] { ErrorCode.DISPLAY_DATE_MUST_BE_LESS_THAN_VALIDITY_DATE.getCode() });
			}
		}
	}

	/**
	 * @param date1
	 * @param date2
	 * @return result true if date is not in past else result false
	 */
	public boolean checkDateInPast(String date1, String date2) {
		formatter.get().setLenient(false);
		Date parsedDate1 = null;
		Date parsedDate2 = null;
		try {
			if (date2 == null)
				parsedDate2 = new Date();
			else
				parsedDate2 = formatter.get().parse(date2);

			parsedDate1 = formatter.get().parse(date1);
		} catch (ParseException e) {
			return false;
		}
		if (parsedDate1.before(parsedDate2)) {
			return true;
		} else
			return false;
	}

	public boolean validateDateFormat(String dateToValdate) {
		formatter.get().setLenient(false);
		try {
			formatter.get().parse(dateToValdate);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public BigInteger setDateTimeInEpoc(String dateTime) {
		formatter.get().setLenient(false);
		Date date = null;
		try {
			if (dateTime == null) {
				date = new Date();
			} else
				date = formatter.get().parse(dateTime);
			return BigInteger.valueOf(date.getTime() / 1000);
		} catch (ParseException e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}

	public String convertEpocToDateTime(String epocTime) {
		formatter.get().setLenient(false);
		Date date = new Date(Long.valueOf(epocTime) * 1000);
		String createdate = formatter.get().format(date);
		return createdate;
	}

	/**
	 * This method calls validator to validate request parameters
	 * 
	 * @param request
	 * @param jsonFileName
	 */
	public <T> void validateRequestParameters(T request, String jsonFileName) {
		String requestValidationResult = jsonRequestValidator.validate(request, jsonFileName,
				Constants.SCHEMA_FOLDER_PATH);
		if (!requestValidationResult.isEmpty()) {
			throw new PushMessagingException(ErrorCode.REQUEST_VALIDATION_FAILED,
					new String[] { requestValidationResult });
		}

		else if ((request instanceof UnicastMessageRequest
				&& ((UnicastMessageRequest) request).getUnicastMessage().size() == 0)
				|| (request instanceof MessageIds && ((MessageIds) request).getMessageIds().size() == 0)
				|| (request instanceof UnicastMessageAssociationRequest && ((UnicastMessageAssociationRequest) request)
						.getUnicastMessageAssociations().getStbs().getStb().size() == 0)) {
			requestValidationResult = Constants.MISSING_REQUEST_MSG;
			throw new PushMessagingException(ErrorCode.REQUEST_VALIDATION_FAILED,
					new String[] { requestValidationResult });
		}

	}

	public <T> void validateMaximumMessages(List<T> mmList) {

		if (mmList.size() > configProperties.getMaxMessagePerRequest()) {
			String errorMessage = messageSourceAccessor.getMessage(ErrorCode.MAXIMUM_50_MESSAGES.getCode(),
					new String[] { Integer.toString(configProperties.getMaxMessagePerRequest()) });
			LOGGER.error(errorMessage);
			throw new PushMessagingException(ErrorCode.MAXIMUM_50_MESSAGES,
					new String[] { Integer.toString(configProperties.getMaxMessagePerRequest()) });
		}
	}

	public void validateDistributionMode(String mode) {
		LOGGER.info("DistributionMode for mode  [{}]", mode);

		if (!(mode.equalsIgnoreCase(Constants.MULTICAST) || mode.equalsIgnoreCase(Constants.BROADCAST))) {
			String errorMessage = messageSourceAccessor.getMessage(ErrorCode.INVALID_DISTRIBUTION_MODE.getCode(),
					new String[] { ErrorCode.INVALID_DISTRIBUTION_MODE.getCode() });
			LOGGER.error(errorMessage);
			throw new PushMessagingException(ErrorCode.INVALID_DISTRIBUTION_MODE,
					new String[] { ErrorCode.INVALID_DISTRIBUTION_MODE.getCode() });
		}
	}
}
