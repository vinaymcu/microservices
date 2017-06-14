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

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.acn.avs.push.messaging.entity.PushMessagingServiceInstance;
import com.acn.avs.push.messaging.repository.PushMessagingServiceInstanceRepository;
import com.acn.avs.push.messaging.service.PushMessagingCreateUpdateServiceHelper;
import com.acn.avs.push.messaging.service.PushMessagingEstimationServiceHelper;

/**
 * @author Happy.Dhingra
 *
 */
@RefreshScope
@Service
public class PushMessagingCreateUpdateServiceHelperImpl implements PushMessagingCreateUpdateServiceHelper {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(PushMessagingCreateUpdateServiceHelperImpl.class);

	@Autowired
	PushMessagingServiceInstanceRepository pushMessagingServiceInstanceRepository;

	@Autowired
	PushMessagingEstimationServiceHelper pushMessagingEstimationServiceHelper;

	/**
	 * Creates group service instance by primary instance value.
	 * 
	 * @param primaryInstance
	 * @return GroupServiceInstance
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public PushMessagingServiceInstance createUpdatePMServiceInstanceByPrimaryInstance(Long primaryInstance) {
		PushMessagingServiceInstance pushMessagingServiceInstance = new PushMessagingServiceInstance();
		pushMessagingServiceInstance.setInstanceId(pushMessagingEstimationServiceHelper.getApplicationInstanceId());
		try {
			pushMessagingServiceInstance.setInstanceName(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException exception) {

			LOGGER.info("Exception in getting host address.", exception);
		}

		pushMessagingServiceInstance.setPrimaryInstance(primaryInstance);
		pushMessagingServiceInstance.setLastHeartbeat(System.currentTimeMillis());
		return pushMessagingServiceInstanceRepository.save(pushMessagingServiceInstance);
	}

}
