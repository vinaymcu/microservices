package com.acn.avs.push.messaging.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author Happy.Dhingra
 *
 */
@Entity
@Table(name = "PUSH_MESSAGING_SERVICE_INSTANCE")
public class PushMessagingServiceInstance implements Serializable{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** instanceId */
	@Id
	@Column(name= "INSTANCE_ID", length = 100)
	private String instanceId;
	
	@Column(name= "INSTANCE_NAME", nullable = false, unique = true, length = 50)
	private String instanceName;
	
	/** primaryInstance */
	@Column(name= "PRIMARY_INSTANCE", nullable = false, precision = 1, scale = 0)
	private Long primaryInstance;

	/** lastHeartbeat */
	@Column(name= "LAST_HEARTBEAT", nullable = false, precision = 13, scale = 0)
	private Long lastHeartbeat;

	/**
	 * @return the instanceId
	 */
	public String getInstanceId() {
		return instanceId;
	}

	/**
	 * @param instanceId the instanceId to set
	 */
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	/**
	 * @return the instanceName
	 */
	public String getInstanceName() {
		return instanceName;
	}

	/**
	 * @param instanceName the instanceName to set
	 */
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	/**
	 * @return the primary
	 */
	public Long getPrimaryInstance() {
		return primaryInstance;
	}

	/**
	 * @param primaryInstance the primary to set
	 */
	public void setPrimaryInstance(Long primaryInstance) {
		this.primaryInstance = primaryInstance;
	}

	/**
	 * @return the lastHeartbeat
	 */
	public Long getLastHeartbeat() {
		return lastHeartbeat;
	}

	/**
	 * @param lastHeartbeat the lastHeartbeat to set
	 */
	public void setLastHeartbeat(Long lastHeartbeat) {
		this.lastHeartbeat = lastHeartbeat;
	}
}
