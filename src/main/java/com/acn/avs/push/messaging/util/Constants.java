package com.acn.avs.push.messaging.util;

/**
 * The Interface Constants.
 *
 * @author vinay.gupta
 */
public interface Constants {

	/** The active. */
	String ACTIVE = "Active";

	/** The Inactive. */
	String INACTIVE = "Inactive";

	/** The Multicast */
	String MULTICAST = "Multicast";

	/** The Broadcast. */
	String BROADCAST = "Broadcast";

	/** The Unicast. */
	String UNICAST = "Unicast";

	/** Message's messageId already exist */
	String MESSAGE_ID_EXIST = "Message's messageId already exist";

	/** Message's messageId already exist */
	String MESSAGE_ID_NOT_EXIST = "Message messageId does not exist.";

	/** DATEFORMAT */
	String DATEFORMAT = "MM/dd/yyyy HH:mm";

	/** STAR_STR */
	String STAR_STR = "*";

	/** PERCENTAGE_STR */
	String PERCENTAGE_STR = "%";

	/** UNICAST_NOTIFIER */
	String UNICAST_NOTIFIER = "UNICASTSERVICE";

	/** SCHEMA_FOLDER_PATH */
	String SCHEMA_FOLDER_PATH="/public/schema/";

	/** UNICAST_MESSAGE_FILENAME */
	String UNICAST_MESSAGE_FILENAME="UnicastMessageRequest.json";

	/** MESSAGE_IDS_FILENAME */
	String MESSAGE_IDS_FILENAME="MessageIds.json";

	/** UNICAST_MESSAGE_ASSOCIATION_FILENAME */
	String UNICAST_MESSAGE_ASSOCIATION_FILENAME="UnicastMessageAssociationRequest.json";


	/** MULTICAST_MESSAGE_ASSOCIATION_FILENAME */
	String MULTICAST_MESSAGE_FILTER_FILENAME="MulticastMessageFilters.json";

	/** TRIGGER_TYPE */
	String TRIGGER_TYPE="S_MESSAGE_INFO";

	/** DOCUMENT_SERVICE */
	String DOCUMENT_SERVICE = "DOCUMENTSERVICE";

	/**  */
	String MISSING_REQUEST_MSG = "Required request body is missing";

	/**  */
	String MISSING_ID_MSG = "Message id";

	/**  */
	int MAX_UNICAST_ACTIVE_MESSAGES = 5000;

	/**  */
	int MAX_MESSAGES_PER_REQUEST = 50;

	/**
	 * BACKGROUD_PROCESS_TIME_INTERVAL In seconds
	 */
	int PM_BACKGROUD_PROCESS_TIME_INTERVAL=60000;

	int PM_MESSAGE_INCLUSION_INTERVAL=2;

	/**
	 * max multicast active message
	 */
	int MAX_MULTICAST_ACTIVE_MESSAGES = 500;

	/**
	 * max Filter
	 */
	int MAX_FILTERS = 3;

}
