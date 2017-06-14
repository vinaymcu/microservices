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
package com.acn.avs.push.messaging.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author surendra.kumar
 *
 */
@Component
public class MessageSearchCriteriaMapper implements SearchCriteriaMapper {

	/** The map. */
	private Map<String, String> searchByMap;

	private Map<String, String> sortMap ;
	
	private static final String MESSAGENAME = "MessageName";

	/**
	 * The Init method.
	 */
	@PostConstruct
	public void init() {
		searchByMap = new HashMap<String, String>();
		searchByMap.put(MESSAGENAME, "messageName");
		searchByMap.put("CreationDateTime", "creationDatetime");
		searchByMap.put("ActivationDateTime", "activationDatetime");
		searchByMap.put("ExpirationDateTime", "expirationDatetime");
		
		
		sortMap = new HashMap<String, String>();
		sortMap.put("MessageName", "messageName");
		sortMap.put("CreationDateTime", "creationDatetime");
		sortMap.put("ActivationDateTime", "activationDatetime");
		sortMap.put("ExpirationDateTime", "expirationDatetime");
		
	}

	/*
	 * com.acn.avs.common.model.SearchCriteriaMapper#mapProperty(java.lang.
	 * String)
	 */
	@Override
	public String searchByMapProperty(String criteria) {
		String property = searchByMap.get(criteria);
		
		return property;
	}

	/**
	 * 
	 * @param criteria
	 * @return
	 */
	@Override
	public String sortMapProperty(String criteria) {
		String property = sortMap.get(criteria);
		if (StringUtils.isEmpty(property)) {
			property = sortMap.get(MESSAGENAME);
		}
		return property;

	}
}
