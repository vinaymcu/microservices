package com.acn.avs.push.messaging.service.filter;

import com.acn.avs.push.messaging.model.GenericResponse;
import com.acn.avs.push.messaging.model.MulticastMessageFilters;
import com.acn.avs.push.messaging.model.UnicastMessageAssociationRequest;
import com.acn.avs.push.messaging.model.UnicastMessageAssociationResponse;

/**
 * @author santosh.sarkar
 *
 */

/**
 * The Interface PushMessagingFilterService.
 */
public interface PushMessagingFilterService {

	public UnicastMessageAssociationResponse getUnicastMessageAssociations(String messageId);

	public GenericResponse setUnicastMessageAssociations(String messageId, UnicastMessageAssociationRequest unicastMessageAssociationRequest);

	public MulticastMessageFilters getMulticastMessageFilters(String messageId);

	public GenericResponse setMulticastMessageFilters(String messageId,MulticastMessageFilters multicastMessageFilters );

}
