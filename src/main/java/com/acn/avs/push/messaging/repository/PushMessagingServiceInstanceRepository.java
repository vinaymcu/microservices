package com.acn.avs.push.messaging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acn.avs.push.messaging.entity.PushMessagingServiceInstance;


/**
 * @author Happy.Dhingra
 *
 */
@Repository
public interface PushMessagingServiceInstanceRepository extends JpaRepository<PushMessagingServiceInstance, String> {

	/**
	 * Finds PushMessagingServiceInstance by primary value.
	 * 
	 * @param primaryInstance
	 * @return PushMessagingServiceInstance
	 */
	PushMessagingServiceInstance findByPrimaryInstance(Long primaryInstance);

}
