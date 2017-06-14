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

package com.acn.avs.push.messaging.scheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.acn.avs.push.messaging.client.DocumentServiceClient;
import com.acn.avs.push.messaging.config.ConfigurationProperties;
import com.acn.avs.push.messaging.entity.EstimationProcessSchedule;
import com.acn.avs.push.messaging.model.ResponseWrapper;
import com.acn.avs.push.messaging.repository.CustomPushMessagingRepository;
import com.acn.avs.push.messaging.repository.EstimationProcessScheduleRepository;
import com.acn.avs.push.messaging.service.PushMessagingEstimationServiceHelper;
import com.netflix.hystrix.exception.HystrixRuntimeException;

import feign.FeignException;

/**
 * @author Happy.Dhingra
 *
 */
@Component
@Scope("prototype")
public class EstimationProcessTask implements Runnable {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = Logger.getLogger(EstimationProcessTask.class);

	@Autowired
	EstimationProcessScheduleRepository estimationProcessScheduleRepository;

	@Autowired
	ConfigurationProperties configurationProperties;

	@Autowired
	CustomPushMessagingRepository customPushMessagingRepository;

	@Autowired
	PushMessagingEstimationServiceHelper pushMessagingEstimationServiceHelper;

	@Autowired
	DocumentServiceClient documentServiceClient;

	private static final String SUCCESS_RESPONSE_CODE = "ACN_200";

	/** SCHEDULED */
	private static final String SCHEDULED = "Scheduled";

	/** COMPLETE */
	private static final String COMPLETE = "Complete";

	@Override
	public void run() {
		EstimationProcessSchedule estimationProcessSchedule = estimationProcessScheduleRepository.findAll().get(0);

		pushMessagingEstimationServiceHelper.updateEstimationProcessSchedule(estimationProcessSchedule, SCHEDULED);
		try {
			process();
		} catch (Exception exception) {
			LOGGER.info("Exception in estimation process.", exception);
		}
		pushMessagingEstimationServiceHelper.updateEstimationProcessSchedule(estimationProcessSchedule, COMPLETE);

	}

	/**
	 * Process will delete the past messages from the Messages table and will
	 * invoke the Document Generator to generate the broker document.
	 * 
	 */
	private void process() {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("(+) process");
		}
		deleteExpiredMessages();
		sendMessage(configurationProperties.getMsgRetry());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("(-) process");
		}
	}

	/**
	 * Method to send notification to document generate to generate the message
	 * document
	 * 
	 * @param retry
	 */
	private void sendMessage(int retry) {
		try {
			ResponseEntity<ResponseWrapper> response = documentServiceClient
					.generateDocument(configurationProperties.getDocumentId() + "");

			if (!response.getBody().getResultObject().get(0).getResultCode().equals(SUCCESS_RESPONSE_CODE)) {
				LOGGER.error(response.getBody().getResultDescription() + "");
			}
		} catch (HystrixRuntimeException | FeignException exp) {
			if (retry != 0) {
				retry = retry - 1;
				sendMessage(retry);
			} else
				LOGGER.error(exp.getMessage(), exp);
		}

	}

	/**
	 * delete expired messages
	 * 
	 * @param id
	 * @return
	 */
	private void deleteExpiredMessages() {
		customPushMessagingRepository.deleteExpiredMessages();
	}

}
