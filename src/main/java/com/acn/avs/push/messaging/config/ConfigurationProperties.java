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

package com.acn.avs.push.messaging.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import com.acn.avs.push.messaging.util.Constants;

/**
 * @author Happy.Dhingra
 *
 */
@Component
@RefreshScope
public class ConfigurationProperties {

	/** The LOGGER */
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationProperties.class);

	/**
	 * MAXIMUM ACTIVE MESSAGE
	 */
	@Value("${max.active.unicastMessages}")
	private String maxActiveMessage;

	/**
	 * MAX MESSAGES PER REQUEST
	 */
	@Value("${max.message.request}")
	private String maxMessagePerRequest;

	/**
	 * MAX MESSAGES PER REQUEST
	 */
	@Value("${pm.documentId:3}")
	private int documentId;

	/**
	 * bkpProcessTimeInterval
	 */
	@Value("${pm.backgroud.process.time.interval}")
	private String bkpProcessTimeInSeconds;

	/**
	 * msgInclusionTimeInterval
	 */
	@Value("${pm.message.inclusion.interval}")
	private String msgInclusionTimeInHours;

	/**
	 * msgInclusionTimeInterval
	 */
	@Value("${max.retry:3}")
	private int msgRetry;

	/** sslPort */
	@Value("${ssl.port}")
	private int sslPort;

	/** sslProtocol */
	@Value("${ssl.protocol}")
	private String sslProtocol;

	/** sslkeystorePassword */
	@Value("${ssl.keystore.password}")
	private String sslkeystorePassword;

	/** sslkeystoreAlias */
	@Value("${ssl.keystore.alias}")
	private String sslkeystoreAlias;

	/** sslkeystoreFilePath */
	@Value("${ssl.keystore.filepath}")
	private String sslkeystoreFilePath;

	/** MAXIMUM ACTIVE MULTICAST MESSAGE */
	@Value("${max.active.multicastMessages}")
	private String maxActiveMulticastMessages;

	/** MAXIMUM NO FILTERS */
	@Value("${max.no.filters}")
	private String  maxNoFilters;
	
	/**
	 * bkpProcessTimeInterval
	 */
	@Value("${pm.backgroud.hb.interval:30}")
	private String heartBeatInterval;

	/**
	 * @return the msgRetry
	 */
	public int getMsgRetry() {
		return msgRetry;
	}

	/**
	 * @return the maxActiveMessage
	 */
	public int getMaxActiveMessage() {
		int value = Constants.MAX_UNICAST_ACTIVE_MESSAGES;
		try {
			value = Integer.parseInt(maxActiveMessage);
			if (value <= 0) {
				value = Constants.MAX_UNICAST_ACTIVE_MESSAGES;
			}
		} catch (NumberFormatException exp) {
			LOGGER.warn("maxActiveMessage value [{}] is invalid, setting to default [{}]", maxActiveMessage,
					Constants.MAX_UNICAST_ACTIVE_MESSAGES);
		}
		return value;
	}

	/**
	 * @return the maxMessagePerRequest
	 */
	public int getMaxMessagePerRequest() {
		int value = Constants.MAX_MESSAGES_PER_REQUEST;
		try {
			value = Integer.parseInt(maxMessagePerRequest);
			if (value <= 0) {
				value = Constants.MAX_MESSAGES_PER_REQUEST;
			}
		} catch (NumberFormatException exp) {
			LOGGER.warn("maxMessagePerRequest value [{}] is invalid, setting to default [{}]", maxMessagePerRequest,
					Constants.MAX_MESSAGES_PER_REQUEST);
		}
		return value;
	}

	
	/**
	 * @return the MaxActiveMulticastMessages
	 */
	public int getMaxActiveMulticastMessages() {
		int value = Constants.MAX_MULTICAST_ACTIVE_MESSAGES;
		try {
			value = Integer.parseInt(maxActiveMulticastMessages);
			if (value <= 0) {
				value = Constants.MAX_MULTICAST_ACTIVE_MESSAGES;
			}
		} catch (NumberFormatException exp) {
			LOGGER.warn("maxActiveMulticastMessages value [{}] is invalid, setting to default [{}]", maxActiveMulticastMessages,
					Constants.MAX_MULTICAST_ACTIVE_MESSAGES);
		}
		return value;
	}
	
	/**
	 * @return max no Filter 
	 */
	public int getMaxNoFilters() {
		int value = Constants.MAX_FILTERS;
		try {
			value = Integer.parseInt(maxNoFilters);
			if (value <= 0) {
				value = Constants.MAX_FILTERS;
			}
		} catch (NumberFormatException exp) {
			LOGGER.warn("maxNoFilters value [{}] is invalid, setting to default [{}]", maxNoFilters,
					Constants.MAX_FILTERS);
		}
		return value;
	}
	
	
	/**
	 * @return the documentId
	 */
	public int getDocumentId() {
		return documentId;
	}

	/**
	 * @return the bkpProcessTimeInSeconds
	 */
	public int getBkpProcessTimeInSeconds() {
		int value = Constants.PM_BACKGROUD_PROCESS_TIME_INTERVAL;
		try {
			value = Integer.parseInt(bkpProcessTimeInSeconds);
			if (value <= 0) {
				value = Constants.PM_BACKGROUD_PROCESS_TIME_INTERVAL;
			}
		} catch (NumberFormatException exp) {
			LOGGER.warn("bkpProcessTimeInSeconds value [{}] is invalid, setting to default [{}]", bkpProcessTimeInSeconds,
					Constants.PM_BACKGROUD_PROCESS_TIME_INTERVAL);
		}
		return value;
	}

	/**
	 * @return the msgInclusionTimeInHours
	 */
	public int getMsgInclusionTimeInHours() {
		int value = Constants.PM_MESSAGE_INCLUSION_INTERVAL;
		try {
			value = Integer.parseInt(msgInclusionTimeInHours);
			if (value <= 0) {
				value = Constants.PM_MESSAGE_INCLUSION_INTERVAL;
			}
		} catch (NumberFormatException exp) {
			LOGGER.warn("msgInclusionTimeInHours value [{}] is invalid, setting to default [{}]", msgInclusionTimeInHours,
					Constants.PM_MESSAGE_INCLUSION_INTERVAL);
		}
		return value;
	}

	
	
	/**
	 * Gets the SSL port.
	 * 
	 * @return the sslPort
	 */
	public int getSslPort() {
		return sslPort;
	}

	/**
	 * Gets the SSL protocol.
	 * 
	 * @return the sslProtocol
	 */
	public String getSslProtocol() {
		return sslProtocol;
	}

	/**
	 * Gets the key store file password.
	 * 
	 * @return the sslkeystorePassword
	 */
	public String getSslKeystorePassword() {
		return sslkeystorePassword;
	}

	/**
	 * Gets alias of key store.
	 * 
	 * @return the sslkeystoreAlias
	 */
	public String getSslKeystoreAlias() {
		return sslkeystoreAlias;
	}

	/**
	 * Gets key store file path.
	 * 
	 * @return the sslkeystoreFilePath
	 */
	public String getSslKeystoreFilePath() {
		return sslkeystoreFilePath;
	}

	/**
	 * @return the heartBeatInterval
	 */
	public int getHeartBeatInterval() {
		if(getBkpProcessTimeInSeconds()/30==0)
		{
			return 1;
		}
		return getBkpProcessTimeInSeconds()/30;
	}

	
	
}
