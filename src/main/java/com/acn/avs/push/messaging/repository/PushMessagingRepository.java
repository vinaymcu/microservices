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

/**
 * 
 */
package com.acn.avs.push.messaging.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.acn.avs.push.messaging.entity.Messages;

@Repository
public interface PushMessagingRepository extends JpaRepository<Messages, Long> {

	List<Messages> findAll();
	
	Long countByStatusAndMultiCast(Boolean status,Boolean multicast);

	@Query("SELECT msg FROM Messages msg WHERE msg.creationDatetime >= :creationDatetime")
	List<Messages> findByCreationDatetime(@Param("creationDatetime") BigInteger creationDatetime);

	Messages findByMessageId(Long messageId);

	@Query(value = "SELECT count(*) FROM Messages msg WHERE msg.messageId like :messageId", nativeQuery = true)
	int findMessageCountWithLike(@Param("messageId") String messageId);

	@Query(value = "SELECT * FROM Messages msg WHERE msg.messageId like :messageId AND msg.messageType like :messageType "
			+ "AND msg.multiCast like :multiCast ORDER BY msg.messageId asc LIMIT :startIndex, :pageSize", nativeQuery = true)
	List<Messages> getMessagesByTypeAndMode(@Param("messageId") String messageId,
			@Param("messageType") String messageType, @Param("multiCast") Boolean multiCast,
			@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);
}
