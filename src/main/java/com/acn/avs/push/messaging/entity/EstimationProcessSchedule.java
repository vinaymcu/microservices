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

package com.acn.avs.push.messaging.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author Happy.Dhingra
 *
 */
@Entity
@Table(name = "ESTIMATION_PROCESS_SCHEDULE")
public class EstimationProcessSchedule implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** scheduleId */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name= "SCHEDULE_ID", precision = 11, scale = 0)
	private Long scheduleId;
	
	/** scheduleTime */
	@Column(name= "SCHEDULED_TIME", nullable = false, precision = 13, scale = 0)
	private Long scheduleTime;
	
	/** status */
	@Column(name= "STATUS", nullable = false, length = 20)
	private String status;

	/**
	 * @return the scheduleId
	 */
	public Long getScheduleId() {
		return scheduleId;
	}

	/**
	 * @param scheduleId the scheduleId to set
	 */
	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	/**
	 * @return the scheduleTime
	 */
	public Long getScheduleTime() {
		return scheduleTime;
	}

	/**
	 * @param scheduleTime the scheduleTime to set
	 */
	public void setScheduleTime(Long scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
