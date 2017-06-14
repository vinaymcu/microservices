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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.acn.avs.push.messaging.config.ConfigurationProperties;
import com.acn.avs.push.messaging.entity.EstimationProcessSchedule;
import com.acn.avs.push.messaging.repository.EstimationProcessScheduleRepository;
import com.acn.avs.push.messaging.service.PushMessagingEstimationServiceHelper;

/**
 * @author Happy.Dhingra
 *
 */
@RefreshScope
@Service
public class PushMessagingEstimationServiceHelperImpl implements PushMessagingEstimationServiceHelper {

	@Autowired
	ConfigurationProperties configurationProperties;

	@Autowired
	EstimationProcessScheduleRepository estimationProcessScheduleRepository;

	@Value("${spring.application.instance_id}")
	private String applicationInstanceId;

	@Value("${estimation.schedule.time.interval:60}")
	private long scheduleTimeInterval;

	/** COMPLETE */
	private static final String COMPLETE = "Complete";

	/** SCHEDULED */
	private static final String SCHEDULED = "Scheduled";

	/**
	 * Creates estimation process schedule.
	 * 
	 * @return EstimationProcessSchedule
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public EstimationProcessSchedule createEstimationProcessSchedule() {
		EstimationProcessSchedule estimationProcessSchedule = new EstimationProcessSchedule();
		estimationProcessSchedule.setScheduleTime(System.currentTimeMillis() + getScheduleInterval());
		estimationProcessSchedule.setStatus(COMPLETE);
		estimationProcessScheduleRepository.save(estimationProcessSchedule);
		return estimationProcessSchedule;
	}

	/**
	 * Updates estimation process schedule.
	 * 
	 * @param estimationProcessSchedule
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void updateEstimationProcessSchedule(EstimationProcessSchedule estimationProcessSchedule, String status) {

		if (SCHEDULED.equals(status)) {

			estimationProcessSchedule
					.setScheduleTime(estimationProcessSchedule.getScheduleTime() + getScheduleInterval());
		}

		estimationProcessSchedule.setStatus(status);
		estimationProcessScheduleRepository.save(estimationProcessSchedule);
	}

	@Override
	public long getScheduleInterval() {
		return configurationProperties.getBkpProcessTimeInSeconds() * 1000;
	}

	/**
	 * Gets application instance id.
	 * 
	 * @return String
	 */
	@Override
	public String getApplicationInstanceId() {

		return applicationInstanceId;
	}

	

}
