package com.acn.avs.push.messaging.scheduler;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import com.acn.avs.push.messaging.service.PushMessagingEstimationServiceHelper;


/**
 * @author Happy.Dhingra
 *
 */
@Component
public class EstimationProcessScheduler {

	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;

	@Autowired
	private PushMessagingEstimationServiceHelper pushMessagingEstimationServiceHelper;

	/** future */
	private ScheduledFuture<?> future;

	/**
	 * Schedules task at fixed rate.
	 * 
	 * @param task
	 * @param startTime
	 */
	public void schedule(EstimationProcessTask task, Date startTime) {
		future = taskScheduler.scheduleAtFixedRate(task, startTime, pushMessagingEstimationServiceHelper.getScheduleInterval());
	}

	/**
	 * Cancels future schedule.
	 */
	public void cancelFutureSchedule() {
		if (future != null) {
			future.cancel(true);
		}
	}
}
