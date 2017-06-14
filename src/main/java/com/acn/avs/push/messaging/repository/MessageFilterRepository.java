package com.acn.avs.push.messaging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.acn.avs.push.messaging.entity.Messages;

public interface MessageFilterRepository extends JpaRepository<Messages, Long> {
	
	@Transactional
	@Modifying
	@Query(" update Messages M set M.messageId =:messageId, M.subscriberId =:subscriberId  ")
	Integer updateUnicastAssociation();

}
