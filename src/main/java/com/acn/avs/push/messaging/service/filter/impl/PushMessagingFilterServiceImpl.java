package com.acn.avs.push.messaging.service.filter.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acn.avs.push.messaging.entity.Filters;
import com.acn.avs.push.messaging.entity.Messages;
import com.acn.avs.push.messaging.entity.StbAddressing;
import com.acn.avs.push.messaging.enums.ErrorCode;
import com.acn.avs.push.messaging.exception.PushMessagingException;
import com.acn.avs.push.messaging.helper.MessageValidator;
import com.acn.avs.push.messaging.model.Filter;
import com.acn.avs.push.messaging.model.GenericResponse;
import com.acn.avs.push.messaging.model.MulticastMessageFilter;
import com.acn.avs.push.messaging.model.MulticastMessageFilters;
import com.acn.avs.push.messaging.model.ResultObject;
import com.acn.avs.push.messaging.model.Stb;
import com.acn.avs.push.messaging.model.Stbs;
import com.acn.avs.push.messaging.model.UnicastMessageAssociation;
import com.acn.avs.push.messaging.model.UnicastMessageAssociationRequest;
import com.acn.avs.push.messaging.model.UnicastMessageAssociationResponse;
import com.acn.avs.push.messaging.repository.CustomPushMessagingRepository;
import com.acn.avs.push.messaging.repository.MessageFilterRepository;
import com.acn.avs.push.messaging.service.filter.PushMessagingFilterService;
import com.acn.avs.push.messaging.util.Constants;

/**
 * @author santosh.sarkar
 *
 */

/**
 * The Class PushMessagingFilterServiceImpl.
 */
@Service
public class PushMessagingFilterServiceImpl implements PushMessagingFilterService {

	/*
	 * @Override public GetMessageFiltersData fetchMessageFiltersData() {
	 *
	 * GetMessageFiltersData filterData = null; return filterData; }
	 *
	 */

	private static final Logger LOGGER = LoggerFactory.getLogger(PushMessagingFilterServiceImpl.class);

	@Autowired
	private MessageFilterRepository messageFilterRepository;

	@Autowired
	private CustomPushMessagingRepository customRepository;

	@Autowired
	private MessageValidator messageValidator;

	@Autowired
	private MessageSourceAccessor messageSource;

	@Override
	@Transactional
	public UnicastMessageAssociationResponse getUnicastMessageAssociations(String messageId) {

		LOGGER.info(" GET unicast message association implementation	..	");

		UnicastMessageAssociationResponse unicastMessageAssociationResponse = new UnicastMessageAssociationResponse();
		UnicastMessageAssociation unicastMessageAssociation = new UnicastMessageAssociation();
		List<UnicastMessageAssociation> unicastMessageAssociationsList = new ArrayList<>();
		Stbs stbs = new Stbs();
		// List<Stb> stbList = new ArrayList<>();
		try {
			Messages message = messageFilterRepository.findOne(Long.valueOf(messageId));
			if (message != null) {
				unicastMessageAssociation.setSubscriberId(message.getSubscriberId());
				stbs = messageIdToStbAssociation(message.getStbAddressingList());
				unicastMessageAssociation.setStbs(stbs);
				unicastMessageAssociationsList.add(unicastMessageAssociation);
				unicastMessageAssociationResponse.setUnicastMessageAssociationsList(unicastMessageAssociationsList);

			} else {
				throw new PushMessagingException(ErrorCode.MESSAGE_NOT_EXIST,
						new String[] { ErrorCode.MESSAGE_NOT_EXIST.getCode() });
			}

		} catch (PersistenceException exception) {
			throw new PushMessagingException(ErrorCode.GENERIC_ERROR,
					new String[] { ErrorCode.GENERIC_ERROR.getCode() });

		}

		return unicastMessageAssociationResponse;

	}

	@Override
	@Transactional
	public GenericResponse setUnicastMessageAssociations(String messageId,
			UnicastMessageAssociationRequest unicastMessageAssociationRequest) {

		LOGGER.info(" SET UNICAST message association ..	");

		/* ACN_3020 */
		messageValidator.validateRequestParameters(unicastMessageAssociationRequest,
				Constants.UNICAST_MESSAGE_ASSOCIATION_FILENAME);

		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setSystemTime(System.currentTimeMillis());

		Messages message = messageFilterRepository.findOne(Long.valueOf(messageId));

		/* ACN_9204 */
		if (message == null) {
			throw new PushMessagingException(ErrorCode.MESSAGE_NOT_EXIST,
					new String[] { ErrorCode.MESSAGE_NOT_EXIST.getCode() });
		} else {
			/* ACN_9206 */
			if (message.getStatus()) {
				throw new PushMessagingException(ErrorCode.MISSING_ASSOCIATION,
						new String[] { ErrorCode.MISSING_ASSOCIATION.getCode() });
			} else {
				customRepository.deleteByMessageId(message.getMessageId());
				message.setSubscriberId(
						unicastMessageAssociationRequest.getUnicastMessageAssociations().getSubscriberId());
				List<StbAddressing> stbAddressingList = new ArrayList<>();
				for (Stb stb : unicastMessageAssociationRequest.getUnicastMessageAssociations().getStbs().getStb()) {
					StbAddressing stbAddressing = new StbAddressing();
					stbAddressing.setMacAddress(stb.getMACAddress());
					stbAddressing.setMessage(message);

					stbAddressingList.add(stbAddressing);
				}
				message.setStbAddressingList(stbAddressingList);
				messageFilterRepository.save(message);

				ResultObject resultObject = new ResultObject();
				List<ResultObject> resultObjectsList = new ArrayList<>();

				resultObject.setId(messageId);
				resultObject.setResultCode(ErrorCode.SUCCESS.getCode());
				resultObject.setResultDescription(messageSource.getMessage(ErrorCode.SUCCESS.getCode()));
				resultObject
						.setUnicastMessageAssociation(unicastMessageAssociationRequest.getUnicastMessageAssociations());

				resultObjectsList.add(resultObject);
				genericResponse.setResultObject(resultObjectsList);

			}
		}
		return genericResponse;

	}

	private Stbs messageIdToStbAssociation(List<StbAddressing> stbaddr) {
		Stbs stbs = new Stbs();
		Stb stb = new Stb();
		List<Stb> stblist = new ArrayList<Stb>();

		for (StbAddressing addr : stbaddr) {
			stb.setMACAddress(addr.getMacAddress());
			stblist.add(stb);
		}
		stbs.setStb(stblist);
		return stbs;

	}

	/*
	 * private Boolean getStatus(String messageId) { Messages messages =
	 * messageFilterRepository.getOne(Long.valueOf(messageId)); return messages
	 * != null ? messages.getStatus() : null; }
	 *
	 */

	@Override
	@Transactional
	public MulticastMessageFilters getMulticastMessageFilters(String messageId) {

		LOGGER.info("	GET MULTICAST message ..	 ");

		MulticastMessageFilters multicastMessageFilters = new MulticastMessageFilters();
		Messages message = new Messages();

		message = messageFilterRepository.findOne(Long.valueOf(messageId));
		if (message == null) {
			throw new PushMessagingException(ErrorCode.MESSAGE_NOT_EXIST,
					new String[] { ErrorCode.MESSAGE_NOT_EXIST.getCode() });
		} else {
			MulticastMessageFilter multicastMessageFilter = new MulticastMessageFilter();
			List<Filter> filterList = new ArrayList<>();
			for (Filters filters : message.getFiltersList()) {
				Filter filter = new Filter();

				filter.setFIPS(filters.getFipsCode());
				filter.setHWVersion(filters.getHardwareVersion());
				filter.setIPAddress(filters.getIpAddress());
				filter.setLocationID(filters.getLocationId());
				filter.setPackageID(filters.getPackageId());
				filter.setSWVersion(filters.getSoftwareVersion());
				filter.setUILanguage(filters.getCurrentUiLanguage());
				filter.setUIVersion(filters.getUiVersion());
				filter.setWatchedChannelID(filters.getWatchedChannelId());

				filterList.add(filter);

			}
			multicastMessageFilter.setFilter(filterList);
			multicastMessageFilters.setMulticastMessageFilter(multicastMessageFilter);

		}
		return multicastMessageFilters;
	}

	@Override
	@Transactional
	public GenericResponse setMulticastMessageFilters(String messageId,
			MulticastMessageFilters multicastMessageFilters) {

		LOGGER.info("	SET MULTICAST message ..	 ");

		/* ACN_3020 */
		messageValidator.validateRequestParameters(multicastMessageFilters,
				Constants.MULTICAST_MESSAGE_FILTER_FILENAME);

		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setSystemTime(System.currentTimeMillis());

		ResultObject resultObject = new ResultObject();
		List<ResultObject> resultObjectsList = new ArrayList<>();

		Messages message = messageFilterRepository.findOne(Long.valueOf(messageId));

		/* ACN_9204 */
		if (message == null) {
			throw new PushMessagingException(ErrorCode.MESSAGE_NOT_EXIST,
					new String[] { ErrorCode.MESSAGE_NOT_EXIST.getCode() });
		} else {
			/* ACN_9206 */
			if (message.getStatus()) {
				throw new PushMessagingException(ErrorCode.MISSING_ASSOCIATION,
						new String[] { ErrorCode.MISSING_ASSOCIATION.getCode() });
			} else {
				/* ACN_9201 */
				if (multicastMessageFilters.getMulticastMessageFilter().getFilter().size() > 3) {
					throw new PushMessagingException(ErrorCode.MAXIMUM_3_FILTERS,
							new String[] { ErrorCode.MAXIMUM_3_FILTERS.getCode() });
				} else {
					customRepository.deleteFilterByMessageId(message.getMessageId());
					List<Filters> filterList = new ArrayList<>();
					for (Filter filter : multicastMessageFilters.getMulticastMessageFilter().getFilter()) {
						Filters filters = new Filters();
						filters.setFipsCode(filter.getFIPS());
						filters.setHardwareVersion(filter.getHWVersion());
						filters.setIpAddress(filter.getIPAddress());
						filters.setLocationId(filter.getLocationID());
						filters.setPackageId(filter.getPackageID());
						filters.setSoftwareVersion(filter.getSWVersion());
						filters.setUiVersion(filter.getUIVersion());
						filters.setCurrentUiLanguage(filter.getUILanguage());
						filters.setWatchedChannelId(filter.getWatchedChannelID());
						filters.setMessage(message);
						filterList.add(filters);
					}
					message.setFiltersList(filterList);
					messageFilterRepository.save(message);

					resultObject.setId(messageId);
					resultObject.setResultCode(ErrorCode.SUCCESS.getCode());
					resultObject.setResultDescription(messageSource.getMessage(ErrorCode.SUCCESS.getCode()));
					resultObject.setMulticastMessageFilter(multicastMessageFilters.getMulticastMessageFilter());

					resultObjectsList.add(resultObject);
					genericResponse.setResultObject(resultObjectsList);

				}
			}

		}

		return genericResponse;
	}

}
