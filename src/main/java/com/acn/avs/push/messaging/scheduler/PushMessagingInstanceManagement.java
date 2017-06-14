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

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import com.acn.avs.push.messaging.config.ConfigurationProperties;
import com.acn.avs.push.messaging.entity.EstimationProcessSchedule;
import com.acn.avs.push.messaging.entity.PushMessagingServiceInstance;
import com.acn.avs.push.messaging.repository.EstimationProcessScheduleRepository;
import com.acn.avs.push.messaging.repository.PushMessagingServiceInstanceRepository;
import com.acn.avs.push.messaging.service.PushMessagingCreateUpdateServiceHelper;
import com.acn.avs.push.messaging.service.PushMessagingEstimationServiceHelper;
import com.acn.avs.push.messaging.util.SpringBeanUtils;

/**
 * @author Happy.Dhingra
 *
 */
@RefreshScope
@Component
public class PushMessagingInstanceManagement {

	/** The logger. */
	private static final Logger LOGGER = Logger.getLogger(PushMessagingInstanceManagement.class);

	@Autowired
	PushMessagingEstimationServiceHelper pushMessagingEstimationServiceHelper;

	@Autowired
	PushMessagingCreateUpdateServiceHelper pushMessagingCreateUpdateServiceHelper;

	@Autowired
	EstimationProcessScheduler estimationProcessScheduler;

	@Autowired
	EstimationProcessScheduleRepository estimationProcessScheduleRepository;

	@Autowired
	PushMessagingServiceInstanceRepository pushMessagingServiceInstanceRepository;

	@Autowired
	ConfigurationProperties configurationProperties;

	/**
	 * ScheduledExecutorService
	 */
	ScheduledExecutorService scheduledExecutorService;

	@Value("${estimation.instance.max.idle:120}")
	private long instanceMaxIdleStateValue;

	@Value("${estimation.heartbeat.check.interval:60000}")
	private long instanceHeatbeatCheckInterval;

	/** PRIMARY */
	private static final long PRIMARY = 1L;

	/** SECONDARY */
	private static final long SECONDARY = 0L;

	/**
	 * 
	 */
	@PostConstruct
	void initMethod() {
		initiateScheduler();
	}

	/**
	 * Initiate Scheduler with fixed delay
	 */
	public void initiateScheduler() {
		if (scheduledExecutorService == null || scheduledExecutorService.isShutdown()) {
			scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		} else {
			scheduledExecutorService.shutdownNow();
			scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		}
		scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {

			@Override
			public void run() {
				try {
					pushMessagingInstanceManagement();

				} catch (Exception e) {
					LOGGER.error("error occured while executing process", e);
				}
			}
		}, configurationProperties.getHeartBeatInterval(), configurationProperties.getHeartBeatInterval(),
				TimeUnit.SECONDS);

		LOGGER.info("(-)initiateScheduler");
	}

	/**
	 * This method does PushMessaging instance management.
	 * 
	 * @return boolean
	 */
	public void pushMessagingInstanceManagement() {

		String applicationInstanceId = pushMessagingEstimationServiceHelper.getApplicationInstanceId();

		removeIdleInstances();

		boolean isCurrentPMInstancePrimary = false;
		PushMessagingServiceInstance currentPushMessagingInstance = pushMessagingServiceInstanceRepository
				.findOne(applicationInstanceId);
		PushMessagingServiceInstance primaryPushMessagingServiceInstance = pushMessagingServiceInstanceRepository
				.findByPrimaryInstance(PRIMARY);
		List<EstimationProcessSchedule> estimationProcessSchedules = estimationProcessScheduleRepository.findAll();

		if (currentPushMessagingInstance == null) {
			if (primaryPushMessagingServiceInstance == null) {
				isCurrentPMInstancePrimary = true;
				currentPushMessagingInstance = pushMessagingCreateUpdateServiceHelper
						.createUpdatePMServiceInstanceByPrimaryInstance(PRIMARY);

				createInstanceAndScheduleTask(estimationProcessSchedules);
			} else {
				if (applicationInstanceId.equals(primaryPushMessagingServiceInstance.getInstanceId())) {
					currentPushMessagingInstance = pushMessagingCreateUpdateServiceHelper
							.createUpdatePMServiceInstanceByPrimaryInstance(PRIMARY);
				} else {
					currentPushMessagingInstance = pushMessagingCreateUpdateServiceHelper
							.createUpdatePMServiceInstanceByPrimaryInstance(SECONDARY);
				}
			}
		} else {

			if (primaryPushMessagingServiceInstance == null
					|| currentPushMessagingInstance.getPrimaryInstance().longValue() == PRIMARY) {
				isCurrentPMInstancePrimary = true;
				currentPushMessagingInstance = pushMessagingCreateUpdateServiceHelper
						.createUpdatePMServiceInstanceByPrimaryInstance(PRIMARY);

				if (primaryPushMessagingServiceInstance == null) {
					EstimationProcessSchedule estimationProcessSchedule = estimationProcessSchedules.get(0);
					scheduleEstimationProcess(estimationProcessSchedule, false);
				}
			} else {

				currentPushMessagingInstance = pushMessagingCreateUpdateServiceHelper
						.createUpdatePMServiceInstanceByPrimaryInstance(SECONDARY);
			}
		}

		LOGGER.info("Is current PM instance " + currentPushMessagingInstance.getInstanceName()
				+ " ready for estimation of members:: " + isCurrentPMInstancePrimary);
	}

	/**
	 * Creates instance and schedules task.
	 * 
	 * @param estimationProcessSchedules
	 */
	private void createInstanceAndScheduleTask(List<EstimationProcessSchedule> estimationProcessSchedules) {
		EstimationProcessSchedule estimationProcessSchedule = null;
		if (estimationProcessSchedules.isEmpty()) {
			estimationProcessSchedule = pushMessagingEstimationServiceHelper.createEstimationProcessSchedule();
			scheduleEstimationProcess(estimationProcessSchedule, false);
		} else {

			estimationProcessSchedule = estimationProcessSchedules.get(0);
			if (estimationProcessSchedule.getScheduleTime() < System.currentTimeMillis()) {
				estimationProcessSchedule.setScheduleTime(System.currentTimeMillis());
				estimationProcessScheduleRepository.save(estimationProcessSchedule);
				scheduleEstimationProcess(estimationProcessSchedule, true);
			} else {
				scheduleEstimationProcess(estimationProcessSchedule, false);
			}
		}
	}

	/**
	 * Removes idle instances.
	 */
	private void removeIdleInstances() {

		for (PushMessagingServiceInstance pushMessagingServiceInstance : pushMessagingServiceInstanceRepository.findAll()) {

			if (System.currentTimeMillis() - pushMessagingServiceInstance.getLastHeartbeat() > getInstanceMaxIdleStateValue()) {

				pushMessagingServiceInstanceRepository.delete(pushMessagingServiceInstance);
			}
		}
	}

	/**
	 * Schedules estimation process.
	 * 
	 * @param estimationProcessSchedule
	 * @param immediateCallReq
	 */
	private void scheduleEstimationProcess(EstimationProcessSchedule estimationProcessSchedule,
			boolean immediateCallReq) {
		Calendar currentTime = Calendar.getInstance();
		estimationProcessScheduler.cancelFutureSchedule();
		Calendar scheduleCalendarTime = Calendar.getInstance();
		scheduleCalendarTime.setTimeInMillis(
				immediateCallReq ? currentTime.getTimeInMillis() : estimationProcessSchedule.getScheduleTime());
		EstimationProcessTask estimationProcessTask = SpringBeanUtils.getBean(EstimationProcessTask.class);
		estimationProcessScheduler.schedule(estimationProcessTask, scheduleCalendarTime.getTime());
	}

	
	/**
	 * Gets instance maximum idle state value.
	 * 
	 * @return long
	 */
	private long getInstanceMaxIdleStateValue() {

		long idleStateValue = configurationProperties.getHeartBeatInterval() * 2000;
		return idleStateValue;
	}
}
