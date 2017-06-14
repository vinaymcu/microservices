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

package com.acn.avs.push.messaging.repository.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.acn.avs.push.messaging.entity.Messages;
import com.acn.avs.push.messaging.repository.CustomPushMessagingRepository;
import com.acn.avs.push.messaging.tenant.TenantContext;
import com.acn.avs.push.messaging.tenant.Tenants;
import com.acn.avs.push.messaging.util.SearchCriteriaMapper;
import com.acn.avs.push.messaging.util.SearchFilter;

@Repository
public class CustomPushMessagingRepositoryImpl implements CustomPushMessagingRepository {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomPushMessagingRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private SearchCriteriaMapper criteriaMapperImpl;

	/*
	 * Method to delete message using provided message id
	 *
	 */
	@Override
	@Transactional
	public void deleteByMessageId(Long messageId) {
		TenantContext.setCurrentTenant(Tenants.WRITE);
		try {
			entityManager.createNamedQuery("DELETE_BY_MESSAGE_ID").setParameter("messageid", messageId).executeUpdate();
		} catch (NoResultException noResultException) {
			LOGGER.error("No result found for message id : [{}] ",messageId,noResultException);
		}

	}

	/**
	 * Method to search message using provided search cretria
	 * 
	 * @param SearchFilter
	 * @return List<Messages>
	 */
	@Override
	public List<Messages> search(SearchFilter filter) {
		final StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT M FROM Messages M ");
		filter.setMapper(criteriaMapperImpl);
		queryBuilder.append(filter.buildCompeleteCriteria("M"));
		LOGGER.info("Query to execute for get message list : " + queryBuilder.toString());
		final Query query = entityManager.createQuery(queryBuilder.toString());

		if (queryBuilder.indexOf("param") > 0) {
			query.setParameter("param", filter.getSearchValue());
		}

		if (filter.getStartIndex() != null) {
			query.setFirstResult(filter.getStartIndex());
		}
		if (filter.getPageSize() != null) {
			query.setMaxResults(filter.getPageSize());
		}
		return query.getResultList();
	}

	/**
	 * Method to get all active and valid messages whose display time is less
	 * than inclusion interval
	 * 
	 * @param int
	 * 
	 * @return List<Messages>
	 */
	@Override
	public List<Messages> getAllActiveMulticastMessages(int inclusionTimeInterval,long currentEpoch) {
		StringBuilder queryBuilder = new StringBuilder(
				"SELECT msg FROM Messages msg WHERE msg.expirationDatetime > "+currentEpoch);
		queryBuilder.append(" AND msg.status=1 AND msg.multiCast=1 ");
		queryBuilder.append("and (msg.immediately=1 or msg.displayDatetime < ");
		queryBuilder.append(currentEpoch + (inclusionTimeInterval * 60*60)).append(")");
		Query query = entityManager.createQuery(queryBuilder.toString());
		return query.getResultList();
	}

	/**
	 * Method to delete expired messages
	 * 
	 */
	@Override
	@Transactional
	public void deleteExpiredMessages() {
		TenantContext.setCurrentTenant(Tenants.WRITE);
		try {
			Query query = entityManager.createNamedQuery("DELETE_PAST_MESSAGES");
			query.setParameter("currTime", BigInteger.valueOf(new Date().getTime() / 1000));
			query.executeUpdate();
		} catch (NoResultException noResultException) {
			LOGGER.error("No result found exception",noResultException);
		}
	}

	@Override
	@Transactional
	public void deleteFilterByMessageId(Long messageId) {
		TenantContext.setCurrentTenant(Tenants.WRITE);
		try {
			Query query=entityManager.createNamedQuery("DELETE_FILTERS_BY_MESSAGE_ID");
			query.setParameter("messageid", messageId);
			query.executeUpdate();
		} catch (NoResultException noResultException) {
			LOGGER.error("No result found for message id : [{}] ",messageId,noResultException);
		}
	}
}
